package frc.robot;

import cern.colt.matrix.DoubleMatrix2D;

import java.util.function.Consumer;
import java.util.function.Supplier;

public class StiltStateSpaceController extends StateSpaceController {
    private double raiseFeedforward;
    private double retractFeedforward;
    private boolean raising = true;

    public StiltStateSpaceController(double raiseFeedforward, double retractFeedforward) {
        this.raiseFeedforward = raiseFeedforward;
        this.retractFeedforward = retractFeedforward;
        initializeMatrices();
    }

    protected void initializeMatrices() {
        init(2, 1, 1);
        double[][] a = new double[][]{
                {1.0, 2.8000216798070815e-05},
                {0.0, 6.1952331876713e-311}
        };
        double[][] a_inv = new double[][]{
                {1.0, 0.0},
                {0.0, 1.0}
        };
        double[][] b = new double[][]{
                {0.001413408031431672},
                {0.07076947961017219}
        };
        double[][] c = new double[][]{
                {1, 0}
        };
        double[][] k = new double[][]{
                {41.34565276795585, 0.0011576872411605205}
        };
        double[][] kff = new double[][]{
                {2.5570639496003844, 11.522919986089597}
        };
        double[][] L = new double[][]{
                {0.999999555555966},
                {0.0}
        };
        // Ks is 0.17599880589230768
        // Kv is 14.130385096914901
        // Ka is 0.00039565384615384614

        assign(A, a);
        assign(A_inv, a_inv);
        assign(B, b);
        assign(C, c);
        assign(K, k);
        assign(Kff, kff);
        assign(this.L, L);
        this.u_min.set(0, 0, -6);
        this.u_max.set(0, 0, 6);

    }

    public void calculate(
            double targetPosition,
            double targetVelocity,
            double currentPosition,
            Supplier<Double> getBusVoltage,
            Consumer<Double> setMotorOutput) {
        this.update();
        this.setInput((r) -> {
            //Target position (meters)
            r.set(0, 0, targetPosition);
            //Target velocity (meters/second)
            r.set(1, 0, targetVelocity);
        });
        double voltage = this.u.get(0, 0);
        double availableVolt = getBusVoltage.get();
        double percentVolt = voltage / availableVolt;
        setMotorOutput.accept(percentVolt);
        this.setOutput((y) -> {
            //Position in meters
            y.set(0, 0, currentPosition);
        });
        this.predict();

    }

    private double feedForward() {
        if(raising) {
            return this.raiseFeedforward;
        }else{
            return this.retractFeedforward;
        }
    }

    @Override
    public void setInput(Consumer<DoubleMatrix2D> input) {
        input.accept(r_next);

        DoubleMatrix2D temp = factory2D.make(r.rows(), r.columns());
        temp.assign(r);
        temp.assign(x_prev, sub);
        this.K.zMult(temp, this.u);

        DoubleMatrix2D temp2 = factory2D.make(A.rows(), r.columns());
        A.zMult(r, temp2);
        temp.assign(r_next);
        temp.assign(temp2, sub);
        this.Kff.zMult(temp, this.uff);

        this.r.assign(this.r_next);
        this.u.assign(this.uff, add);
        this.u.set(0, 0, this.u.get(0,0) - this.feedForward());

        this.u.assign(u_min, max);
        this.u.assign(u_max, min);
    }

    public double getEstimatedPosition()
    {
        return this.x_prev.get(0, 0);
    }
}
