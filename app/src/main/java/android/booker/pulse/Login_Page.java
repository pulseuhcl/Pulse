package android.booker.pulse;
import android.os.*;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class Login_Page extends AppCompatActivity {
    private EditText usernameInput;
    private EditText passwordInput;
    private String username;
    private String password;
    private Button loginButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_page);
        // map the variables to their GUI counterparts
        loginButton = findViewById(R.id.Login_Button);
        usernameInput = findViewById(R.id.UserName_EditText);
        passwordInput = findViewById(R.id.Password_EditText);
        loginButton.setOnClickListener(LoginButtonListener);
    }

    // Listeners go here
    private View.OnClickListener LoginButtonListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            LoginButtonClicked();
        }
    };

    // Functions go below this line.
    public void LoginButtonClicked() {
        username = usernameInput.getText().toString();
        password = passwordInput.getText().toString();
        new AsyncLogin(Login_Page.this).execute(username, password);
    }
}