package pe.com.gmdsa.sedapal.view.activity;

import android.content.DialogInterface;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.ContextThemeWrapper;
import android.view.KeyEvent;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;

import java.util.Objects;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import pe.com.gmdsa.sedapal.R;
import pe.com.gmdsa.sedapal.domain.DTO.ServiceResponse;
import pe.com.gmdsa.sedapal.domain.ro.request.RecuperarContrasenaRequest;
import pe.com.gmdsa.sedapal.domain.ro.response.BaseResponse;
import pe.com.gmdsa.sedapal.domain.ro.response.RecuperarContrasenaResponse;
import pe.com.gmdsa.sedapal.service.business.UserService;
import pe.com.gmdsa.sedapal.service.business.impl.UserServiceImpl;
import pe.com.gmdsa.sedapal.util.Constants;

public class PasswordForgotActivity extends BaseActivity {

    @BindView(R.id.linearLayout3)
    LinearLayout linearLayout3;
    @BindView(R.id.txtUser)
    EditText txtUser;
    @BindView(R.id.btnSend)
    Button btnSend;
    @BindView(R.id.linearLayoutBottom)
    LinearLayout linearLayoutBottom;
    @BindView(R.id.toolbar)
    Toolbar toolbar;

    UserService userService = new UserServiceImpl();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_password_forgot);
        ButterKnife.bind(this);
        initializeToolbar();
    }

    private void initializeToolbar() {
        setSupportActionBar(toolbar);
        toolbar.setTitle(Constants.VALUES.EMPTY);
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
        setTitle(getString(R.string.title_password_forgot));
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        onBackPressed();
        return super.onOptionsItemSelected(item);
    }

    private boolean validar() {
        if (txtUser.getText().toString().trim().length() > 0) {
            return true;
        } else {
            showDialog("Mesanje", "Ingrese un usuario para enviar la solicitud.", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {

                }
            });
            return false;
        }

    }

    private void callServicePasswordForgot() {
        dismissProgressDialog();
        showProgressDialog();
        RecuperarContrasenaRequest recuperarContrasenaRequest = new RecuperarContrasenaRequest(txtUser.getText().toString().trim());
        userService.opRecuperarContrasena(this, recuperarContrasenaRequest, new ServiceResponse<BaseResponse, String>() {
            @Override
            public void onResponse(BaseResponse response) {
                dismissProgressDialog();
                if (response.getCodResult().equals(Constants.SERVICES.SUCCESSFUL_CODE)) {
                    callActivityPasswordForgotSucessful();
                } else {
                    showMesangeService(response.getMsg());
                }
            }

            @Override
            public void onFailure(String response) {
                //callActivityPasswordForgotSucessful();
                dismissProgressDialog();
                showDialog(null, response, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                    }
                });
            }
        });


    }

    private void showMesangeService(String mensaje) {
        showDialog("Mensaje", mensaje, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });
    }


    private void callActivityPasswordForgotSucessful() {
        Bundle b = new Bundle();
        b.putString(Constants.INTENT_KEY.USER, txtUser.getText().toString());
        callActivity(PasswordForgotSuccessfulAcivity.class.getName(), b);
        finish();
    }

    @OnClick(R.id.btnSend)
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btnSend:
                if (validar()) {
                    callServicePasswordForgot();
                }
                break;
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        validateCheyPresses();
    }

    private void validateCheyPresses() {

        txtUser.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (txtUser.length() > 0) {
                    btnSend.setBackgroundResource(R.drawable.button_border_blue);
                } else {
                    btnSend.setBackgroundResource(R.drawable.button_border_gray);
                }
            }
        });
    }
}
