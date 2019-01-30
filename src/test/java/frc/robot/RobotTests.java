package frc.robot;

import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import static org.junit.Assert.assertEquals;

@RunWith(PowerMockRunner.class)
@PrepareForTest({WPI_TalonSRX.class})
public class RobotTests {

    @Test
    public void testTesting() {
        assertEquals(1,1);
        WPI_TalonSRX talon = PowerMockito.mock(WPI_TalonSRX.class);
        talon.set(1.0);
    }
}