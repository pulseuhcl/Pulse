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
    private Button loginButton;
    private ImageView userIcon;
    private ImageView gearIcon;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_page);
        loginButton = findViewById(R.id.signupButton);
        usernameInput = findViewById(R.id.usernameEditText);
        passwordInput = findViewById(R.id.passwordEditText);
        userIcon = findViewById(R.id.UserButton);
        gearIcon = findViewById(R.id.GearButton);
        loginButton.setOnClickListener(LoginButtonListener);

        Glide
                .with(this)
                .load(getDrawable(R.drawable.user))
                .into(userIcon);
        Glide
                .with(this)
                .load(getDrawable(R.drawable.gear))
                .into(gearIcon);
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
        User currentUser = new User(usernameInput.getText().toString().replaceAll("\\s", ""), passwordInput.getText().toString());
        if(!currentUser.getUserName().isEmpty() && !currentUser.getPassword().isEmpty()){
            new AsyncLogin(Login_Page.this).execute(currentUser);
        }
    }
}