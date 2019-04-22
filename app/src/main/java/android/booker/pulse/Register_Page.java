package android.booker.pulse;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.bumptech.glide.Glide;

public class Register_Page extends AppCompatActivity {
    EditText firstNameEditText;
    EditText lastNameEditText;
    EditText initialsEditText;
    EditText emailEditText;
    EditText phoneNumberEditText;
    EditText passwordEditText;
    EditText confirmPasswordEditText;
    Button signupButton;
    String firstName; String lastName; String initials; String email; String phoneNumber;
    String password; String confirmPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register__page);
        firstNameEditText = findViewById(R.id.firstnameEditText);
        lastNameEditText = findViewById(R.id.lastnameEditText);
        initialsEditText = findViewById(R.id.initialsEditText);
        emailEditText = findViewById(R.id.usernameEditText);
        phoneNumberEditText = findViewById(R.id.PhoneNum_EditText);
        passwordEditText = findViewById(R.id.passwordEditText);
        confirmPasswordEditText = findViewById(R.id.confirmPasswordEditText);
        signupButton = findViewById(R.id.signupButton);
        signupButton.setOnClickListener(signupButtonListener);
    }

    private View.OnClickListener signupButtonListener = new View.OnClickListener(){
        @Override
        public void onClick(View v) {
            signupButtonClicked();
        }
    };

    private void signupButtonClicked(){
        firstName = firstNameEditText.getText().toString();
        lastName = lastNameEditText.getText().toString();
        initials = initialsEditText.getText().toString();
        email = emailEditText.getText().toString();
        phoneNumber = phoneNumberEditText.getText().toString();
        password = passwordEditText.getText().toString();
        confirmPassword = confirmPasswordEditText.getText().toString();
        if(password.equals(confirmPassword)){
            new AsyncRegister(Register_Page.this).execute(firstName, lastName, initials,
                    email, phoneNumber, password, confirmPassword);
        }
        else{
            Toast.makeText(this, "Passwords do not match! Try again.", Toast.LENGTH_LONG).show();
        }
    }
}
