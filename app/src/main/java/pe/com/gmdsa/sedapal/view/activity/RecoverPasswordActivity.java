package pe.com.gmdsa.sedapal.view.activity;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.AppBarLayout;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import butterknife.BindView;
import butterknife.ButterKnife;
import pe.com.gmdsa.sedapal.R;
import pe.com.gmdsa.sedapal.util.Constants;

public class RecoverPasswordActivity extends AppCompatActivity {

    @BindView(R.id.txtNumber)
    TextView txtNumber;
    @BindView(R.id.txtDescription)
    TextView txtDescription;
    @BindView(R.id.toolbar)
    Toolbar toolbar;

    private static final int PERMISSIONS_CALL = 89;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recover_password);
        ButterKnife.bind(this);
        initializeToolbar();
    }

    private void initializeToolbar() {
        setSupportActionBar(toolbar);
        toolbar.setNavigationIcon(R.drawable.icon_close);
        toolbar.setTitle(Constants.VALUES.EMPTY);
        setTitle(getString(R.string.recover_password));
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        onBackPressed();
        return super.onOptionsItemSelected(item);
    }

    public void onClickCall(View view) {
        if (Build.VERSION.SDK_INT < 23) {
            phoneCall();
        } else {
            if (ActivityCompat.checkSelfPermission(this,
                    Manifest.permission.CALL_PHONE) == PackageManager.PERMISSION_GRANTED) {

                phoneCall();
            } else {
                final String[] PERMISSIONS_STORAGE = {Manifest.permission.CALL_PHONE};
                //Asking request Permissions
                ActivityCompat.requestPermissions(this, PERMISSIONS_STORAGE, PERMISSIONS_CALL);
            }
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == PERMISSIONS_CALL) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                phoneCall();
            } else {
            }
        }
    }

    private void phoneCall() {
        if (ActivityCompat.checkSelfPermission(this,
                Manifest.permission.CALL_PHONE) == PackageManager.PERMISSION_GRANTED) {
            Intent callIntent = new Intent(Intent.ACTION_CALL);
            String number = txtNumber.getText().toString();
            callIntent.setData(Uri.parse("tel:" + number));
            //startActivity(callIntent);
        } else {
            Toast.makeText(this, getString(R.string.dont_have_call_permission), Toast.LENGTH_SHORT).show();
        }
    }
}
