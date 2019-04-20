package android.booker.pulse;
import android.os.*;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import com.bumptech.glide.Glide;

public class Login_Page extends AppCompatActivity {
    private EditText usernameInput;
    private EditText passwordInput;
    private String username;
    private String password;
    private Button loginButton;
    private ImageView userIcon;
    private ImageView gearIcon;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_page);
        // The widget typed variables declared above are assigned to their respective widgets in the view
        loginButton = findViewById(R.id.Login_Button);
        usernameInput = findViewById(R.id.usernameEditText);
        passwordInput = findViewById(R.id.passwordEditText);
        userIcon = findViewById(R.id.userIcon);
        gearIcon = findViewById(R.id.gearIcon);
        // the event listener for the login button is assigned
        loginButton.setOnClickListener(LoginButtonListener);

        // images are loaded into their widgets using Glide image loading engine
        Glide
                .with(this)
                .load(getDrawable(R.drawable.user))
                .into(userIcon);
        Glide
                .with(this)
                .load(getDrawable(R.drawable.gear))
                .into(gearIcon);

    }

    // the login button listener is declared here
    private View.OnClickListener LoginButtonListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            LoginButtonClicked();
        }
    };

    // this function takes user input from the text boxes and assigned it to the username and password variables
    public void LoginButtonClicked() {
        // the username and password string variables are passed as parameters to the asynchronous
        // task that handles the login
        username = usernameInput.getText().toString();
        password = passwordInput.getText().toString();

        new AsyncLogin(Login_Page.this).execute(username, password);
    }
}