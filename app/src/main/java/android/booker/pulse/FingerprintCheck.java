//package android.booker.pulse;
//
//import android.Manifest;
//import android.annotation.TargetApi;
//import android.content.Context;
//import android.content.pm.PackageManager;
//import android.support.v4.app.ActivityCompat;
//import android.support.v4.hardware.fingerprint.FingerprintManagerCompat;
//
//public class FingerprintCheck {
//
//    // check if the user already has registered fingerprints on the device
//    public static boolean isFingerprintAvailable(Context context){
//        FingerprintManagerCompat fingerprintManager = FingerprintManagerCompat.from(context);
//        return fingerprintManager.hasEnrolledFingerprints();
//    }
//
//    // check if the permission was added to the app
//    public static boolean isPermissionGranted(Context context){
//        return ActivityCompat.checkSelfPermission(context, Manifest.permission.USE_BIOMETRIC) ==
//                PackageManager.PERMISSION_GRANTED;
//    }
//
//
//}
