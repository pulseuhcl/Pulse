package android.booker.pulse;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.EditText;

public class Profile_Page extends AppCompatActivity {
    User currentUser = new User();
    EditText firstNameEditText;
    EditText lastNameEditText;
    EditText initialsEditText;
    EditText userNameEditText;
    EditText passwordEditText;
    EditText phoneEditText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile_page);
        currentUser = getIntent().getExtras().getParcelable("currentUser");
        firstNameEditText = findViewById(R.id.firstnameEditText);
        lastNameEditText = findViewById(R.id.lastnameEditText);
        initialsEditText = findViewById(R.id.initialsEditText);
        userNameEditText = findViewById(R.id.usernameEditText);
        passwordEditText = findViewById(R.id.passwordEditText);
        phoneEditText = findViewById(R.id.PhoneNum_EditText);
        firstNameEditText.setText(currentUser.getFirstName());
        lastNameEditText.setText(currentUser.getLastName());
        initialsEditText.setText(currentUser.getInitials());
        phoneEditText.setText(currentUser.getPhoneNumber());
        userNameEditText.setText(currentUser.getUserName());
        passwordEditText.setText(currentUser.getPassword());

    }
}
