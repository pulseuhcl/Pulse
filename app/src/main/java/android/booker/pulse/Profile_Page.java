package android.booker.pulse;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
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
    EditText phoneEditText;
    ImageButton profileIcon;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile_page);
        currentUser = getIntent().getExtras().getParcelable("currentUser");
        firstNameEditText = findViewById(R.id.firstnameEditText);
        lastNameEditText = findViewById(R.id.lastnameEditText);
        initialsEditText = findViewById(R.id.initialsEditText);
        userNameEditText = findViewById(R.id.usernameEditText);
        currentPasswordEditText = findViewById(R.id.newPasswordEditText);
        phoneEditText = findViewById(R.id.PhoneNum_EditText);
        profileIcon = findViewById(R.id.UserButton);
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


}
