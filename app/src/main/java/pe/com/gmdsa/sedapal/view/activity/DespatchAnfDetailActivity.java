package pe.com.gmdsa.sedapal.view.activity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import pe.com.gmdsa.sedapal.R;
import pe.com.gmdsa.sedapal.domain.DTO.ServiceResponse;
import pe.com.gmdsa.sedapal.domain.model.Despacho;
import pe.com.gmdsa.sedapal.domain.model.Evento;
import pe.com.gmdsa.sedapal.domain.ro.request.DespachoAnfConfirmarRequest;
import pe.com.gmdsa.sedapal.domain.ro.response.DespachoAnfConfirmarResponse;
import pe.com.gmdsa.sedapal.service.business.DespachoService;
import pe.com.gmdsa.sedapal.service.business.impl.DespachoServiceImpl;
import pe.com.gmdsa.sedapal.util.Constants;
import pe.com.gmdsa.sedapal.util.Util;
import pe.com.gmdsa.sedapal.view.AndroidApplication;

@SuppressWarnings("all")
public class DespatchAnfDetailActivity extends BaseActivity {

    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.text_toolbar_title)
    TextView textToolbarTitle;
    @BindView(R.id.btn_toolbar)
    TextView btnToolbar;
    @BindView(R.id.toolbarLayout)
    AppBarLayout toolbarLayout;
    @BindView(R.id.linearLayout3)
    LinearLayout linearLayout3;
    @BindView(R.id.txtSurtidor)
    TextView txtSurtidor;
    @BindView(R.id.txtPlate)
    TextView txtPlate;
    @BindView(R.id.txtTicket)
    TextView txtTicket;
    @BindView(R.id.txtCapacity)
    TextView txtCapacity;
    @BindView(R.id.txtEvent)
    EditText txtEvent;
    @BindView(R.id.txtObservations)
    TextView txtObservations;
    @BindView(R.id.btnRegister)
    Button btnRegister;
    @BindView(R.id.linearLayoutBottom)
    LinearLayout linearLayoutBottom;

    @BindView(R.id.llVolumen)
    LinearLayout llVolumen;
    @BindView(R.id.llLectura)
    LinearLayout llLectura;
    @BindView(R.id.txtVolumen)
    EditText txtVolumen;
    @BindView(R.id.txtLecturaInicial)
    EditText txtLecturaInicial;
    @BindView(R.id.txtLecturaFinal)
    EditText txtLecturaFinal;
    @BindView(R.id.checkCapacidad)
    Switch checkCapacidad;
    int Flag = 0;
    String cap = "";

    DespachoService despachoService = new DespachoServiceImpl();
    int idCamion;
    int idEvento;

    List<Evento> eventos;
    List<String> stringList = new ArrayList<>();

    int posicionInicial = -1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_despatch_anf_detail);
        ButterKnife.bind(this);

        initializeToolbar();
        initView();
    }

    private void initView() {

        Bundle args = getIntent().getExtras();
        if (args != null) {
            eventos = args.getParcelableArrayList(Constants.INTENT_KEY.EVENT_LIST);
            idCamion = args.getInt(Constants.INTENT_KEY.ID_CAMION);
            cap = args.getString(Constants.INTENT_KEY.CAPACITY).toUpperCase().replace("M3", "").trim();

            txtSurtidor.setText(Util.getContent(args.getString(Constants.INTENT_KEY.PUMP)));
            txtPlate.setText(Util.getContent(args.getString(Constants.INTENT_KEY.CODE)));
            txtTicket.setText(Util.getContent(String.valueOf(args.getInt(Constants.INTENT_KEY.TICKET))));
            txtCapacity.setText(String.valueOf(args.getString(Constants.INTENT_KEY.CAPACITY)));
            txtObservations.setText(Util.getContent(args.getString(Constants.INTENT_KEY.OBSERVATION)));

            if (args.getInt(Constants.INTENT_KEY.FLAG) == 1) {
                Flag = 0;
                checkCapacidad.setChecked(true);
                llVolumen.setVisibility(View.VISIBLE);
                llLectura.setVisibility(View.GONE);
                txtLecturaInicial.setText("0");
                txtLecturaFinal.setText("0");
                txtVolumen.setText("0");
                SaveLecturaFinal(false);
            } else {
                Flag = 1;
                checkCapacidad.setChecked(false);
                llVolumen.setVisibility(View.GONE);
                llLectura.setVisibility(View.VISIBLE);
                txtLecturaInicial.setText("0");
                txtLecturaFinal.setText("0");
                txtVolumen.setText("0");
                SaveLecturaFinal(true);
            }
        }

        if (eventos != null) {
            for (Evento obj : eventos) {
                stringList.add(obj.getDescripcion());
            }
        }

        checkCapacidad.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    Flag = 1;
                    llVolumen.setVisibility(View.VISIBLE);
                    llLectura.setVisibility(View.GONE);
                    txtLecturaInicial.setText("0");
                    txtLecturaFinal.setText("0");
                    SaveLecturaFinal(false);
                } else {
                    Flag = 0;
                    llVolumen.setVisibility(View.GONE);
                    llLectura.setVisibility(View.VISIBLE);
                    txtVolumen.setText("0");
                    SaveLecturaFinal(true);
                }
            }
        });

        txtVolumen.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int ij, int i1, int i2) {
            }

            @Override
            public void onTextChanged(CharSequence cs, int ij, int i1, int i2) {

                if (cs.length() > 0) {
                    double vol = Double.parseDouble(String.valueOf(cs));
                    double capp = Double.parseDouble(cap);

                    if (vol > capp) {
                        Toast.makeText(getApplicationContext(), "Volumen no puede ser mayor a " + cap, Toast.LENGTH_SHORT).show();
                        txtVolumen.setText("");
                    }

                }
            }

            @Override
            public void afterTextChanged(Editable editable) {
            }
        });

        txtLecturaFinal.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int ij, int i1, int i2) {
            }

            @Override
            public void onTextChanged(CharSequence cs, int ij, int i1, int i2) {

                if (cs.length() > 0) {
                    if (txtLecturaInicial.getText().toString().length() != 0) {
                        double lecturaInicial = Double.parseDouble(txtLecturaInicial.getText().toString());
                        double lecturaFinal = Double.parseDouble(String.valueOf(cs));

                        if (lecturaInicial > lecturaFinal) {
                            Toast.makeText(getApplicationContext(), "Lectura inicial debe ser menor a Lectura final" + cap, Toast.LENGTH_SHORT).show();
                        } else {
                            double lect = lecturaFinal - lecturaInicial;
                            double capp = Double.parseDouble(cap);
                            if (lect > capp) {
                                Toast.makeText(getApplicationContext(), "La diferencia de las lecturas no puede ser mayor a " + cap, Toast.LENGTH_SHORT).show();
                                txtLecturaFinal.setText("");
                            }
                        }
                    } else {
                        Toast.makeText(getApplicationContext(), "Verificar Lectura inicial " + cap, Toast.LENGTH_SHORT).show();
                    }
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {
            }
        });

    }

    public void SaveLecturaFinal(boolean dato) {
        if (dato) {
            if (AndroidApplication.getInstance().getLecturaFinal().toString().length() != 0) {
                txtLecturaInicial.setText(AndroidApplication.getInstance().getLecturaFinal());
            }
        } else {
            txtLecturaInicial.setText("0");
        }

    }

    private void initializeToolbar() {
        setSupportActionBar(toolbar);
        toolbar.setTitle(Constants.VALUES.EMPTY);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        setTitle(getString(R.string.despatch_anf));
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        onBackPressed();
        return super.onOptionsItemSelected(item);
    }

    private void goToSalesSuccessfull(Despacho despacho) {

        Bundle args = new Bundle();
        args.putInt(Constants.INTENT_KEY.TYPE_VIEW, Constants.VALUES.SALES.ANF);
        args.putString(Constants.INTENT_KEY.PUMP, despacho.getSurtidor());
        args.putString(Constants.INTENT_KEY.EVENT, despacho.getEvento());
        args.putString(Constants.INTENT_KEY.LICENCE, despacho.getPlaca());
        //args.putString(Constants.INTENT_KEY.CAPACITY, String.valueOf(despacho.getCapacidad()));

        if (checkCapacidad.isChecked()) {
            args.putString(Constants.INTENT_KEY.CAPACITY, String.valueOf(txtVolumen.getText().toString()));
        } else {
            double lecturaInicial = Double.parseDouble(txtLecturaInicial.getText().toString());
            double lecturaFinal = Double.parseDouble(txtLecturaFinal.getText().toString());
            double lect = lecturaFinal - lecturaInicial;
            args.putString(Constants.INTENT_KEY.CAPACITY, String.valueOf(lect));
        }

        callActivity(DespatchSaleSuccessfulActivity.class.getName(), args);
        finish();
    }

    private void showDespatchMessage() {

        showDialogOptions(Constants.VALUES.EMPTY, getString(R.string.message_anf), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                AndroidApplication.getInstance().setLecturaFinal(txtLecturaFinal.getText().toString());
                callServiceDespatchConfirmarANF();
            }
        }, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
            }
        }, getString(R.string.accept), getString(R.string.cancel));
    }

    private void showDespatchMessageDynamic(String mensaje) {
        showDialog(null, mensaje, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });
    }

    public void onClickDespatch(View view) {

        if (validar()) {
            if (checkCapacidad.isChecked()) {
                if (txtVolumen.getText().length() != 0) {
                    if (txtVolumen.getText().toString().equals("0.0") || txtVolumen.getText().toString().equals("0")) {
                        showDespatchMessageDynamic("Verificar el volumen");
                    } else {
                        showDespatchMessage();
                    }
                } else {
                    showDespatchMessageDynamic("Verificar el volumen");
                }
            } else {
                if (txtLecturaInicial.getText().length() != 0) {
                    if (txtLecturaFinal.getText().length() != 0) {

                        double lecturaInicial = Double.parseDouble(txtLecturaInicial.getText().toString());
                        double lecturaFinal = Double.parseDouble(txtLecturaFinal.getText().toString());

                        double lect = lecturaFinal - lecturaInicial;
                        double capp = Double.parseDouble(cap);
                        if (lect > capp) {
                            showDespatchMessageDynamic("La diferencia de las lecturas no puede ser mayor a " + cap);
                        } else if (lect < 0 || lect < 0.0) {
                            showDespatchMessageDynamic("La diferencia de las lecturas no puede ser menor a 0");
                        } else if (lect == 0 || lect == 0.0) {
                            showDespatchMessageDynamic("La diferencia de las lecturas no puede ser 0");
                        } else {
                            showDespatchMessage();
                        }
                    } else {
                        showDespatchMessageDynamic("Verificar lectura final");
                    }
                } else {
                    showDespatchMessageDynamic("Verificar lectura inicial");
                }
            }
            //showDespatchMessage();
        }
    }

    @OnClick({R.id.txtEvent})
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.txtEvent:
                clickEvento();
                break;
        }
    }

    int selectedOption_aux;

    private void clickEvento() {
        selectedOption_aux = posicionInicial;

        showDialogRadioOptions("Seleccionar evento", stringList, posicionInicial, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                posicionInicial = which;
            }
        }, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                if (posicionInicial != -1) {
                    txtEvent.setText(eventos.get(posicionInicial).getDescripcion());
                    idEvento = eventos.get(posicionInicial).getCodigo();
                }
            }
        }, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                posicionInicial = selectedOption_aux;
            }
        });
    }

    private void callServiceDespatchConfirmarANF() {
        showProgressDialog();
        //DespachoAnfConfirmarRequest request = new DespachoAnfConfirmarRequest(idCamion, idEvento);
        int flagg = 1;
        if (checkCapacidad.isChecked()) {
            flagg = 1;
        } else {
            flagg = 0;
        }

        DespachoAnfConfirmarRequest request = new DespachoAnfConfirmarRequest(
                idCamion,
                idEvento,
                flagg,
                Integer.parseInt(txtVolumen.getText().toString()),
                Integer.parseInt(txtLecturaFinal.getText().toString()),
                Integer.parseInt(txtLecturaInicial.getText().toString()));

        despachoService.opDespachoAnfConfirmar(this, request, new ServiceResponse<DespachoAnfConfirmarResponse, String>() {
            @Override
            public void onResponse(DespachoAnfConfirmarResponse response) {
                dismissProgressDialog();
                if (response.getData() != null) {
                    goToSalesSuccessfull(response.getData().getDespacho());
                }
            }

            @Override
            public void onFailure(String response) {
                dismissProgressDialog();
            }
        });
    }

    private boolean validar() {
        if (posicionInicial == -1) {
            showDialog(null, "Seleccione un evento para realizar el despacho", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {

                }
            });

            return false;
        } else {
            return true;
        }
    }
}
