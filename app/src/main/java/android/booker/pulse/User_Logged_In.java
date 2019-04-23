package android.booker.pulse;

import android.content.Context;
import android.content.Intent;
import android.hardware.camera2.CameraAccessException;
import android.hardware.camera2.CameraManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Toast;

import com.bumptech.glide.Glide;

public class User_Logged_In extends AppCompatActivity {
    User currentUser = new User();
    private ImageButton settingsButton;
    private ImageButton unlockButton;
    private ImageButton sendCodeButton;
    private ImageButton profileButton;
    private User_Logged_In userLoggedIn;

    // Light Pattern Initializing Code
    private Button flashButton;
    private Button stopButton;
    Boolean light = true;
    private CameraManager cameraManager;
    private String cameraID;
    Boolean startPattern = false;
    //

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user__logged__in);
        currentUser = getIntent().getExtras().getParcelable("currentUser");
        settingsButton = findViewById(R.id.GearButton);
        unlockButton = findViewById(R.id.unlockButton);
        sendCodeButton = findViewById(R.id.sendCodeButton);
        profileButton = findViewById(R.id.UserButton);
        profileButton.setOnClickListener(profileButtonListener);
        settingsButton.setOnClickListener(settingsPageListener);
        unlockButton.setOnClickListener(unlockButtonListener);
        //testing to see if the user object was passed successfully via intent
        Toast.makeText(getApplicationContext(), "password " + currentUser.getPassword(), Toast.LENGTH_LONG).show();

        Glide
                .with(this)
                .load(getDrawable(R.drawable.user))
                .into(profileButton);
        Glide
                .with(this)
                .load(getDrawable(R.drawable.gear))
                .into(settingsButton);
        Glide
                .with(this)
                .load(getDrawable(R.drawable.pulse))
                .into(unlockButton);
        Glide
                .with(this)
                .load(getDrawable(R.drawable.send))
                .into(sendCodeButton);

        // Light Pattern Code Here
        cameraManager = (CameraManager) getSystemService(Context.CAMERA_SERVICE);

        try {
            cameraID = cameraManager.getCameraIdList()[0];
        } catch (CameraAccessException e) {
            e.printStackTrace();
        }
    }

    // turns on the flash
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

    // turns off the flash
    private void disableTorch() {
        if (!light) {
            try {
                cameraManager.setTorchMode(cameraID, false);
            } catch (CameraAccessException e) {
                e.printStackTrace();
            }
            light = true;
        }
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

    private View.OnClickListener profileButtonListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            profileButtonClicked();
        }
    };

    private void profileButtonClicked(){
        Intent intent = new Intent(getApplicationContext(), Profile_Page.class);
        intent.putExtra("currentUser", currentUser);
        startActivity(intent);
    }

    private View.OnClickListener settingsPageListener = new View.OnClickListener(){
        @Override
        public void onClick(View v){
            settingsButtonClicked();
        }
    };

    private void settingsButtonClicked(){
        Intent intent = new Intent(getApplicationContext(), Settings_Page.class);
        intent.putExtra("currentUser", currentUser);
        startActivity(intent);
    }

    private View.OnClickListener unlockButtonListener = new View.OnClickListener(){
        @Override
        public void onClick(View v){
            unlockButtonClicked();
        }
    };

    private void unlockButtonClicked(){
        flasher.start();
        recreate();
    }
}

