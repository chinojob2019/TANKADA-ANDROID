package pe.com.gmdsa.sedapal.view.activity;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import java.util.Objects;

import butterknife.BindView;
import butterknife.ButterKnife;
import pe.com.gmdsa.sedapal.R;
import pe.com.gmdsa.sedapal.domain.DTO.ServiceResponse;
import pe.com.gmdsa.sedapal.domain.ro.request.DespachoVentaConfirmarRequest;
import pe.com.gmdsa.sedapal.domain.ro.response.DespachoVentaConfirmarResponse;
import pe.com.gmdsa.sedapal.service.business.DespachoService;
import pe.com.gmdsa.sedapal.service.business.impl.DespachoServiceImpl;
import pe.com.gmdsa.sedapal.util.Constants;
import pe.com.gmdsa.sedapal.util.Util;

public class DespatchSaleDetailActivity extends BaseActivity {

    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.txtSurtidor)
    TextView txtSurtidor;
    @BindView(R.id.txtPlate)
    TextView txtPlate;
    @BindView(R.id.txtTicket)
    TextView txtTicket;
    @BindView(R.id.txtCapacity)
    TextView txtCapacity;
    @BindView(R.id.txtObservations)
    TextView txtObservations;

    int typeDespatch;
    String code;

    int idDespacho;

    DespachoService despachoService = new DespachoServiceImpl();
    @BindView(R.id.txtTipo)
    TextView txtTipo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_despatch_sale_detail);
        ButterKnife.bind(this);
        initializeToolbar();
        initArgs();
    }

    private void initializeToolbar() {
        setSupportActionBar(toolbar);
        toolbar.setTitle(Constants.VALUES.EMPTY);
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
        setTitle(getString(R.string.despatch_sale));
    }

    private void initArgs() {
        Bundle args = getIntent().getExtras();
        if (args != null) {
            typeDespatch = args.getInt(Constants.INTENT_KEY.TYPE_DESPATCH);
            code = args.getString(Constants.INTENT_KEY.CODE);
            idDespacho = args.getInt(Constants.INTENT_KEY.ID_DESPACHO);

            txtTipo.setText(tipoString(typeDespatch));
            txtSurtidor.setText(Util.getContent(args.getString(Constants.INTENT_KEY.PUMP)));
            txtPlate.setText(Util.getContent(args.getString(Constants.INTENT_KEY.LICENCE)));
            txtTicket.setText(Util.getContent(""+args.getInt(Constants.INTENT_KEY.TICKET)));
            txtCapacity.setText(Util.getContent(args.getString(Constants.INTENT_KEY.CAPACITY)));
            txtObservations.setText(Util.getContent(args.getString(Constants.INTENT_KEY.OBSERVATION)));


        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        onBackPressed();
        return super.onOptionsItemSelected(item);
    }

    private void goToSalesSuccessfull() {

        callServiceDespatchConfirm();
    }

    private void showDespatchMessage() {
        showDialogOptions(Constants.VALUES.EMPTY, "¿Está seguro de realizar el despacho?", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                goToSalesSuccessfull();
            }
        }, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
            }
        }, getString(R.string.accept), getString(R.string.cancel));
    }

    public void onClickDespatch(View view) {
        showDespatchMessage();
    }

    private void callServiceDespatchConfirm() {
        final DespachoVentaConfirmarRequest request = new DespachoVentaConfirmarRequest(idDespacho);
        despachoService.opDespachoVentaConfirmar(this, request, new ServiceResponse<DespachoVentaConfirmarResponse, String>() {
            @Override
            public void onResponse(DespachoVentaConfirmarResponse response) {
                Bundle args = getIntent().getExtras();
                args.putInt(Constants.INTENT_KEY.TYPE_VIEW, Constants.VALUES.SALES.NORMAL);
                args.putString(Constants.INTENT_KEY.PUMP, response.getData().getDespacho().getSurtidor());
                args.putString(Constants.INTENT_KEY.TYPE_DESPATCH,response.getData().getDespacho().getTipoDespacho());
                args.putString(Constants.INTENT_KEY.LICENCE,response.getData().getDespacho().getPlaca());
                args.putInt(Constants.INTENT_KEY.TICKET,response.getData().getDespacho().getTicket());
                args.putString(Constants.INTENT_KEY.CAPACITY,response.getData().getDespacho().getCapacidad());

                callActivity(DespatchSaleSuccessfulActivity.class.getName(), args);
                finish();
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

    private String tipoString(int tipo) {
        switch (tipo) {
            case 1:
                return "Despacho según QR";
            case 2:
                return "Despacho según Nro. de Placa";
            case 3:
                return "Despacho según Nro. de Ticket";
            case 4:
                return "Despacho según RUC";
            case 5:
                return "Despacho según DNI";
            default:
                return "";
        }
    }
}
