package android.booker.light_module;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.graphics.Camera;
import android.hardware.camera2.CameraAccessException;
import android.hardware.camera2.CameraCharacteristics;
import android.hardware.camera2.CameraDevice;
import android.hardware.camera2.CameraManager;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Timer;
import org.w3c.dom.Text;

import java.util.TimerTask;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;

public class MainActivity extends AppCompatActivity {
    private Button flashButton;
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
        flashButton = findViewById(R.id.FlashButton);
        flashButton.setOnClickListener(FlashButtonOnClickListener);

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

    private void FlashButtonClicked() {
            startPattern = true;
            try{
                frequency = Long.parseLong(frequencyInput.getText().toString());
            }catch(NumberFormatException e){
                Toast.makeText(getBaseContext(), "value not acceptable", Toast.LENGTH_LONG).show();
            }
            frequency = 1000/frequency;
            while(startPattern){
                enableTorch();
                try{
                    // see if you can figure out why I can't pass the user input into the
                    //Thread.sleep() method without it fucking up. When you change it manually with
                    // a long input, it works just fine.
                    Thread.sleep(frequency);
                }catch(Exception e){
                    e.printStackTrace();
                }
            }
    }

    // Event listener for FlashButton
    private View.OnClickListener FlashButtonOnClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            FlashButtonClicked();
        }
    };

}


