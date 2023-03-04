package pe.com.gmdsa.sedapal.view.activity;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.InputFilter;
import android.text.InputType;
import android.text.Spanned;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import pe.com.gmdsa.sedapal.R;
import pe.com.gmdsa.sedapal.domain.DTO.ServiceResponse;
import pe.com.gmdsa.sedapal.domain.model.ItemDespatchSale;
import pe.com.gmdsa.sedapal.domain.ro.request.DespachoVentasRequest;
import pe.com.gmdsa.sedapal.domain.ro.response.DespachoVentasResponse;
import pe.com.gmdsa.sedapal.service.business.DespachoService;
import pe.com.gmdsa.sedapal.service.business.impl.DespachoServiceImpl;
import pe.com.gmdsa.sedapal.util.Constants;

public class DespatchSaleActivity extends BaseActivity {

    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.txtDespatch)
    EditText txtDespatch;
    @BindView(R.id.txtNumber)
    EditText txtNumber;

    int selectedOption = Constants.VALUES.RADIO_BUTTON;
    List<String> despatchList = new ArrayList<>();
    @BindView(R.id.btnRegister)
    Button btnRegister;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_despatch_sale);
        ButterKnife.bind(this);
        initializeToolbar();
        radioButtonOptions();
    }

    private void initializeToolbar() {
        setSupportActionBar(toolbar);
        toolbar.setTitle(Constants.VALUES.EMPTY);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        setTitle(getString(R.string.despatch_sale));
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        onBackPressed();
        return super.onOptionsItemSelected(item);
    }



    public void onClickEnter(View view) {
        Bundle args = new Bundle();
        if (validarSeleccion()) {
            if (validarCodigo()) {
                args.putString(Constants.INTENT_KEY.MESSAGE, despatchList.get(selectedOption));
                args.putInt(Constants.INTENT_KEY.TYPE_DESPATCH, (selectedOption + 2));
                args.putString(Constants.INTENT_KEY.CODE, txtNumber.getText().toString().trim());


                callActivity(DespatchSaleListActivity.class.getName(), args);
            }
        }

    }

    private void radioButtonOptions() {
        String[] despatchType;
        despatchType = getResources().getStringArray(R.array.despatch_type_array);
        despatchList = Arrays.asList(despatchType);
    }


    int selectedOption_aux;
    public void onClickDespatchType(View view) {
        selectedOption_aux=selectedOption;
        showDialogRadioOptions(getString(R.string.despatch_for_each), despatchList, selectedOption, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                selectedOption = which;
            }
        }, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

                if (selectedOption!=-1){
                    txtDespatch.setText(despatchList.get(selectedOption));
                    txtNumber.setText("");
                    changeKeyBoard(selectedOption);
                }
            }
        }, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                selectedOption=selectedOption_aux;
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        changeKeyBoard(selectedOption);
    }

    private void changeKeyBoard(int tipo) {
        switch (tipo) {
            case -1:
                txtNumber.setFilters(new InputFilter[]{filterKey(tipo),new InputFilter.LengthFilter(0)});
                break;
            case 0:
                txtNumber.setInputType(InputType.TYPE_CLASS_TEXT |
                        InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);
                txtNumber.setFilters(new InputFilter[]{filterKey(tipo),new InputFilter.LengthFilter(6),new InputFilter.AllCaps()});
                break;

            case 1:
                txtNumber.setInputType(InputType.TYPE_CLASS_NUMBER);
                txtNumber.setFilters(new InputFilter[]{filterKey(tipo),new InputFilter.LengthFilter(12)});
                break;
            case 2:
                txtNumber.setInputType(InputType.TYPE_CLASS_NUMBER);
                txtNumber.setFilters(new InputFilter[]{filterKey(tipo),new InputFilter.LengthFilter(11)});

                break;
            case 3:
                txtNumber.setInputType(InputType.TYPE_CLASS_NUMBER);
                txtNumber.setFilters(new InputFilter[]{filterKey(tipo),new InputFilter.LengthFilter(8)});

                break;
        }
    }

    private InputFilter filterKey(int tipo){

        String values="";
        switch (tipo){
            case -1:
                values="*";
                break;
            case 0:
                values="QWERTYUIOPÑLKJHGFDSAMNBVCXZ12345678790qwertyuiopasdfghjklñzxcvbnm";
                break;
            case 1:
                values="12345678790";
                break;
            case 2:
                values="12345678790";
                break;
            case 3:
                values="12345678790";
                break;
        }
        final String data=values;
        InputFilter filter = new InputFilter() {
            public CharSequence filter(CharSequence source, int start, int end,
                                       Spanned dest, int dstart, int dend) {
                for (int i = start; i < end; i++) {
                    if (!data.contains(String.valueOf(source.charAt(i)))) {
                        return "";
                    }
                }
                return null;
            }
        };

        return filter;
    }


    private boolean validarSeleccion() {
        if (selectedOption != -1) {
            switch (selectedOption) {
                case 0:
                    if (txtNumber.getText().toString().length() > 4) {
                        return true;
                    } else {
                        showDialog(null, "La PLACA debe contener minimo 5 caracteres.", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {

                            }
                        });
                        return false;
                    }
                case 1:
                    if (txtNumber.getText().toString().length() <= 12) {
                        return true;
                    } else {
                        showDialog(null, "El TICKET debe contener como maximo 12 dígitos", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {

                            }
                        });
                        return false;
                    }
                case 2:
                    if (txtNumber.getText().toString().length() == 11) {
                        return true;
                    } else {
                        showDialog(null, "El RUC debe contener 11 dígitos", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {

                            }
                        });
                        return false;
                    }

                case 3:
                    if (txtNumber.getText().toString().length() == 8) {
                        return true;
                    } else {
                        showDialog(null, "El DNI debe contener 8 dígitos", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {

                            }
                        });
                        return false;
                    }

                default:
                    return true;

            }

        } else {
            showDialog("Mensaje", "Por favor seleccione un tipo de despacho.", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {

                }
            });
            return false;
        }
    }

    private boolean validarCodigo() {
        if (txtNumber.getText().toString().trim().length() > 0) {
            return true;
        } else {
            showDialog("Mensaje", "Por favor ingrese el dato a buscar.", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {

                }
            });
            return false;
        }
    }


    DespachoService despachoService = new DespachoServiceImpl();
    private void callServiceDespachoQR(final String codigo) {
        showProgressDialog();

        final DespachoVentasRequest request = new DespachoVentasRequest(1, codigo);
        despachoService.opDespachoVentasConsulta(this, request, new ServiceResponse<DespachoVentasResponse, String>() {
            @Override
            public void onResponse(DespachoVentasResponse response) {
                dismissProgressDialog();
                if (response.getData() != null) {
                    if (response.getData().getDespachos()!=null){
                        if (response.getData().getDespachos().size()>0){

                            ItemDespatchSale obj = response.getData().getDespachos().get(0);
                            Bundle args = new Bundle();
                            args.putInt(Constants.INTENT_KEY.TYPE_DESPATCH, 1);
                            args.putString(Constants.INTENT_KEY.CODE, codigo);

                            args.putString(Constants.INTENT_KEY.PUMP, obj.getSurtidor());
                            args.putString(Constants.INTENT_KEY.LICENCE, obj.getPlaca());
                            args.putInt(Constants.INTENT_KEY.TICKET, obj.getTicket());
                            args.putString(Constants.INTENT_KEY.DOCUMENT, obj.getDocumento());
                            args.putString(Constants.INTENT_KEY.CAPACITY, obj.getCapacidad());
                            args.putString(Constants.INTENT_KEY.OBSERVATION, obj.getObservaciones());
                            args.putInt(Constants.INTENT_KEY.ID_DESPACHO, obj.getIdDespacho());

                            callActivity(DespatchSaleDetailActivity.class.getName(), args);
                        }
                    }
                    else {
                        if (response.getMsg()!=null){
                            showDialog(null, response.getMsg(), new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {

                                }
                            });
                        }
                        else {
                            showDialog(null, "No se encontro el ticket.", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {

                                }
                            });
                        }
                    }
                }else {
                    showDialog(null, response.getMsg(), new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {

                        }
                    });
                }
            }

            @Override
            public void onFailure(String response) {
                dismissProgressDialog();
            }
        });
    }

}

