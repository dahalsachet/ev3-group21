package lightsensor;

import lejos.hardware.sensor.EV3ColorSensor;
import lejos.hardware.port.SensorPort;
import lejos.hardware.lcd.LCD;
import lejos.hardware.Button;
import lejos.robotics.SampleProvider;

public class LightSensor {
    public static void main(String[] args) {
        // Connect to the color sensor on port 2
        EV3ColorSensor colorSensor = new EV3ColorSensor(SensorPort.S2);
        
        // Get the ambient light mode (brightness)
        SampleProvider lightProvider = colorSensor.getAmbientMode();
        float[] sample = new float[lightProvider.sampleSize()];

        LCD.clear();
        LCD.drawString("Reading Light", 0, 0);

        // Keep reading until any button is pressed
        while (!Button.ESCAPE.isDown()) {
            lightProvider.fetchSample(sample, 0);

            // Scale the light intensity for better granularity
            int lightIntensity = (int)(sample[0] * 1000); // Increase scaling factor for higher density (x1000)
            if (lightIntensity > 1000) lightIntensity = 1000; // Cap it to 1000 for display

            LCD.clear();
            LCD.drawString("Light: " + lightIntensity + "/1000", 0, 0); // Display as 0-1000 range for higher density

            try {
                Thread.sleep(100); // Update every 0.1 seconds for higher responsiveness
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        // Clean up
        colorSensor.close();
    }
}
