package pe.com.gmdsa.sedapal.view.activity;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.FrameLayout;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.Result;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

import me.dm7.barcodescanner.core.IViewFinder;
import me.dm7.barcodescanner.zxing.ZXingScannerView;
import pe.com.gmdsa.sedapal.R;
import pe.com.gmdsa.sedapal.util.Constants;
import pe.com.gmdsa.sedapal.util.LectorqrCustomizado;

public class CaptureQRActivity extends BaseActivity implements ZXingScannerView.ResultHandler {
    private static final int CAMERA_PERMISSION_CODE = 11;
    private ZXingScannerView escannerView;

    @BindView(R.id.fl_escanner)
    FrameLayout flEscanner;

    @BindView(R.id.toolbar)
    Toolbar toolbar;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_capture_qr);
        ButterKnife.bind(this);
        initializeToolbar();
        comprobarPermisosCamara();
    }

    public static Intent getCallingIntent(Context context) {
        return new Intent(context, CaptureQRActivity.class);
    }

    private void initializeToolbar() {
        setSupportActionBar(toolbar);
        toolbar.setTitle(Constants.VALUES.EMPTY);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        setTitle(getString(R.string.despatch_qr));
    }

    @Override
    public void handleResult(Result result) {
        Intent intent = new Intent();

        intent.putExtra(Constants.INTENT_KEY.CODE_QR, result.getText());
        setResult(Activity.RESULT_OK, intent);
        finish();
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        onBackPressed();
        return super.onOptionsItemSelected(item);
    }

    public void comprobarPermisosCamara() {
        if (!isCameraAllowed()) {
            requestCameraPermission();
        }
    }

    public void iniciarEscannerQR() {
        escannerView = new ZXingScannerView(this) {
            @Override
            protected IViewFinder createViewFinderView(Context context) {
                return new LectorqrCustomizado(context);
            }
        };
        List<BarcodeFormat> formats=new ArrayList<>();
        formats.add(BarcodeFormat.QR_CODE);

        flEscanner.addView(escannerView);
        escannerView.setFormats(formats);
        escannerView.setResultHandler(this);
        escannerView.startCamera();
        escannerView.setAutoFocus(true);

    }


    @Override
    protected void onResume() {
        super.onResume();
        iniciarEscannerQR();
    }

    @Override
    protected void onPause() {
        super.onPause();
        escannerView.stopCamera();
    }

    public boolean isCameraAllowed() {
        int result = ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA);
        if (result == PackageManager.PERMISSION_GRANTED)
            return true;
        return false;
    }

    public void requestCameraPermission() {
        if (ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.CAMERA)) {
        }
        ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.CAMERA}, CAMERA_PERMISSION_CODE);
    }


}
