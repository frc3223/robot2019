package frc.robot;

import java.util.function.Consumer;

//Imports
import cern.colt.function.DoubleDoubleFunction;
import cern.colt.matrix.*;
import cern.colt.matrix.linalg.*;

public class StateSpaceController {
    //Global
    public Algebra alg;
    public DoubleDoubleFunction add = new DoubleDoubleFunction(){
        @Override
        public double apply(double arg0, double arg1) {
            return arg0 + arg1;
        }
    };
    public DoubleDoubleFunction sub = new DoubleDoubleFunction(){
    
        @Override
        public double apply(double arg0, double arg1) {
            return arg0 - arg1;
        }
    };
    public DoubleDoubleFunction min = new DoubleDoubleFunction(){
        @Override
        public double apply(double arg0, double arg1) {
            return Math.min(arg0, arg1);
        }
    };
    public DoubleDoubleFunction max = new DoubleDoubleFunction(){
        @Override
        public double apply(double arg0, double arg1) {
            return Math.max(arg0, arg1);
        }
    };
    public DoubleFactory2D factory2D;
    public DoubleMatrix2D A;
    public DoubleMatrix2D A_inv;
    public DoubleMatrix2D B;
    public DoubleMatrix2D C;
    public DoubleMatrix2D K;
    public DoubleMatrix2D Kff;
    public DoubleMatrix2D L;
    public DoubleMatrix2D r;
    public DoubleMatrix2D r_next;
    public DoubleMatrix2D u;
    public DoubleMatrix2D uff;
    public DoubleMatrix2D u_max;
    public DoubleMatrix2D u_min;
    public DoubleMatrix2D uOffset;
    public DoubleMatrix2D x_prev;
    public DoubleMatrix2D y_obs;
    public DoubleMatrix2D y_est;

    public void init(int n, int n_2, int p) {
        this.alg = new Algebra();
        this.factory2D = DoubleFactory2D.dense;

        //Yeeeee, create everything
        this.A = factory2D.make(n, n);
        this.A_inv = factory2D.make(n, n);
        this.B = factory2D.make(n, n_2);
        this.x_prev = factory2D.make(n, 1);
        this.u = factory2D.make(n_2, 1);
        this.uff = factory2D.make(n_2, 1);
        this.u_max = factory2D.make(n_2, 1);
        this.u_min = factory2D.make(n_2, 1);
        this.uOffset = factory2D.make(n_2, 1);
        this.y_est = factory2D.make(p, 1);
        this.y_obs = factory2D.make(p, 1);
        this.C = factory2D.make(p, n);
        this.r = factory2D.make(n, 1);
        this.r_next = factory2D.make(n, 1);
        this.L = factory2D.make(n, p);
        this.Kff = factory2D.make(n_2, n);
        this.K = factory2D.make(n_2, n);
    }

    public void predict() {
        DoubleMatrix2D x_temp = alg.mult(this.B, this.u);
        this.x_prev = alg.mult(this.A, this.x_prev);
        this.x_prev.assign(x_temp, add);
    }

    public void update() {
        this.y_est = alg.mult(this.C, this.x_prev);
        DoubleMatrix2D m = alg.mult(this.A_inv, this.L);
        DoubleMatrix2D x_temp1 = alg.mult(m, this.y_obs);
        DoubleMatrix2D x_temp2 = alg.mult(m, this.y_est);
        this.x_prev.assign(x_temp1, add);
        this.x_prev.assign(x_temp2, sub);
    }

    public void setInput(Consumer<DoubleMatrix2D> input) {
        input.accept(r_next);

        DoubleMatrix2D temp = factory2D.make(r.rows(), r.columns());
        temp.assign(r);
        temp.assign(x_prev, sub);
        this.u = alg.mult(this.K, temp);

        DoubleMatrix2D temp2 = alg.mult(this.A, this.r);
        temp.assign(r_next);
        temp.assign(temp2, sub);
        this.uff = alg.mult(this.Kff, temp);
        
        this.r.assign(this.r_next);
        this.u.assign(this.uff, add);
        this.u.assign(this.uOffset, add);

        this.u.assign(u_min, max);
        this.u.assign(u_max, min);
    }

    public void setOutput(Consumer<DoubleMatrix2D> input) {
        input.accept(y_obs);
    }

    public static void assign(DoubleMatrix2D matrix, double[][] literal) {
        for(int i = 0; i < matrix.rows(); i++) {
            for(int j = 0; j < matrix.columns(); j++) {
                matrix.set(i, j, literal[i][j]);
            }
        }
    }
}