package lightsensor;

import lejos.hardware.sensor.EV3ColorSensor;
import lejos.hardware.port.SensorPort;
import lejos.hardware.lcd.LCD;
import lejos.hardware.Button;
import lejos.robotics.SampleProvider;

public class LightSensor{
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
            int lightIntensity = (int)(sample[0] * 100); // Convert to percentage
            
            LCD.clear();
            LCD.drawString("Light: " + lightIntensity + "%", 0, 0);

            try {
                Thread.sleep(200); // update every 0.2 seconds
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        // Clean up
        colorSensor.close();
    }
}
