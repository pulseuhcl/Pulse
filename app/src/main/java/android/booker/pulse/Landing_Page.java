package android.booker.pulse;

        import android.content.Intent;
        import android.support.v7.app.AppCompatActivity;
        import android.os.Bundle;
        import android.view.View;
        import android.widget.Button;

        import java.io.BufferedReader;

public class Landing_Page extends AppCompatActivity {
    private Button loginButton;
    private Button registerButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_landing_page);
        loginButton = findViewById(R.id.Login_Button);
        registerButton = findViewById(R.id.Register_Button);
        loginButton.setOnClickListener(LoginButtonListener);
        registerButton.setOnClickListener(RegisterButtonListener);
    }

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
