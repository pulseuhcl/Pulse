package android.booker.light_module;

import android.content.Context;
import android.graphics.Camera;
import android.hardware.camera2.CameraAccessException;
import android.hardware.camera2.CameraCharacteristics;
import android.hardware.camera2.CameraDevice;
import android.hardware.camera2.CameraManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import java.util.Timer;
import org.w3c.dom.Text;

import java.util.TimerTask;

public class MainActivity extends AppCompatActivity {
    private Button flashButton;
    private EditText frequencyInput;
    Boolean light = true;
    CameraDevice cameraDevice;
    private CameraManager cameraManager;
    private CameraCharacteristics cameraCharacteristics;
    String cameraID;
    Timer timer = new Timer();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        frequencyInput = findViewById(R.id.frequencyInputEditText);
        flashButton = findViewById(R.id.FlashButton);
        flashButton.setOnClickListener(FlashButtonOnClickListener);
        getSystemService(Context.CAMERA_SERVICE);
        try {
            cameraID = cameraManager.getCameraIdList()[0];
        } catch (CameraAccessException e) {
            e.printStackTrace();
        }
    }

    TimerTask task = new TimerTask() {
        @Override
        public void run() {
            if (light) {
                try {
                    cameraManager.setTorchMode(cameraID, true);
                } catch (CameraAccessException e) {
                    e.printStackTrace();
                }
                light = false;
            } else {
                try {
                    cameraManager.setTorchMode(cameraID, false);
                } catch (CameraAccessException e) {
                    e.printStackTrace();
                }

                light = true;
            }
        }
    };


    private void FlashButtonClicked() {
        timer.schedule(task, 1000, 1000);
    }

    // Event listener for FlashButton
    private View.OnClickListener FlashButtonOnClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            FlashButtonClicked();
        }
    };
}
//

