package android.booker.pulse;

import android.annotation.TargetApi;
import android.content.Context;
import android.hardware.biometrics.BiometricPrompt;
import android.os.CancellationSignal;
import android.widget.Toast;

@TargetApi(28)
public class AuthenticatusMaximus {
    private Context context;
    private CancellationSignal cancellationSignal;

    public AuthenticatusMaximus(Context context){
        this.context = context;
    }

    public BiometricPrompt.AuthenticationCallback authenticationCallback(){
        // returns an instance of Authentication Callback to the caller
        return new BiometricPrompt.AuthenticationCallback() {
            // Here comes the boilerplate! I love boilerplate code!!! :)
            // super in this case is used to call methods from the parent class BiometricPrompt.
            @Override
            public void onAuthenticationError(int errorCode, CharSequence errString) {
                super.onAuthenticationError(errorCode, errString);
                Toast.makeText(context, "Authentication error" + errString, Toast.LENGTH_LONG).show();
            }

            @Override
            public void onAuthenticationHelp(int helpCode, CharSequence helpString){
                super.onAuthenticationHelp(helpCode, helpString);
            }

            @Override
            public void onAuthenticationFailed(){
                super.onAuthenticationFailed();
            }

            @Override
            public void onAuthenticationSucceeded(BiometricPrompt.AuthenticationResult result){
                Toast.makeText(context, "Authentication success!", Toast.LENGTH_LONG).show();
                super.onAuthenticationSucceeded(result);
            }
        };
    }

    // CancellationSignal implementation goes here
    public CancellationSignal getCancellationSignal(){
        cancellationSignal = new CancellationSignal();
        cancellationSignal.setOnCancelListener(new CancellationSignal.OnCancelListener() {
            @Override
            public void onCancel() {
                Toast.makeText(context, "Cancelled via signal!", Toast.LENGTH_LONG).show();
            }
        });
        return cancellationSignal;
    }
}
