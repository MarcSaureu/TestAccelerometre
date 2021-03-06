package udl.eps.testaccelerometre.listeners;

import android.app.Activity;
import android.graphics.Color;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.widget.TextView;
import android.widget.Toast;

import udl.eps.testaccelerometre.R;

public class AccelerometerSensor implements SensorEventListener {
    private final Activity mainActivity;
    private long lastUpdate;
    private boolean color = false;
    private final TextView colorView;

    public AccelerometerSensor(Activity activity) {
        this.mainActivity = activity;
        colorView = this.mainActivity.findViewById(R.id.ViewTop);
        lastUpdate = System.currentTimeMillis();
    }

    @Override
    public void onSensorChanged(SensorEvent event) {
        getAccelerometer(event);
    }

    private void getAccelerometer(SensorEvent event) {
        long actualTime = System.currentTimeMillis();
        if (actualTime - lastUpdate < 200) {
            return;
        }
        float[] values = event.values;
        // Movement
        float x = values[0];
        float y = values[1];
        float z = values[2];

        float accelerationSquareRoot = (x * x + y * y + z * z)
                / (SensorManager.GRAVITY_EARTH * SensorManager.GRAVITY_EARTH);
        if (accelerationSquareRoot >= 1.7) {
            lastUpdate = actualTime;
            Toast.makeText(this.mainActivity, R.string.shuffed, Toast.LENGTH_SHORT).show();
            if (color) {
                colorView.setBackgroundColor(Color.GREEN);

            } else {
                colorView.setBackgroundColor(Color.RED);
            }
            color = !color;
        }
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {
        // Do something here if sensor accuracy changes.
    }


}
