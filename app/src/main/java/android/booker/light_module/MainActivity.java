package android.booker.light_module;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.hardware.camera2.CameraAccessException;
import android.hardware.camera2.CameraManager;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;


public class MainActivity extends AppCompatActivity {
    private Button flashButton;
    private Button stopButton;
    private EditText frequencyInput;
    Boolean light = true;
    private CameraManager cameraManager;
    private String cameraID;
    private long frequency;
    Boolean startPattern = false;
    ScheduledExecutorService scheduledExecutorService = Executors.newScheduledThreadPool(1);
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        if(ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA)
            != PackageManager.PERMISSION_DENIED){
            ActivityCompat.requestPermissions(this, new String[] {Manifest.permission.CAMERA}, 0);
        }

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        frequencyInput = findViewById(R.id.frequencyInputEditText);
        flashButton = findViewById(R.id.flashButton);
        flashButton.setOnClickListener(FlashButtonOnClickListener);
        stopButton = findViewById(R.id.stopButton);
        stopButton.setOnClickListener(StopButtonOnClickListener);
        cameraManager = (CameraManager) getSystemService(Context.CAMERA_SERVICE);

        try {
            cameraID = cameraManager.getCameraIdList()[0];
        } catch (CameraAccessException e) {
            e.printStackTrace();
        }
    }

    private void enableTorch() {
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

    public void toast(String msg){
        Context context = getApplicationContext();
        CharSequence text = msg;
        int duration = Toast.LENGTH_SHORT;

        Toast toast = Toast.makeText(context, text, duration);
        toast.show();
    }

    Thread flasher = new Thread(new Runnable() {
        @Override
        public void run() {
            startPattern = true;
            try {
                frequency = Long.parseLong(frequencyInput.getText().toString());
            } catch (NumberFormatException e) {
                Toast.makeText(getBaseContext(), "value not acceptable", Toast.LENGTH_LONG).show();
            }
            frequency = 1000 / frequency;
            while (startPattern) {
                enableTorch();
                try {
                    Thread.sleep(frequency);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    });

    private void FlashButtonClicked() {
        flasher.start();
    }

    // Event listener for FlashButton
    private View.OnClickListener FlashButtonOnClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            FlashButtonClicked();
        }
    };

    private void StopButtonClicked(){
        startPattern = false;
    }

    private View.OnClickListener StopButtonOnClickListener = new View.OnClickListener(){
        @Override
        public void onClick(View v){
            StopButtonClicked();
        }
    };

}


