
public class TrapezoidalProfiler {
    double acc;
    double maxVelocity;
    double target_pos;
    double tolerance;
    double current_target_v;
    double current_a = 0;
    boolean do_print = false;
    double adist = 0;
    double err = 0;
    double choice = 0;
    double v;
    double okerr;

    public TrapezoidalProfiler(double maxVelocity, double acc, double target_pos, double tolerance, double current_target_v) {
        assert maxVelocity > 0;
        this.maxVelocity = maxVelocity;
        this.target_pos = target_pos;
        this.tolerance = tolerance;
        this.current_target_v = current_target_v;
        this.acc = acc;
    }

    public double getMaxVelocity() {
        return this.maxVelocity;
    }

    public void print(String s) {
        if (do_print) {
            System.out.printf("%s: v=%f, a=%f \n", s, current_target_v, current_a);
        }
    }

    public void calculate_new_velocity(double current_pos, double dt) {
        assert maxVelocity > 0;
        v = current_target_v;
        double okerr = tolerance;
        if (acc == 0) {
            adist = v * 100 * tolerance;
        } else {
            adist = 0.5 * v * v / acc;
        }

        err = this.target_pos - current_pos;
        current_a = 0;

        if (Math.abs(err) < okerr) {
            if (v > 0) {
                choice = 1;
                print("pro1");
                dec_to_zero(dt);
            } else if (v < 0) {
                choice = 2;
                print("pro2");
                inc(dt);
            }
        } else if (okerr <= err && err < adist && v > 0) {
            choice = 3;
            print("pro3");
            dec(dt);
        } else if (okerr <= err && err < adist && v < 0) {
            choice = 4;
            print("pro4");
            inc(dt);
        } else if (-okerr >= err && err > -adist && v < 0) {
            choice = 5;
            print("pro5");
            inc(dt);
            current_a = +acc;
        } else if (-okerr >= err && err > -adist && v > 0) {
            choice = 6;
            print("pro6");
            dec(dt);
        } else if (err > adist && v >= 0) {
            if (v < maxVelocity) {
                choice = 7;
                print("pro7");
                inc(dt);
            } else if (v > maxVelocity) {
                choice = 8;
                print("pro8");
                dec(dt);
            }
        } else if (err < -adist && v <= 0) {
            if (v > -maxVelocity) {
                choice = 9;
                print("pro9");
                dec(dt);
            } else if (v < -maxVelocity) {
                choice = 10;
                print("pro10");
                inc(dt);
            }
        } else if (err > adist && v < 0) {
            choice = 11;
            print("pro11");
            inc(dt);
        } else if (err < -adist && v > 0) {
            choice = 12;
            print("pro12");
            dec(dt);
        }

        print("post");
        current_target_v = v;
    }

    public void inc(double dt) {
        if (v + dt * acc > maxVelocity) {
            current_a = (maxVelocity - v) / dt;
            v = maxVelocity;
        } else {
            v += dt * acc;
            current_a = acc;
        }
    }

    public void dec(double dt) {
        if (v - dt * acc < -maxVelocity) {
            current_a = (-maxVelocity - v) / dt;
            v = -maxVelocity;
        } else {
            v -= dt * acc;
            current_a = -acc;
        }
    }

    public void dec_to_zero(double dt) {
        if (v < dt * acc) {
            v = 0;
            current_a = -v / dt;
        } else {
            v -= dt * acc;
            current_a = -acc;
        }
    }

    public boolean isFinished(double current_pos) {
        err = target_pos - current_pos;
        return (Math.abs(err) < tolerance && current_target_v == 0);
    }
}

