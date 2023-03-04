package pe.com.gmdsa.sedapal.view.activity;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import pe.com.gmdsa.sedapal.R;
import pe.com.gmdsa.sedapal.domain.DTO.ServiceResponse;
import pe.com.gmdsa.sedapal.domain.ro.response.CerrarSesionResponse;
import pe.com.gmdsa.sedapal.domain.ro.response.SesionTokenResponse;
import pe.com.gmdsa.sedapal.service.business.UserService;
import pe.com.gmdsa.sedapal.service.business.impl.UserServiceImpl;
import pe.com.gmdsa.sedapal.util.Constants;
import pe.com.gmdsa.sedapal.view.AndroidApplication;

public class OptionMenuActivity extends BaseActivity {

    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.frameLayoutSale)
    FrameLayout frameLayoutSale;
    @BindView(R.id.frameLayoutANF)
    FrameLayout frameLayoutANF;
    @BindView(R.id.btn_activity_movement)
    ImageButton btnActivityMovement;
    @BindView(R.id.btn_closure_day)
    ImageButton btnClosureDay;
    @BindView(R.id.tv_despacho_ventas)
    TextView tvDespachoVentas;
    @BindView(R.id.iv_despacho_ventas)
    ImageView ivDespachoVentas;
    @BindView(R.id.tv_despacho_anf)
    TextView tvDespachoAnf;
    @BindView(R.id.iv_despacho_anf)
    ImageView ivDespachoAnf;
    @BindView(R.id.btn_toolbar)
    TextView btnToolbar;

    private int typeView;//estado actual del usuario

    UserService userService = new UserServiceImpl();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_option_menu);
        ButterKnife.bind(this);
        initializeToolbar();
    }

    private void callServiceStatus() {

        showProgressDialog().setCancelable(false);
        userService.opSesionToken(this, new ServiceResponse<SesionTokenResponse, String>() {
            @Override
            public void onResponse(final SesionTokenResponse response) {
                dismissProgressDialog();
                if (response.getCodResult()!=null){
                    if (response.getCodResult().equals(Constants.SERVICES.SUCCESSFUL_CODE)) {
                        typeView = response.getData().getEstado();
                        paintView(typeView);
                    } else {
                        showDialog(Constants.VALUES.EMPTY, response.getMsg(), new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {

                            }
                        });
                    }
                }else {
                    showDialog(Constants.VALUES.EMPTY, "Error de conexion", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {

                        }
                    });
                }
            }

            @Override
            public void onFailure(String response) {
                showDialog(Constants.VALUES.EMPTY, response, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                    }
                });
            }
        });
    }


    private void paintView(int typeView) {
        switch (typeView) {
            case Constants.VALUES.CURRENT_STATUS_SIN_APERTURA:
                callActivity(MaintenanceDailyActivity_2.class.getName());
                finish();

                break;
            case Constants.VALUES.CURRENT_STATUS_APERTURADO:
                btnActivityMovement.setEnabled(true);
                btnClosureDay.setEnabled(true);
                frameLayoutSale.setEnabled(true);
                frameLayoutANF.setEnabled(true);
                btnClosureDay.setImageResource(R.drawable.icon_import_enabled);
                tvDespachoVentas.setTextColor(ContextCompat.getColor(this, R.color.gray));
                tvDespachoAnf.setTextColor(ContextCompat.getColor(this, R.color.gray));
                ivDespachoVentas.setVisibility(View.VISIBLE);
                ivDespachoAnf.setVisibility(View.VISIBLE);
                break;

            case Constants.VALUES.CURRENT_STATUS_CERRADO:
                btnActivityMovement.setEnabled(true);
                // btnClosureDay.setEnabled(false);
                frameLayoutSale.setEnabled(false);
                frameLayoutANF.setEnabled(false);
                btnClosureDay.setImageResource(R.drawable.icon_import_disabled);
                tvDespachoVentas.setTextColor(ContextCompat.getColor(this, R.color.light_gray));
                tvDespachoAnf.setTextColor(ContextCompat.getColor(this, R.color.light_gray));
                ivDespachoVentas.setVisibility(View.GONE);
                ivDespachoAnf.setVisibility(View.GONE);
                break;

            case Constants.VALUES.CURRENT_STATUS_SIN_CIERRE_ANTERIOR:

                showDialogOptions("Mensaje", "Tiene pendiente el cierre diario", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        callActivity(MaintenanceDailyActivity_2.class.getName());

                    }
                }, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                }, getString(R.string.accept), getString(R.string.cancel));

                //callActivity(MaintenanceDailyActivity_2.class.getName());

                tvDespachoVentas.setTextColor(ContextCompat.getColor(this, R.color.light_gray));
                tvDespachoAnf.setTextColor(ContextCompat.getColor(this, R.color.light_gray));
                ivDespachoVentas.setVisibility(View.GONE);
                ivDespachoAnf.setVisibility(View.GONE);
                break;
        }

    }

    @OnClick({R.id.frameLayoutSale, R.id.frameLayoutANF,R.id.btn_toolbar})
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.frameLayoutSale:
                callActivity(DespachoSaleOptionActivity.class.getName());
                break;
            case R.id.frameLayoutANF:
                callActivity(DespatchAnfActivity.class.getName());
                break;
            case R.id.btn_toolbar:
                showDialogOptions("Mensaje", "¿Está seguro que quiere cerrar su sesión?", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        cerrarSesionSerive();
                    }
                }, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                    }
                },"Aceptar","Cancelar");
                break;

        }
    }

    private void cerrarSesion(){
        AndroidApplication.getInstance().cerraSesion();

        Intent intent=new Intent(this,LogInActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
        finish();
    }

    private void initializeToolbar() {
        btnToolbar.setVisibility(View.VISIBLE);
        setSupportActionBar(toolbar);
        toolbar.setTitle(Constants.VALUES.EMPTY);
        setTitle(getString(R.string.home));
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        onBackPressed();
        return super.onOptionsItemSelected(item);
    }

    private void cerrarSesionSerive(){
        userService.opCerrarSesion(getApplicationContext(), new ServiceResponse<CerrarSesionResponse, String>() {
            @Override
            public void onResponse(CerrarSesionResponse response) {
                cerrarSesion();
            }

            @Override
            public void onFailure(String response) {
                showDialog(null, response, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });
            }
        });
    }

    private void goToDailySuccessfull() {
        Bundle args = new Bundle();
        args.putString(Constants.INTENT_KEY.TYPE_VIEW, String.valueOf(Constants.VALUES.CURRENT_STATUS_SIN_CIERRE_ANTERIOR));
        args.putBoolean(Constants.INTENT_KEY.OPCION_CIERRE_DIARIO, true);
        callActivity(MaintenanceDailyActivity_2.class.getName(), args);
    }

    private void showClosureDayMessage() {
        if (typeView == Constants.VALUES.CURRENT_STATUS_CERRADO) {
            showDialog("Mensaje", "Ya realizó el cierre diario, mañana podrá realizar la apertura.", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {

                }
            });
        } else {
            showDialogOptions(getString(R.string.title_message_are_you_sure), getString(R.string.message_closure_daily), new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    goToDailySuccessfull();
                }
            }, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                }
            }, getString(R.string.accept), getString(R.string.cancel));
        }
    }

    public void onClickClosureDay(View view) {
        showClosureDayMessage();
    }

    public void onClickActivityMovement(View view) {
        Bundle args = new Bundle();
        args.putInt(Constants.INTENT_KEY.TYPE_VIEW, typeView);

        callActivity(QueryDailyActivity.class.getName(), args);
    }

    @Override
    protected void onResume() {
        super.onResume();
        callServiceStatus();
        btnActivityMovement.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onClickActivityMovement(v);
            }
        });

        btnClosureDay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onClickClosureDay(v);
            }
        });
    }

    private long mBackPressed;
    private static final int TIME_INTERVAL = 2000;


    @Override
    public void onBackPressed() {
        if (mBackPressed + TIME_INTERVAL > System.currentTimeMillis()) {
            super.onBackPressed();
        } else {

        }
        mBackPressed = System.currentTimeMillis();
    }
}
