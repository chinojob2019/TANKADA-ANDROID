package pe.com.gmdsa.sedapal.view.activity;

import android.Manifest;
import android.app.Activity;
import android.content.ContentValues;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v7.widget.CardView;
import android.support.v7.widget.Toolbar;
import android.text.InputFilter;
import android.text.InputType;
import android.text.Spanned;
import android.util.Base64;
import android.view.KeyEvent;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.io.ByteArrayOutputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import pe.com.gmdsa.sedapal.R;
import pe.com.gmdsa.sedapal.domain.DTO.ServiceResponse;
import pe.com.gmdsa.sedapal.domain.model.User;
import pe.com.gmdsa.sedapal.domain.ro.request.CerrarSesionRequest;
import pe.com.gmdsa.sedapal.domain.ro.request.EventoDiarioRequest;
import pe.com.gmdsa.sedapal.domain.ro.response.CerrarSesionResponse;
import pe.com.gmdsa.sedapal.domain.ro.response.EventoDiarioResponse;
import pe.com.gmdsa.sedapal.domain.ro.response.SesionTokenResponse;
import pe.com.gmdsa.sedapal.service.business.EventoDiarioService;
import pe.com.gmdsa.sedapal.service.business.UserService;
import pe.com.gmdsa.sedapal.service.business.impl.EventoDiarioServiceImpl;
import pe.com.gmdsa.sedapal.service.business.impl.UserServiceImpl;
import pe.com.gmdsa.sedapal.util.Constants;
import pe.com.gmdsa.sedapal.view.AndroidApplication;

public class MaintenanceDailyActivity_2 extends BaseActivity {

    User user;
    int typeView = Constants.VALUES.CURRENT_STATUS_SIN_APERTURA;
    UserService userService = new UserServiceImpl();
    @BindView(R.id.txtDate)
    TextView txtDate;
    @BindView(R.id.txtSurtidor)
    TextView txtSurtidor;
    @BindView(R.id.txtQuantity)
    EditText txtQuantity;
    @BindView(R.id.ivPhoto)
    ImageView ivPhoto;
    @BindView(R.id.frameLayoutCameraImage)
    FrameLayout frameLayoutCameraImage;
    @BindView(R.id.ll_content)
    LinearLayout llContent;
    @BindView(R.id.pd_loading)
    ProgressBar pdLoading;
    @BindView(R.id.bt_reload)
    Button btReload;
    @BindView(R.id.ll_errorConection)
    LinearLayout llErrorConection;
    @BindView(R.id.ll_contentError)
    LinearLayout llContentError;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.btnClearImage)
    ImageButton btnClearImage;
    @BindView(R.id.btnCamera)
    ImageButton btnCamera;
    @BindView(R.id.ll_CameraButton)
    LinearLayout llCameraButton;
    @BindView(R.id.linearLayout9)
    LinearLayout linearLayout9;
    @BindView(R.id.btnRegister)
    Button btnRegister;
    @BindView(R.id.ll_Register)
    LinearLayout llRegister;
    @BindView(R.id.textView8)
    TextView textView8;
    @BindView(R.id.linearLayoutBottom)
    LinearLayout linearLayoutBottom;
    @BindView(R.id.btn_toolbar)
    TextView btnToolbar;

    boolean existePhoto;
    @BindView(R.id.ll_BottomButtons)
    LinearLayout llBottomButtons;
    private static final int PERMISSIONS_REQUEST_GALLERY = 998;
    @BindView(R.id.linearLayout2)
    LinearLayout linearLayout2;
    @BindView(R.id.cardView)
    CardView cardView;
    @BindView(R.id.imageView2)
    ImageView imageView2;
    @BindView(R.id.ib_cierre_diario)
    ImageButton ibCierreDiario;
    @BindView(R.id.tv_cierre_diario)
    TextView tvCierreDiario;
    @BindView(R.id.flConsulta)
    FrameLayout flConsulta;
    @BindView(R.id.flCierre)
    FrameLayout flCierre;

    EventoDiarioService eventoDiarioService = new EventoDiarioServiceImpl();
    @BindView(R.id.ib_cosultar)
    ImageButton ibCosultar;

    private long mBackPressed;
    private static final int TIME_INTERVAL = 2000;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maintenance_daily_2);
        ButterKnife.bind(this);
        callServiceStatus();
    }

    private boolean validateData() {
        if (txtQuantity.getText().toString().trim().length() > 0) {
            return true;
        }
        return false;
    }

    private void initView() {
        llContent.setVisibility(View.GONE);
        llContentError.setVisibility(View.VISIBLE);
        pdLoading.setVisibility(View.VISIBLE);
        llErrorConection.setVisibility(View.GONE);
        llRegister.setVisibility(View.GONE);
    }

    private void paintViewSuccess(String mensaje) {
        paintToolbar();
        changeOptionsButtons();
        pintarMensajeDependeDelEstatus(mensaje);


        llContent.setVisibility(View.VISIBLE);
        llContentError.setVisibility(View.GONE);
        llErrorConection.setVisibility(View.GONE);
        pdLoading.setVisibility(View.GONE);

        if (user != null) {
            txtDate.setText(user.getFecha());
            txtSurtidor.setText(user.getSurtidor());
        }
    }

    private void paintToolbar() {
        setSupportActionBar(toolbar);
        btnToolbar.setVisibility(View.VISIBLE);
        toolbar.setTitle(Constants.VALUES.EMPTY);
        if (typeView == Constants.VALUES.CURRENT_STATUS_SIN_APERTURA) {
            toolbar.setTitle(R.string.maintenance_daily);
        } else {
            if (typeView == Constants.VALUES.CURRENT_STATUS_SIN_CIERRE_ANTERIOR) {
                toolbar.setTitle(R.string.closure_daily);
            } else {
                if (typeView == Constants.VALUES.CURRENT_STATUS_APERTURADO) {
                    toolbar.setTitle(R.string.closure_daily);
                } else {
                    if (typeView == Constants.VALUES.CURRENT_STATUS_CERRADO) {
                        showDialog(null, "Ya se realizo el cierre diario. se mostrará la opción de consultas.", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                callActivity(OptionMenuActivity.class.getName());
                            }
                        });
                    }
                }
            }
        }
    }


    private void changeOptionsButtons() {
        if (existePhoto) {
            llCameraButton.setVisibility(View.GONE);
            llRegister.setVisibility(View.VISIBLE);
            llBottomButtons.setVisibility(View.GONE);
            frameLayoutCameraImage.setVisibility(View.VISIBLE);
            Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
        } else {
            llCameraButton.setVisibility(View.VISIBLE);
            llRegister.setVisibility(View.GONE);
            llBottomButtons.setVisibility(View.VISIBLE);
            frameLayoutCameraImage.setVisibility(View.GONE);
            Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(false);
        }
    }

    private void pintarMensajeDependeDelEstatus(String mensaje) {
        //pintamos el status
        if (typeView==Constants.VALUES.CURRENT_STATUS_SIN_CIERRE_ANTERIOR){
            showDialog(null, mensaje, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {

                }
            });
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        cleanPhoto();
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }

    @OnClick({R.id.btnCamera, R.id.btnClearImage, R.id.btnRegister, R.id.ib_cosultar, R.id.ib_cierre_diario,R.id.btn_toolbar})
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btnCamera:
                onClickCamera(v);
                break;
            case R.id.btnClearImage:
                //quita la foto
                cleanPhoto();
                break;
            case R.id.btnRegister:
                if (validateData()) {
                    showRegisterMessage();
                } else {
                    showErrorValidate();
                }
                break;
            case R.id.ib_cosultar:
                Bundle args = new Bundle();
                args.putInt(Constants.INTENT_KEY.TYPE_VIEW, typeView);
                callActivity(QueryDailyActivity.class.getName());
                break;

            case R.id.ib_cierre_diario:
                clickCierreDiario();
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

    private void clickCierreDiario() {
        if (typeView == Constants.VALUES.CURRENT_STATUS_SIN_APERTURA) {
            showDialog("Mensaje", "Para realizar el cierre diario, primero realice la apertura diaria.", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {

                }
            });
        } else {
            if (typeView == Constants.VALUES.CURRENT_STATUS_SIN_CIERRE_ANTERIOR) {
                showDialog("Mensaje", "Para continuar complete el formulario de cierre y registre", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });
            }
        }
    }

    private void showErrorValidate() {
        showDialog("Mensaje", "Ingrese los metros cúbicos", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
            }
        });
    }

    private void cleanPhoto() {
        existePhoto = false;
        ivPhoto.setImageResource(0);
        changeOptionsButtons();
    }

    boolean getCamera = false;

    private void showRegisterMessage() {
        String message = "";
        if (typeView == Constants.VALUES.CURRENT_STATUS_SIN_APERTURA) {
            message = getString(R.string.message_register);
        } else {
            message = getString(R.string.message_closure_daily);
        }

        showDialogOptions(Constants.VALUES.EMPTY, message, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                if (typeView == Constants.VALUES.CURRENT_STATUS_SIN_APERTURA) {
                    callServiceMaintenanceDaily(Constants.VALUES.EVENTO_APERTURAR);
                } else {
                    callServiceMaintenanceDaily(Constants.VALUES.EVENTO_CERRAR);
                }
            }
        }, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
            }
        }, getString(R.string.accept), getString(R.string.cancel)).setCancelable(false);

    }

    private void callServiceMaintenanceDaily(int event) {
        dismissProgressDialog();
        showProgressDialog().setCancelable(false);
        String fotoBase64="";
        if (existePhoto){
            fotoBase64=cargarFoto(capturedImageFilePath);
        }

        EventoDiarioRequest request;

        request=new EventoDiarioRequest(txtQuantity.getText().toString(),fotoBase64,event);

        eventoDiarioService.opRegistrarEventoDiario(this, request, new ServiceResponse<EventoDiarioResponse, String>() {
            @Override
            public void onResponse(EventoDiarioResponse response) {
                dismissProgressDialog();
                Bundle args = new Bundle();
                if (response.getCodResult().equals(Constants.SERVICES.SUCCESSFUL_CODE)) {

                    args.putString(Constants.INTENT_KEY.MESSAGE, response.getMsg());
                    args.putString(Constants.INTENT_KEY.DATE, response.getData().getFecha());
                    args.putString(Constants.INTENT_KEY.TIME, response.getData().getHora());
                    args.putString(Constants.INTENT_KEY.PUMP, response.getData().getSurtidor());
                    args.putString(Constants.INTENT_KEY.M3, response.getData().getM3());

                    if (typeView==0) {
                        args.putInt(Constants.INTENT_KEY.TYPE_VIEW, Constants.VALUES.SALES.OPENING);
                    }else {
                        args.putInt(Constants.INTENT_KEY.TYPE_VIEW, Constants.VALUES.SALES.CLOSURE);
                    }

                    callActivity(DespatchSaleSuccessfulActivity.class.getName(), args);
                    finish();
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
                showDialog(Constants.VALUES.EMPTY, response, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        callServiceStatus();
                    }
                });
            }
        });
    }
    public void onClickCamera(View v) {
        getCamera = true;
        checkPermission();
    }

    private void checkPermission() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            String[] PERMISSIONS = {Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.CAMERA};
            if (!hasPermissions(this, PERMISSIONS)) {
                requestPermissions(PERMISSIONS, PERMISSIONS_REQUEST_GALLERY);
            } else {
                goToCamera();
            }
        } else {
            goToCamera();
        }
    }

    private boolean hasPermissions(Context context, String... permissions) {
        if (context != null && permissions != null) {
            for (String permission : permissions) {
                if (ActivityCompat.checkSelfPermission(context, permission) != PackageManager.PERMISSION_GRANTED) {
                    return false;
                }
            }
        }
        return true;
    }
    Uri mCapturedImageURI;
    private void goToCamera() {

        String fileName = "temp.jpg";
        ContentValues values = new ContentValues();
        values.put(MediaStore.Images.Media.TITLE, fileName);
        mCapturedImageURI = getContentResolver().insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, values);


        Intent cameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        cameraIntent.putExtra(MediaStore.EXTRA_OUTPUT, mCapturedImageURI);
        startActivityForResult(cameraIntent, PERMISSIONS_REQUEST_GALLERY);
    }

    Bitmap bytesImage;
    String capturedImageFilePath;
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == PERMISSIONS_REQUEST_GALLERY && resultCode == Activity.RESULT_OK) {


            String[] projection = { MediaStore.Images.Media.DATA};
            Cursor cursor = managedQuery(mCapturedImageURI, projection, null, null, null);
            int column_index_data = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
            cursor.moveToFirst();
            capturedImageFilePath = cursor.getString(column_index_data);
            Glide.with(ivPhoto.getContext()).load(capturedImageFilePath)
                    .into(ivPhoto);
            existePhoto = true;

        } else {
            existePhoto = false;
        }
        changeOptionsButtons();
    }

    private void callServiceStatus() {
        dismissProgressDialog();

        showProgressDialog().setCancelable(false);
        userService.opSesionToken(this, new ServiceResponse<SesionTokenResponse, String>() {
            @Override
            public void onResponse(SesionTokenResponse response) {
                dismissProgressDialog();

                if (response.getData() != null) {
                    if (response.getCodResult().equals("0000")){
                        user = response.getData();
                        typeView = user.getEstado();
                        paintViewSuccess(response.getData().getMensaje());
                    }else {
                        if (response.getCodResult().equals("0001")){
                            showDialog("Mensaje", response.getMsg(), new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    callActivity(LogInActivity.class.getName());
                                    finish();
                                }
                            });
                        }
                    }
                } else {
                    showDialog("", "Error del servicio", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            sindata();
                        }
                    });
                }
            }

            @Override
            public void onFailure(String response) {
                dismissProgressDialog();
                sindata();
            }
        });
    }

    private void sindata(){
        paintViewSuccess("");
    }
    int count= -1 ;

    @Override
    protected void onResume() {
        super.onResume();
        txtQuantity.setFilters(new InputFilter[]{new DecimalDigitsInputFilter(10,2)});

        if (!getCamera) {
            initView();
            callServiceStatus();
        }
        getCamera = false;
    }

    public class DecimalDigitsInputFilter implements InputFilter {

        Pattern mPattern;

        public DecimalDigitsInputFilter(int digitsBeforeZero,int digitsAfterZero) {
            mPattern=Pattern.compile("[0-9]{0," + (digitsBeforeZero-1) + "}+((\\.[0-9]{0," + (digitsAfterZero-1) + "})?)||(\\.)?");
        }

        @Override
        public CharSequence filter(CharSequence source, int start, int end, Spanned dest, int dstart, int dend) {

            Matcher matcher=mPattern.matcher(dest);
            if(!matcher.matches())
                return "";
            return null;
        }
    }


    private void cerrarSesion(){
        AndroidApplication.getInstance().cerraSesion();
        Intent intent=new Intent(this,LogInActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
        finish();
    }

    public String cargarFoto(String path_foto) {
        String foto_str = "";
        String encodedString = "";
        if (path_foto != null) {
            try {

                InputStream inputStream = new FileInputStream(path_foto);
                byte[] bytes;
                byte[] buffer = new byte[2048];
                int bytesRead;
                ByteArrayOutputStream output = new ByteArrayOutputStream();
                try {
                    while ((bytesRead = inputStream.read(buffer)) != -1) {
                        output.write(buffer, 0, bytesRead);
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
                bytes = output.toByteArray();

                encodedString = Base64.encodeToString(bytes, Base64.DEFAULT);

            } catch (Exception e) {
                e.printStackTrace();
                return encodedString;
            }
        }
        //return  foto_str;
        return encodedString;
    }
}