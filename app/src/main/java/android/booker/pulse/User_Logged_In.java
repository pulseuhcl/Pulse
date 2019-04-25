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
import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;


public class User_Logged_In extends AppCompatActivity {
    User currentUser = new User();
    private ImageButton settingsButton;
    private ImageButton unlockButton;
    private ImageButton sendCodeButton;
    private ImageButton profileButton;;

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
        CameraController cameraController = new CameraController(this, currentUser.getPinNumber());
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
        CameraController cameraController = new CameraController(this, currentUser.getPinNumber());
        cameraController.GetCameraIDList();
        cameraController.executePattern();
    }
}

