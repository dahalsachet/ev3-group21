package linefollower;

import lejos.hardware.motor.Motor;
import lejos.hardware.sensor.EV3ColorSensor;
import lejos.hardware.port.SensorPort;
import lejos.hardware.lcd.LCD;
import lejos.hardware.Button;
import lejos.robotics.SampleProvider;
import lejos.utility.Delay;

public class LineFollower {

    public static void main(String[] args) {
        // Create and configure the color sensor
        EV3ColorSensor colorSensor = new EV3ColorSensor(SensorPort.S3);

        // Set the sensor mode to ambient light mode (this measures the ambient light intensity)
        SampleProvider light = colorSensor.getAmbientMode();  

        // Array to store the sensor sample
        float[] sample = new float[light.sampleSize()];

        // Set the motor speeds
        Motor.A.setSpeed(200);
        Motor.B.setSpeed(200);

        // Move both motors forward initially
        Motor.A.forward();
        Motor.B.forward();

        // Main loop for line following
        while (!Button.ESCAPE.isDown()) {
            // Get the current light intensity reading
            light.fetchSample(sample, 0);

            // Display the light intensity for debugging
            LCD.clear();
            LCD.drawString("Light Intensity: " + (int)(sample[0] * 100) + "%", 0, 0);

            // Define a threshold for line detection (you may need to adjust this value)
            float threshold = 0.3f;

            // If the light intensity is low (indicating black line), the robot is on the line
            if (sample[0] < threshold) {
                // Move straight forward
                Motor.A.setSpeed(200);
                Motor.B.setSpeed(200);
                Motor.A.forward();
                Motor.B.forward();
            } 
            else {
                // If the robot is off the line (on a white surface)
                if (sample[0] > 0.6) {
                    // Turn left (if the light intensity is high)
                    Motor.A.setSpeed(150);
                    Motor.B.setSpeed(200);
                    Motor.A.forward();
                    Motor.B.forward();
                } 
                else {
                    // Turn right (if the light intensity is moderate)
                    Motor.A.setSpeed(200);
                    Motor.B.setSpeed(150);
                    Motor.A.forward();
                    Motor.B.forward();
                }
            }

            // Small delay to prevent overwhelming the system
            Delay.msDelay(50);
        }

        // Stop the motors before exiting
        Motor.A.stop();
        Motor.B.stop();

        // Close the color sensor
        colorSensor.close();
    }
}
