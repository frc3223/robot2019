package frc.robot;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import edu.wpi.first.hal.HAL;
import edu.wpi.first.hal.sim.DriverStationSim;
import frc.robot.subsystems.DataLogger;

public class StateSpaceControllerTest {

    public StateSpaceController stateController;
    public DataLogger logger;

    @Test
    public void testSetInput() {
        makeController();

        stateController.setInput((r) -> {
            r.set(0, 0, 1);
            r.set(1, 0, 0);
        });
        assertEquals(0.69, stateController.u.get(0, 0), 0.01);
        assertEquals(0.69, stateController.uff.get(0, 0), 0.01);
    }
    @Test
    public void testSetOutput() {
        makeController();
        stateController.setOutput((y) -> {
            y.set(0, 0, 1);
        });
        assertEquals(1, stateController.y_obs.get(0, 0), 0.0000001);
    }
    @Test
    public void testPredict() {
        double[][] xprev = new double[][] {
            { 0.1 },
            { 0.1 }
        };
        makeController();
        stateController.setInput((r) -> {
            r.set(0, 0, 1);
            r.set(1, 0, 0);
        });
        StateSpaceController.assign(stateController.x_prev, xprev);
        stateController.predict();
        assertEquals(0.12, stateController.x_prev.get(0, 0), 0.01);
        assertEquals(0.876, stateController.x_prev.get(1, 0), 0.001);
    }
    @Test
    public void testUpdate() {
        makeController();
        stateController.setOutput((y) -> {
            y.set(0, 0, 0.2);
        });
        double[][] xprev = new double[][] {
            { 0.1 },
            { 0.1 }
        };
        double[][] u = new double[][] {
            { 0.693 }
        };
        StateSpaceController.assign(stateController.x_prev, xprev);
        StateSpaceController.assign(stateController.u, u);
        stateController.predict();
        stateController.update();
        assertEquals(0.693, stateController.u.get(0, 0), 0.001);
        assertEquals(0.1, stateController.y_est.get(0, 0), 0.02);
        assertEquals(0.199, stateController.x_prev.get(0, 0), 0.001);
        assertEquals(0.881, stateController.x_prev.get(1, 0), 0.001);
    }
    @Test
    public void testPeriodic() {
        //HAL.initialize(500, 0);
        DriverStationSim dsSim = new DriverStationSim();
        dsSim.setDsAttached(true);
        dsSim.setAutonomous(false);
        dsSim.setEnabled(true);
        dsSim.setTest(false);
        logger = new DataLogger("testPeriodic");
        logger.add("Position", () -> stateController.x_prev.get(0, 0));
        logger.add("Velocity", () -> stateController.x_prev.get(1, 0));
        logger.add("Voltage", () -> stateController.u.get(0, 0));
        makeController();
        double[][] xprev = new double[][] {
            { 0.1 },
            { 0.1 }
        };
        double[][] u = new double[][] {
            { 0 }
        };
        StateSpaceController.assign(stateController.x_prev, xprev);
        StateSpaceController.assign(stateController.u, u);

        for(int i = 0; i < 200; i++) {
            stateController.update();
            stateController.setInput((r) -> {
                r.set(0, 0, 1);
                r.set(1, 0, 0);
            });
            stateController.setOutput((y) -> {
                y.set(0, 0, stateController.y_est.get(0, 0));
            });
            stateController.predict();
            logger.log();
        }
        assertEquals(1, stateController.x_prev.get(0, 0), 0.001);
        assertEquals(0, stateController.x_prev.get(1, 0), 0.001);
    }

    public void makeController() {
        stateController = new StateSpaceController();
        stateController.init(2, 1, 1);
        double[][] a = new double[][] {
            { 1, 1.25e-3 },
            { 0, 1.15e-7 }
        };
        double[][] a_inv = new double[][] {
            { 1, -1.09e+4 },
            { 0, 8.70e+6 }
        };
        double[][] b = new double[][] {
            { 0.024 },
            { 1.27 }
        };
        double[][] c = new double[][] {
            { 1, 0 }
        };
        double[][] k = new double[][] {
            { 5.08, 0.006 }
        };
        double[][] kff = new double[][] {
            { 0.69, 0.77 }
        };
        double[][] L = new double[][] {
            { 1 },
            { 6.61e-15 }
        };

        StateSpaceController.assign(stateController.A, a);
        StateSpaceController.assign(stateController.A_inv, a_inv);
        StateSpaceController.assign(stateController.B, b);
        StateSpaceController.assign(stateController.C, c);
        StateSpaceController.assign(stateController.K, k);
        StateSpaceController.assign(stateController.Kff, kff);
        StateSpaceController.assign(stateController.L, L);
        stateController.u_min.set(0, 0, -12);
        stateController.u_max.set(0, 0, 12);
    }
}