package ultrasonic;

import lejos.hardware.motor.EV3LargeRegulatedMotor;
import lejos.hardware.port.MotorPort;
import lejos.hardware.Button;
import lejos.hardware.lcd.LCD;

public class UltraSonicSensor {

    public static void main(String[] args) {
        // Set up motors
        EV3LargeRegulatedMotor leftMotor = new EV3LargeRegulatedMotor(MotorPort.A);  // Motor on Port A
        EV3LargeRegulatedMotor rightMotor = new EV3LargeRegulatedMotor(MotorPort.B); // Motor on Port B

        // Set speed and start moving forward
        leftMotor.setSpeed(300);   // degrees per second
        rightMotor.setSpeed(300);

        LCD.clear();
        LCD.drawString("Moving forward", 0, 0);

        leftMotor.forward();
        rightMotor.forward();

        // Move for 15 seconds
        try {
            Thread.sleep(15000); // 15000 milliseconds = 15 seconds
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        // Stop motors
        leftMotor.stop(true);  // true = immediate stop
        rightMotor.stop(true);

        LCD.clear();
        LCD.drawString("Stopped", 0, 0);
        LCD.drawString("Press any key", 0, 2);

        Button.waitForAnyPress();

        // Close motors
        leftMotor.close();
        rightMotor.close();
    }
}
