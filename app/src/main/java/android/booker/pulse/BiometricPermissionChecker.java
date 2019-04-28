package android.booker.pulse;

import android.Manifest;
import android.annotation.TargetApi;
import android.app.KeyguardManager;
import android.content.Context;
import android.content.pm.PackageManager;
import android.support.v4.app.ActivityCompat;
import android.widget.Toast;

/*
    This class checks to make sure biometric permission is enabled in the manifest and checks
    to make sure that a lock screen was setup by the user. It also checks if fingerprint authentication
    is possible with the device.
*/

@TargetApi(28)
public class BiometricPermissionChecker {
    private KeyguardManager keyguardManager;
    private PackageManager packageManager;
    private Context context;

    public BiometricPermissionChecker(Context context){
        keyguardManager = (KeyguardManager) context.getSystemService(Context.KEYGUARD_SERVICE);
        packageManager = context.getPackageManager();
        this.context = context;
    }

    public void checkBiometricSupport(){

        if(!keyguardManager.isKeyguardSecure()){
            Toast.makeText(context, "Lock screen not enabled", Toast.LENGTH_LONG).show();
        }

        if(ActivityCompat.checkSelfPermission(context,
                Manifest.permission.USE_BIOMETRIC) != PackageManager.PERMISSION_GRANTED){
        }
        if(packageManager.hasSystemFeature(PackageManager.FEATURE_FINGERPRINT))
        {
            Toast.makeText(context, "This phone supports fingerprint authentication!", Toast.LENGTH_LONG).show();
        }
    }
}
