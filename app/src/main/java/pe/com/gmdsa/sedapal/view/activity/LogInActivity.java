package pe.com.gmdsa.sedapal.view.activity;

import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.TextInputLayout;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import pe.com.gmdsa.sedapal.R;
import pe.com.gmdsa.sedapal.domain.DTO.ServiceResponse;
import pe.com.gmdsa.sedapal.domain.ro.request.LoginRequest;
import pe.com.gmdsa.sedapal.domain.ro.response.LoginResponse;
import pe.com.gmdsa.sedapal.service.business.UserService;
import pe.com.gmdsa.sedapal.service.business.impl.UserServiceImpl;
import pe.com.gmdsa.sedapal.util.Constants;
import pe.com.gmdsa.sedapal.view.AndroidApplication;


public class LogInActivity extends BaseActivity {

    @BindView(R.id.imageViewLogo)
    ImageView imageViewLogo;
    @BindView(R.id.txtUser)
    EditText txtUser;
    @BindView(R.id.txtPassword)
    EditText txtPassword;
    @BindView(R.id.linearLayout)
    LinearLayout linearLayout;
    @BindView(R.id.btnLogin)
    Button btnLogin;
    @BindView(R.id.btnRecoverPassword)
    TextView btnRecoverPassword;

    UserService loginService = new UserServiceImpl();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_log_in);
        ButterKnife.bind(this);
    }


    private void callServiceMaintenanceDaily() {
        dismissProgressDialog();
        showProgressDialog("Cargando...").setCancelable(false);

        LoginRequest request = new LoginRequest(txtUser.getText().toString(), txtPassword.getText().toString());
        loginService.opLogin(this, request, new ServiceResponse<LoginResponse, String>() {
            @Override
            public void onResponse(LoginResponse response) {
                dismissProgressDialog();
                if (response.getCodResult().equals(Constants.SERVICES.SUCCESSFUL_CODE)) {
                    goToDailyMaintenance(response);
                } else {
                    showDialog(Constants.VALUES.EMPTY, response.getMsg(), new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {

                        }
                    });
                }
            }
            @Override
            public void onFailure(String response) {
                dismissProgressDialog();
                showDialog(null, response, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                    }
                });
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();

        txtPassword.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_SEARCH
                        || actionId == EditorInfo.IME_ACTION_DONE
                        || actionId == EditorInfo.IME_ACTION_SEND) {

                    hideKeyboardFromView(v);

                    onClickLogin(v);
                    return true;
                }
                return false;
            }
        });
    }


    private void goToDailyMaintenance(LoginResponse response) {
        AndroidApplication.getInstance().setUser(response.getData());
        switch (response.getData().getEstado()) {
            case Constants.VALUES.CURRENT_STATUS_SIN_APERTURA:
                callActivity(MaintenanceDailyActivity_2.class.getName());
                finish();
                break;
            case Constants.VALUES.CURRENT_STATUS_APERTURADO:
                callActivity(OptionMenuActivity.class.getName());
                finish();
                break;
            case Constants.VALUES.CURRENT_STATUS_CERRADO:
                callActivity(OptionMenuActivity.class.getName());
                finish();
                break;
            case Constants.VALUES.CURRENT_STATUS_SIN_CIERRE_ANTERIOR:
                callActivity(MaintenanceDailyActivity_2.class.getName());
                finish();
                break;
        }

    }

    private void goToRecoverPassword() {
        callActivity(PasswordForgotActivity.class.getName());
    }

    public void onClickLogin(View v) {
        if (dataValidate()) {
            callServiceMaintenanceDaily();
        }
    }


    @OnClick({R.id.btnRecoverPassword})
    public void onClick() {
        goToRecoverPassword();
    }

    private boolean dataValidate() {

        if (txtUser.getText().length() < 1) {
            showDialog("Mensaje", "Ingrese un usuario", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.dismiss();
                }
            });
            return false;
        }
        if (txtPassword.getText().length() < 1) {
            showDialog("Mensaje", "Ingrese la contraseña", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.dismiss();
                }
            });
            return false;
        }
        return true;
    }

    @Override
    public void onBackPressed() {
        finish();
    }
}