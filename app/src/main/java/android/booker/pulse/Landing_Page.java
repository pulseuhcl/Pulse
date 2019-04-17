package android.booker.pulse;

        import android.content.Intent;
        import android.database.sqlite.SQLiteDatabase;
        import android.os.Handler;
        import android.support.constraint.ConstraintLayout;
        import android.support.v7.app.AppCompatActivity;
        import android.os.Bundle;
        import android.view.View;
        import android.widget.Button;
        import android.widget.ImageView;

        import com.bumptech.glide.Glide;

        import java.io.BufferedReader;

public class Landing_Page extends AppCompatActivity {
    private Button loginButton;
    private Button registerButton;
    private ImageView userIcon;
    private ImageView gearIcon;
    private ImageView pulseLogo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_landing_page);
            loginButton = findViewById(R.id.loginButton);
            registerButton = findViewById(R.id.registerButton);
            loginButton.setOnClickListener(LoginButtonListener);
            registerButton.setOnClickListener(RegisterButtonListener);
            userIcon = findViewById(R.id.userIcon);
            gearIcon = findViewById(R.id.gearIcon);
            pulseLogo = findViewById(R.id.flickerLogo);

        Glide
                .with(this)
                .load(getDrawable(R.drawable.pulse))
                .into(pulseLogo);
    }

    // Hide the navigation bar after a certain delay

    // Event listeners here
    private View.OnClickListener LoginButtonListener = new View.OnClickListener(){
        @Override
        public void onClick(View v){
            LoginButtonClicked();
        }
    };

    private View.OnClickListener RegisterButtonListener = new View.OnClickListener(){
        @Override
        public void onClick(View v){
            RegisterButtonClicked();
        }
    };

    //Event handlers here
    private void LoginButtonClicked(){
        Intent intent = new Intent(Landing_Page.this, Login_Page.class);
        startActivity(intent);
    }

    private void RegisterButtonClicked(){
        Intent intent = new Intent(Landing_Page.this, Register_Page.class);
        startActivity(intent);
    }
}
