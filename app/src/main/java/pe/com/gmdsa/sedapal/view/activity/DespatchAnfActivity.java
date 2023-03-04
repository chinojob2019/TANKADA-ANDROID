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

import butterknife.BindView;
import butterknife.ButterKnife;
import pe.com.gmdsa.sedapal.R;
import pe.com.gmdsa.sedapal.domain.DTO.ServiceResponse;
import pe.com.gmdsa.sedapal.domain.ro.request.DespachoAnfConsultaRequest;
import pe.com.gmdsa.sedapal.domain.ro.response.DespachoAnfConsultaResponse;
import pe.com.gmdsa.sedapal.service.business.DespachoService;
import pe.com.gmdsa.sedapal.service.business.impl.DespachoServiceImpl;
import pe.com.gmdsa.sedapal.util.Constants;
import pe.com.gmdsa.sedapal.util.Util;

public class DespatchAnfActivity extends BaseActivity {

    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.txtNumber)
    EditText txtNumber;
    @BindView(R.id.btnRegister)
    Button btnRegister;
    DespachoService despachoService = new DespachoServiceImpl();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_despatch_anf);
        ButterKnife.bind(this);
        initializeToolbar();
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

    public void onClickEnter(View view) {
        if (validar()) {
            callServiceDespachoAnfConsulta();
        } else {
            showDialog(null, "La PLACA debe contener minimo 5 caracteres.", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {

                }
            });
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        txtNumber.setInputType(InputType.TYPE_CLASS_TEXT |
                InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);
        txtNumber.setFilters(new InputFilter[]{filterKey(),new InputFilter.LengthFilter(6),new InputFilter.AllCaps()});
    }

    private void callServiceDespachoAnfConsulta() {
        showProgressDialog();

        DespachoAnfConsultaRequest request = new DespachoAnfConsultaRequest(Util.getContent(txtNumber.getText().toString()));

        despachoService.opDespachoAnfConsulta(this, request, new ServiceResponse<DespachoAnfConsultaResponse, String>() {
            @Override
            public void onResponse(DespachoAnfConsultaResponse response) {
                dismissProgressDialog();
               if (response.getCodResult().equals("0000")){
                if (response.getData() != null) {

                    Bundle args = new Bundle();
                    args.putParcelableArrayList(Constants.INTENT_KEY.EVENT_LIST, response.getData().getEventosANF());
                    args.putString(Constants.INTENT_KEY.CODE, response.getData().getPlaca());
                    args.putString(Constants.INTENT_KEY.PUMP, response.getData().getDespacho().getSurtidor());
                    args.putString(Constants.INTENT_KEY.CODE, response.getData().getDespacho().getPlaca());
                    args.putInt(Constants.INTENT_KEY.TICKET, response.getData().getDespacho().getTicket());
                    args.putString(Constants.INTENT_KEY.CAPACITY, response.getData().getDespacho().getCapacidad());
                    args.putString(Constants.INTENT_KEY.OBSERVATION, response.getData().getDespacho().getObservaciones());
                    args.putInt(Constants.INTENT_KEY.ID_CAMION,response.getData().getIdCamion());

                    if (response.getData().getDespacho().getFlag()==null ||
                            response.getData().getDespacho().getFlag().toUpperCase().equals("NULL")) {
                        args.putInt(Constants.INTENT_KEY.FLAG,0);
                    }else{
                        args.putInt(Constants.INTENT_KEY.FLAG,Integer.parseInt(response.getData().getDespacho().getFlag()));
                    }

                    callActivity(DespatchAnfDetailActivity.class.getName(), args);
                }
               }else {
                   showDialog("", response.getMsg(), new DialogInterface.OnClickListener() {
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

    private boolean validar() {
        if (txtNumber.getText().toString().trim().length() > 4) {
            return true;
        }
        return false;
    }

    private InputFilter filterKey(){

        final String values="QWERTYUIOPÑLKJHGFDSAMNBVCXZ12345678790qwertyuiopasdfghjklñzxcvbnm";

        InputFilter filter = new InputFilter() {
            public CharSequence filter(CharSequence source, int start, int end,
                                       Spanned dest, int dstart, int dend) {
                for (int i = start; i < end; i++) {
                    if (!values.contains(String.valueOf(source.charAt(i)))) {
                        return "";
                    }
                }
                return null;
            }
        };

        return filter;
    }

}
