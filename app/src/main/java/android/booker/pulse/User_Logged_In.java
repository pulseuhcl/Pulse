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
import java.util.Deque;
import java.util.LinkedList;


public class User_Logged_In extends AppCompatActivity {
    User currentUser = new User();
    private ImageButton settingsButton;
    private ImageButton unlockButton;
    private ImageButton sendCodeButton;
    private ImageButton profileButton;
    private User_Logged_In userLoggedIn;

    // Light Pattern Initializing Code
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
                .load(getDrawable(R.drawable.lock))
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
        Thread flasher = new Thread(new Runnable() {
            @Override

            public void run() {
                // pin pulled from the user object; converted to int
                int pin = Integer.parseInt(currentUser.getPinNumber());
                // pin converted to binary string
                String pinString = Integer.toBinaryString(pin);
                // binary string to array of characters
                char[] bufferArray = pinString.toCharArray();
                // initialize the list to read the char array values into
                final Deque<Character> pattern = new LinkedList<>();
                int counter = 2;
                while(counter > 0){
                    for(int i =0; i < bufferArray.length; i++){
                        pattern.addLast(bufferArray[i]);
                    }
                    while(pattern.size() < 10 + (10 - bufferArray.length)){
                        pattern.addFirst('0');
                        pattern.addLast('0');
                    }
                    counter--;
                }
                pattern.addFirst('1');
                pattern.addFirst('1');
                pattern.addFirst('1');
                pattern.addLast('0');
                pattern.addLast('1');

                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(getApplicationContext(), pattern.toString(), Toast.LENGTH_LONG).show();
                    }
                });
                // sum of consecutive ones to give the time for the flash to stay on
                int delayForZeros = 40;// I need to fix it
                int delayForOnes = 40;//
                /////
                startPattern = true;
                Boolean isOn = false;
                long futureTime=System.currentTimeMillis();

                // wake up call
                enableTorch();
                while(System.currentTimeMillis() <= futureTime + 1){}

                disableTorch();
                while(System.currentTimeMillis() <= futureTime + 10){}

                for(Character c : pattern){
                    futureTime = System.currentTimeMillis();

                    if(c == '1'){
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
        flasher.start();
    }
}

