package android.booker.pulse;

        import android.annotation.TargetApi;
        import android.content.DialogInterface;
        import android.content.Intent;
        import android.hardware.biometrics.BiometricPrompt;
        import android.support.v7.app.AppCompatActivity;
        import android.os.Bundle;
        import android.view.View;
        import android.widget.Button;
        import android.widget.ImageView;
        import android.widget.Toast;

        import com.bumptech.glide.Glide;

public class Landing_Page extends AppCompatActivity {
    private Button loginButton;
    private Button registerButton;
    private ImageView userIcon;
    private ImageView gearIcon;
    private ImageView flickerLogo;
    @TargetApi(28)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_landing_page);
            loginButton = findViewById(R.id.loginButton);
            registerButton = findViewById(R.id.registerButton);
            loginButton.setOnClickListener(LoginButtonListener);
            registerButton.setOnClickListener(RegisterButtonListener);
            userIcon = findViewById(R.id.UserButton);
            gearIcon = findViewById(R.id.GearButton);
            flickerLogo = findViewById(R.id.flickerLogo);
            BiometricPermissionChecker biometricPermissionChecker = new BiometricPermissionChecker(this);
            biometricPermissionChecker.checkBiometricSupport();
            AuthenticatusMaximus authenticator = new AuthenticatusMaximus(this);

            BiometricPrompt biometricPrompt = new BiometricPrompt.Builder(this)
                    .setTitle("Pulse")
                    .setSubtitle("Pulse Biometric Authentication")
                    .setDescription("Pulse uses biometric authentication for extra security")
                    .setNegativeButton("Cancel", this.getMainExecutor(), new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            Toast.makeText(getApplicationContext(), "Authentication cancelled!", Toast.LENGTH_LONG).show();
                        }
                    })
                    .build();

            biometricPrompt.authenticate(authenticator.getCancellationSignal(), getMainExecutor(), authenticator.authenticationCallback());

            Glide
                    .with(this)
                    .load(getDrawable(R.drawable.pulse))
                    .into(flickerLogo);
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
