package pe.com.gmdsa.sedapal.view.activity;

import android.Manifest;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.support.v4.app.ActivityCompat;
import android.support.v4.view.ViewCompat;
import android.support.v4.view.ViewPropertyAnimatorCompat;
import android.telephony.TelephonyManager;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.DecelerateInterpolator;
import android.widget.Button;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.GlideDrawableImageViewTarget;

import java.util.Timer;
import java.util.TimerTask;

import pe.com.gmdsa.sedapal.R;
import pe.com.gmdsa.sedapal.domain.DTO.ServiceResponse;
import pe.com.gmdsa.sedapal.domain.ro.response.SesionTokenResponse;
import pe.com.gmdsa.sedapal.service.business.UserService;
import pe.com.gmdsa.sedapal.service.business.impl.UserServiceImpl;
import pe.com.gmdsa.sedapal.util.Constants;
import pe.com.gmdsa.sedapal.view.AndroidApplication;


public class SplashActivity extends BaseActivity {

    // private TimerTask splash_task_parametric;
    private TimerTask splash_task;
    private Timer timer;
    private Context ctx;
    //private ParametricService parametricService = new ParametricServiceImpl();
    public static final int STARTUP_DELAY = 300;
    public static final int ANIM_ITEM_DURATION = 100;
    public static final int ITEM_DELAY = 300;

    private boolean animationStarted = false;
    private static final int PERMISSIONS_REQUEST_READ_PHONE_STATE = 999;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // super.onCreate(savedInstanceState);
        setTheme(R.style.AppTheme);
        getWindow().getDecorView().setSystemUiVisibility(
                View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        getDeviceImei();
    }
    private void showSplash(){
        ctx = this;
        timer = new Timer("SplashTimer", true);

        splash_task = new TimerTask() {
            @Override
            public void run() {
               // if (!AndroidApplication.getInstance().getUser().getToken().equals("")) {
                    callServiceStatus();
              //  } else {
                   // goToLogin();
               // }
            }
        };
        timer.schedule(splash_task, Constants.SPLASH_DELAY);
    }

    private void permisoImei() {

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (checkSelfPermission(Manifest.permission.READ_PHONE_STATE)
                    != PackageManager.PERMISSION_GRANTED) {
                // Should we show an explanation?
                ActivityCompat.requestPermissions(this,
                        new String[]{Manifest.permission.READ_PHONE_STATE},
                        PERMISSIONS_REQUEST_READ_PHONE_STATE);
            }
        }else {
            showSplash();
        }
    }

    private TelephonyManager mTelephonyManager;

    private void getDeviceImei() {
        mTelephonyManager = (TelephonyManager) getSystemService(Context.TELEPHONY_SERVICE);
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.READ_PHONE_STATE) != PackageManager.PERMISSION_GRANTED) {
            permisoImei();
        }else {
            showSplash();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (requestCode == PERMISSIONS_REQUEST_READ_PHONE_STATE
                && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            showSplash();
        }else {

            if (ActivityCompat.shouldShowRequestPermissionRationale(this,
                    Manifest.permission.READ_PHONE_STATE)) {
                showDialogOptions("Mensaje", "Para continuar es necesario conceder el permiso", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        ActivityCompat.requestPermissions(SplashActivity.this,
                                new String[]{Manifest.permission.READ_PHONE_STATE},
                                PERMISSIONS_REQUEST_READ_PHONE_STATE);
                    }
                },new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        finish();
                    }
                },"Aceptar","Cancelar");
            } else {
                showDialog("Mensaje", "Otorgar permisos en ajustes.", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        //finish();
                        Intent launchIntent = getPackageManager().getLaunchIntentForPackage("com.package.address");
                        if (launchIntent != null) {
                            startActivity(launchIntent);//null pointer check in case package name was not found
                        }
                    }
                });
            }
        }
    }

    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        if (!hasFocus || animationStarted) {
            return;
        }
        //animate();

        super.onWindowFocusChanged(hasFocus);
    }

    private void animate() {
        ImageView logoImageView = (ImageView) findViewById(R.id.imageViewLogo);
        ViewGroup container = (ViewGroup) findViewById(R.id.container);

        /** En caso se necesite animar un gif **/
        GlideDrawableImageViewTarget imageViewTarget = new GlideDrawableImageViewTarget(logoImageView);
        Glide.with(this).load(R.drawable.splash_gif).into(imageViewTarget);

        ViewCompat.animate(logoImageView)
                .translationY(-250)
                .setStartDelay(STARTUP_DELAY)
                .setDuration(ANIM_ITEM_DURATION).setInterpolator(
                new DecelerateInterpolator(1.2f)).start();

        for (int i = 0; i < container.getChildCount(); i++) {
            View v = container.getChildAt(i);
            ViewPropertyAnimatorCompat viewAnimator;

            if (!(v instanceof Button)) {
                viewAnimator = ViewCompat.animate(v)
                        .translationY(50).alpha(1)
                        .setStartDelay((ITEM_DELAY * i) + 500)
                        .setDuration(1000);
            } else {
                viewAnimator = ViewCompat.animate(v)
                        .scaleY(1).scaleX(1)
                        .setStartDelay((ITEM_DELAY * i) + 500)
                        .setDuration(500);
            }

            viewAnimator.setInterpolator(new DecelerateInterpolator()).start();
        }
    }

    @Override
    public void onBackPressed() {
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (splash_task != null)
            splash_task.cancel();
    }


    private void goToNextActivity(int estado) {
        if (estado == -1) {
            goToLogin();
        } else {
            if (estado == Constants.VALUES.CURRENT_STATUS_SIN_APERTURA || estado == Constants.VALUES.CURRENT_STATUS_SIN_CIERRE_ANTERIOR) {
                callActivity(MaintenanceDailyActivity_2.class.getName());
                finish();
            } else {
                if (estado == Constants.VALUES.CURRENT_STATUS_APERTURADO || estado == Constants.VALUES.CURRENT_STATUS_CERRADO) {
                    callActivity(OptionMenuActivity.class.getName());
                    finish();
                }
            }
        }
    }

    private void goToLogin() {
        callActivity(LogInActivity.class.getName());
        finish();
    }

    private void goToMain() {
        callActivity(OptionMenuActivity.class.getName());
        finish();
    }


    UserService userService = new UserServiceImpl();

    private void callServiceStatus() {
        userService.opSesionToken(this, new ServiceResponse<SesionTokenResponse, String>() {
            @Override
            public void onResponse(SesionTokenResponse response) {
                if (response.getCodResult().equals("0000")) {
                    goToNextActivity(response.getData().getEstado());
                } else {
                    goToLogin();
                }
            }

            @Override
            public void onFailure(String response) {
                showDialog("Mensaje", response, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        //callServiceStatus();
                        finish();

                    }
                });
            }
        });
    }

}