package android.booker.pulse;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.EditText;

public class Profile_Page extends AppCompatActivity {
    User currentUser = new User();
    EditText firstNameEditText;
    EditText lastNameEditText;
    EditText userNameEditText;
    EditText passwordEditText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile_page);
        currentUser = getIntent().getExtras().getParcelable("currentUser");
        firstNameEditText = findViewById(R.id.firstNameEditText);
        lastNameEditText = findViewById(R.id.lastNameEditText);
        userNameEditText = findViewById(R.id.usernameEditText);
        passwordEditText = findViewById(R.id.passwordEditText);
        firstNameEditText.setText(currentUser.getFirstName());
        lastNameEditText.setText(currentUser.getLastName());
        userNameEditText.setText(currentUser.getUserName());
        passwordEditText.setText(currentUser.getPassword());
    }
}
