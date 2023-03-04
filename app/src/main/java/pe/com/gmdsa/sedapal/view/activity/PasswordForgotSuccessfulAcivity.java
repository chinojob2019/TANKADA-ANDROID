package pe.com.gmdsa.sedapal.view.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import pe.com.gmdsa.sedapal.R;
import pe.com.gmdsa.sedapal.util.Constants;

public class PasswordForgotSuccessfulAcivity extends BaseActivity {
    @BindView(R.id.txtUser)
    TextView txtUser;
    @BindView(R.id.btnVolver)
    Button btnVolver;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_password_forgot_successful);
        ButterKnife.bind(this);

        txtUser.setText(getIntent().getExtras().getString(Constants.INTENT_KEY.USER));
    }

    @OnClick({R.id.btnVolver})
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btnVolver:
                finish();
                break;
        }
    }

    @Override
    public void onBackPressed() {
        finish();
        //super.onBackPressed();
    }
}
