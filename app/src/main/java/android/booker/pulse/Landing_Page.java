package android.booker.pulse;

        import android.annotation.TargetApi;
        import android.content.DialogInterface;
        import android.content.Intent;
        import android.database.sqlite.SQLiteDatabase;
        import android.hardware.biometrics.BiometricPrompt;
        import android.os.Build;
        import android.support.v7.app.AppCompatActivity;
        import android.os.Bundle;
        import android.view.View;
        import android.widget.Button;

public class Landing_Page extends AppCompatActivity {
    private Button loginButton;
    private Button registerButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
            SQLiteDatabase pulseDB = openOrCreateDatabase("pulseDB", MODE_PRIVATE, null);
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_landing_page);
            loginButton = findViewById(R.id.Login_Button);
            registerButton = findViewById(R.id.Register_Button);
            loginButton.setOnClickListener(LoginButtonListener);
            registerButton.setOnClickListener(RegisterButtonListener);

    }

    @TargetApi(Build.VERSION_CODES.P)
    private void displayBiometricPrompt(final BiometricPrompt.AuthenticationCallback biometricCallback){
        new BiometricPrompt.Builder(Landing_Page.this)
                .setTitle("Flickr Biometric Authentication")
                .setSubtitle("Please place your finger")
                .setDescription("")
                .setNegativeButton("Error", Landing_Page.this.getMainExecutor(), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        biometricCallback.onAuthenticationFailed();
                    }
                })
                .build();
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
