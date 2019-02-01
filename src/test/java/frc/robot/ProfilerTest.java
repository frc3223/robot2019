package frc.robot;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class ProfilerTest {

    @Test
    public void testSomething(){
        TrapezoidalProfiler profiler = new TrapezoidalProfiler(1,1,3,0.05,0);
        profiler.calculate_new_velocity(0,1);
        assertEquals(1,profiler.getCurrentTargetVelocity(),0.01);
        assertEquals(1,profiler.getCurrentPosition(),0.01);
    }
    @Test(expected = AssertionError.class )
    public void test_cruise_velocity_positive(){
        TrapezoidalProfiler profiler = new TrapezoidalProfiler(5 ,20,0,0.5,0);
        assertEquals(5,profiler.getCurrentTargetVelocity(),0.01);
    }
    @Test
    public void test_pos_can_be_negative(){
        TrapezoidalProfiler profiler = new TrapezoidalProfiler(3,20,-200,0.5,0);
    }

    @Test
    //public void test_isFinished(){
        //TrapezoidalProfiler profiler = new TrapezoidalProfiler (10, 20, 100,.5,0);
        //assertEquals(true,profiler.isFinished(profiler.current_pos));
        //assert profiler.isFinished(profiler.getCurrentPosition());
        //assertTrue(profiler.isFinished(profiler.current_pos));
    //}
    public void loop_test(){
        double DT = 0.02;
        TrapezoidalProfiler profiler = new TrapezoidalProfiler(10, 20, 50,.5, 0);

        double t = 0;
        double pos = 0;

        //if (log_trajectory) {
            /*
            logger = DataLogger("test_profiler1.csv");
            logger.log_while_disabled = True;
            logger.do_print = True;
            logger.add('t', lambda:t);
            logger.add('pos', lambda:pos);
            logger.add('v', lambda:profiler.current_target_v);
            logger.add('a', lambda:profiler.current_a);
            */
        //}
        while (!profiler.isFinished(pos)) {
            if (true/*log_trajectory*/) {
                //logger.log();
                profiler.calculate_new_velocity(pos, DT);

                pos += profiler.current_target_v * DT;
                t += DT;
            }
            if (t > 10) {
                //if (log_trajectory) {
                    //logger.close();
                    //assert False,"sim loop timed out";
                //}
            }
            if (t< 0.501) {
                assertEquals(20,profiler.current_a,0.01);
            }
            if (0.501 < t && t < 5) {
                assertEquals(10,profiler.current_target_v,0.01);
                assertEquals(0,profiler.current_a,0.01);
            }
            if (5 < t && t < 5.5 - 0.001) {
                assertEquals(-20,profiler.current_a,0.01);
            }
            if ( t <= 5.5+0.001 && t >= 5.5-0.001 ) {
                assertEquals(0,profiler.current_a,0.01); //profiler.current_a == pytest.approx(0., 0.01),"t = %f" % (t,);
                assertEquals(0,profiler.current_target_v,0.01);
            }
        }

        //if (log_trajectory) {
            //logger.log();
            //logger.close();
        //}

        assertEquals(5.52,t,0.05);
        assert profiler.current_a == 0;
    }


}
