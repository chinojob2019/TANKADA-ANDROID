package pe.com.gmdsa.sedapal.util;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;
import android.support.v4.content.ContextCompat;

/**
 * Created by jmauriciog on 08/06/2016.
 * Class Check permisos
 */
public class PermissionChecker {

    public static boolean isGpsEnable(Activity activity) {
        boolean result = true;

        if (ContextCompat.checkSelfPermission(activity,
                Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {
            result = false;
        }
        return result;
    }

    public static boolean isCameraEnable(Context ctx) {
        boolean result = true;

        if (ContextCompat.checkSelfPermission(ctx,
                Manifest.permission.CAMERA)
                != PackageManager.PERMISSION_GRANTED) {
            result = false;
        }
        return result;
    }

}
