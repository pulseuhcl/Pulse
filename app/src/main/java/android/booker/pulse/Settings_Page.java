package android.booker.pulse;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;

import com.bumptech.glide.Glide;

public class Settings_Page extends AppCompatActivity {
    EditText currentPinEditText;
    EditText newPinEditText;
    EditText confirmPinEditText;
    ImageButton profileButton;
    ImageButton settingsButton;
    String currentPin;
    String newPin;
    String confirmPin;
    Button updatePinButton;
    User currentUser = new User();
    View currentPinView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings_page);
        currentUser = getIntent().getExtras().getParcelable("currentUser");
        profileButton = findViewById(R.id.UserButton);
        settingsButton = findViewById(R.id.GearButton);
        currentPinEditText = findViewById(R.id.currentPinEditText);
        newPinEditText = findViewById(R.id.newPinEditText);
        confirmPinEditText = findViewById(R.id.confirmNewPinEditText);
        updatePinButton = findViewById(R.id.updatePin);
        currentPinView = findViewById(R.id.CurrentPinView);
        updatePinButton.setOnClickListener(updatePinButtonListener);

        if(currentUser.getPinNumber() == null){
            currentPinView.setVisibility(View.INVISIBLE);
        }

        Glide
                .with(this)
                .load(getDrawable(R.drawable.user))
                .into(profileButton);

        Glide
                .with(this)
                .load(getDrawable(R.drawable.gear))
                .into(settingsButton);
    }

    private View.OnClickListener updatePinButtonListener = new View.OnClickListener(){
        @Override
        public void onClick(View v){
            updatePinButtonClicked();
        }
    };

    private void updatePinButtonClicked(){
        currentPin = currentPinEditText.getText().toString();
        newPin = newPinEditText.getText().toString();
        confirmPin = confirmPinEditText.getText().toString();

        if(newPin.equals(confirmPin)){
            /* pass the current values of the User object into the  SetPin asynchronous task so that they can
               then be passed further along to the User_Logged_In activity. This is where the user is dropped
               off after they change their pin. This is the same page the Login_Page directs to
               but everything about the user object must be passed from here to maintain state once
               you arrive back at the landing_page.
            */
            new AsyncSetPin(Settings_Page.this).execute(currentUser.getUserName(), currentUser.getPassword(),
                    currentPin, newPin, currentUser.getFirstName(), currentUser.getLastName(), currentUser.getInitials(),
                    currentUser.getPhoneNumber());
        }
    }
}
