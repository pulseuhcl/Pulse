package android.booker.pulse;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import com.bumptech.glide.Glide;

public class Profile_Page extends AppCompatActivity {
    User currentUser = new User();
    EditText firstNameEditText;
    EditText lastNameEditText;
    EditText initialsEditText;
    EditText userNameEditText;
    EditText currentPasswordEditText;
    EditText newPasswordEditText;
    EditText confirmPasswordEditText;
    EditText phoneEditText;
    ImageButton profileIcon;
    ImageButton settingsButton;
    Button editButton;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile_page);
        currentUser = getIntent().getExtras().getParcelable("currentUser");
        firstNameEditText = findViewById(R.id.firstnameEditText);
        lastNameEditText = findViewById(R.id.lastnameEditText);
        initialsEditText = findViewById(R.id.initialsEditText);
        userNameEditText = findViewById(R.id.usernameEditText);
        currentPasswordEditText = findViewById(R.id.currentPasswordEditText);
        newPasswordEditText = findViewById(R.id.newPasswordEditText);
        confirmPasswordEditText = findViewById(R.id.confirmPasswordEditText);
        phoneEditText = findViewById(R.id.PhoneNum_EditText);
        profileIcon = findViewById(R.id.UserButton);
        editButton = findViewById(R.id.Edit_Button);
        editButton.setOnClickListener(EditButtonListener);
        settingsButton = findViewById(R.id.GearButton);
        settingsButton.setOnClickListener(settingsButtonListener);
        firstNameEditText.setText(currentUser.getFirstName());
        lastNameEditText.setText(currentUser.getLastName());
        initialsEditText.setText(currentUser.getInitials());
        phoneEditText.setText(currentUser.getPhoneNumber());
        userNameEditText.setText(currentUser.getUserName());
        Toast.makeText(this, currentUser.getPassword(), Toast.LENGTH_SHORT).show();
        Glide
                .with(this)
                .load(getDrawable(R.drawable.user))
                .into(profileIcon);
    }

    private View.OnClickListener settingsButtonListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            settingsButtonClicked();
        }
    };

    private View.OnClickListener EditButtonListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            editButtonClicked();
        }
    };

    private void settingsButtonClicked(){
        Intent intent = new Intent(getApplicationContext(), Settings_Page.class);
        intent.putExtra("currentUser", currentUser);
        startActivity(intent);
    }

    private void editButtonClicked(){
        User newUser = new User(firstNameEditText.getText().toString(), lastNameEditText.getText().toString(),
                initialsEditText.getText().toString(), userNameEditText.getText().toString(), currentPasswordEditText.getText().toString(),
                newPasswordEditText.getText().toString(), confirmPasswordEditText.getText().toString(), phoneEditText.getText().toString());

        if(newUser.getNewPassword().equals(newUser.getConfirmPassword())){
            new AsyncProfileUpdate(Profile_Page.this).execute(newUser, currentUser);
        }
        else{
            Toast.makeText(this, "Passwords do not match! Please try again.", Toast.LENGTH_LONG).show();
        }
    }
}
