package pe.com.gmdsa.sedapal.view.activity;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import pe.com.gmdsa.sedapal.R;
import pe.com.gmdsa.sedapal.domain.DTO.ServiceResponse;
import pe.com.gmdsa.sedapal.domain.model.ItemDespatchSale;
import pe.com.gmdsa.sedapal.domain.ro.request.DespachoVentasRequest;
import pe.com.gmdsa.sedapal.domain.ro.response.DespachoVentasResponse;
import pe.com.gmdsa.sedapal.service.business.DespachoService;
import pe.com.gmdsa.sedapal.service.business.impl.DespachoServiceImpl;
import pe.com.gmdsa.sedapal.util.Constants;

public class DespatchSaleQRActivity extends BaseActivity {

    @BindView(R.id.toolbar)
    Toolbar toolbar;
    String codigoQRObtenido;
    DespachoService despachoService = new DespachoServiceImpl();
    private List<ItemDespatchSale> items = new ArrayList<>();
    private int typeDespatch = 1; //tipo  QR


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_despatch_sale_qr);
        ButterKnife.bind(this);
        initializeToolbar();
    }

    private void initializeToolbar() {
        setSupportActionBar(toolbar);
        toolbar.setTitle(Constants.VALUES.EMPTY);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        setTitle(getString(R.string.despatch_qr));
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        onBackPressed();
        return super.onOptionsItemSelected(item);
    }

    public void onClickQR(View view) {
        Intent intent = CaptureQRActivity.getCallingIntent(this);
        startActivityForResult(intent, Constants.IDENTIF_LECTOR_QR);
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == Constants.IDENTIF_LECTOR_QR && resultCode == RESULT_OK) {
            codigoQRObtenido = data.getStringExtra(Constants.INTENT_KEY.CODE_QR);
           // toastShow(codigoQRObtenido);
            callServiceDespachoVentasConsulta(typeDespatch, codigoQRObtenido);
        }else {
            //toastShow("");
            //callServiceDespachoVentasConsulta(typeDespatch, "5");
        }

    }

    private void callServiceDespachoVentasConsulta(final int tipo, String codigo) {
        dismissProgressDialog();
        showProgressDialog();
        items.clear();
        final DespachoVentasRequest request = new DespachoVentasRequest(tipo, codigo);
        despachoService.opDespachoVentasConsulta(this, request, new ServiceResponse<DespachoVentasResponse, String>() {
            @Override
            public void onResponse(DespachoVentasResponse response) {
                dismissProgressDialog();
                if (response.getCodResult().equals(Constants.SERVICES.SUCCESSFUL_CODE)) {
                    if (response.getData() != null) {
                        items.addAll(response.getData().getDespachos());
                        pintarData();
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
                showDialog(null, response, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });
            }
        });
    }

    private void pintarData() {
        if (items.size() > 0) {
            ItemDespatchSale obj = items.get(0);
            Bundle args = new Bundle();
            args.putInt(Constants.INTENT_KEY.TYPE_DESPATCH, 1);
            args.putString(Constants.INTENT_KEY.CODE, codigoQRObtenido);

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
}
