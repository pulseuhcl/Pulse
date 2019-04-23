package android.booker.light_module;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
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
import android.widget.TextView;
import android.widget.Toast;

import java.math.BigInteger;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;


public class MainActivity extends AppCompatActivity {
    private Button flashButton;
    private Button stopButton;
    Boolean light = true;
    private CameraManager cameraManager;
    private String cameraID;
    Boolean startPattern = false;
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        if(ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA)
            != PackageManager.PERMISSION_DENIED){
            ActivityCompat.requestPermissions(this, new String[] {Manifest.permission.CAMERA}, 0);
        }

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
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
        if(light){
            try {
                cameraManager.setTorchMode(cameraID, true);
            } catch (CameraAccessException e) {
                e.printStackTrace();
            }
            light = false;
        }
    }

    private void disableTorch(){
        if(!light){
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
           // String pin = "111";
            //char[] pinArray = pin.toCharArray();
            char[] pinArray = { '1','0','1','0','0',
                                '0','1','0','1','0','1','0','0','0','1',
                                '0','0','0','0','0','1','0','0','0','1',
                                '0','1','0','1','0','0','0','0','0','0',
                                '0','0','0','0','0','0','0','0','0','0',
                                '0','1','0','0','0'};

            // sum of consecutive ones to give the time for the flash to stay on
            int delayForZeros = 20;// I need to fix it
            int delayForOnes = 20;//
            /////
            startPattern = true;
            Boolean isOn = false;
            long futureTime=System.currentTimeMillis();

            // wake up call
            enableTorch();
            while(System.currentTimeMillis() <= futureTime + 1){}

           disableTorch();
           while(System.currentTimeMillis() <= futureTime + 10){}

            for(int i = 0; i < pinArray.length; i++){
                futureTime = System.currentTimeMillis();

                if(pinArray[i] == '1'){
                    //futureTime = System.currentTimeMillis() + delayForOnes;
                    if(!isOn){
                        enableTorch();
                        isOn = true;
                    }
                    while(System.currentTimeMillis() <= futureTime + delayForOnes){

                    }
                }
                else{
                    //futureTime = System.currentTimeMillis() + delayForZeros;
                    if(isOn){
                        disableTorch();
                        isOn = false;
                    }
                    while(System.currentTimeMillis() <= futureTime + delayForZeros){

                    }
                }

            }
            if(isOn){
            disableTorch();
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

    // Event listener for focus leave pinEditText



    private void StopButtonClicked(){
        startPattern = false;
        try{
            cameraManager.setTorchMode(cameraID, false);
        }catch(CameraAccessException e){
            e.printStackTrace();
        }

        recreate();
    }

    private View.OnClickListener StopButtonOnClickListener = new View.OnClickListener(){
        @Override
        public void onClick(View v){
            StopButtonClicked();
        }
    };

}


