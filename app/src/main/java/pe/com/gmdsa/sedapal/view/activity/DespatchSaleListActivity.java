package pe.com.gmdsa.sedapal.view.activity;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

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
import pe.com.gmdsa.sedapal.view.adapter.DespatchSaleAdapter;

public class DespatchSaleListActivity extends BaseActivity implements DespatchSaleAdapter.OnClickItem {

    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.textDespatchAccordingTo)
    TextView textDespatchAccordingTo;
    @BindView(R.id.recycler)
    RecyclerView recycler;

    List<ItemDespatchSale> list = new ArrayList<>();
    DespatchSaleAdapter adapter;
    DespachoService despachoService = new DespachoServiceImpl();
    @BindView(R.id.ll_no_elementos)
    LinearLayout llNoElementos;

    private int tipo_despacho;
    private String numero_codigo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_despatch_sale_list);
        ButterKnife.bind(this);
        initializeToolbar();
        initializeView();
        initializeAdapter();

    }

    @Override
    protected void onResume() {
        super.onResume();
        callServiceDespatchSales();
    }

    private void initializeAdapter() {
        adapter = new DespatchSaleAdapter(list, this, this);
        recycler.setHasFixedSize(true);
        recycler.setLayoutManager(new LinearLayoutManager(this));
        recycler.setAdapter(adapter);
    }

    private void initializeToolbar() {
        setSupportActionBar(toolbar);
        toolbar.setTitle(Constants.VALUES.EMPTY);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        setTitle(getString(R.string.despatch_sale));
    }

    private void initializeView() {
        Bundle args = getIntent().getExtras();
        tipo_despacho = args.getInt(Constants.INTENT_KEY.TYPE_DESPATCH);
        numero_codigo = args.getString(Constants.INTENT_KEY.CODE);
        textDespatchAccordingTo.setText(getString(R.string.despatch_according_to) + Constants.VALUES.ONE_SPACE + tipoString(tipo_despacho));
        //txtDate.setText(args.getString(Constants.INTENT_KEY.NUMBER));
    }

    @Override
    public void onClick(View view, ItemDespatchSale item) {
        Bundle args = new Bundle();
        //args.putString(Constants.INTENT_KEY.TYPE_VIEW, String.valueOf(Constants.VALUES.SALES.NORMAL));

        args.putInt(Constants.INTENT_KEY.TYPE_DESPATCH, Constants.VALUES.SALES.NORMAL);
        args.putString(Constants.INTENT_KEY.CODE, String.valueOf(item.getTicket()));

        args.putString(Constants.INTENT_KEY.PUMP, item.getSurtidor());
        args.putString(Constants.INTENT_KEY.LICENCE, item.getPlaca());
        args.putInt(Constants.INTENT_KEY.TICKET, item.getTicket());
        args.putString(Constants.INTENT_KEY.DOCUMENT, item.getDocumento());
        args.putString(Constants.INTENT_KEY.CAPACITY, item.getCapacidad());
        args.putString(Constants.INTENT_KEY.OBSERVATION, item.getObservaciones());
        args.putInt(Constants.INTENT_KEY.ID_DESPACHO, item.getIdDespacho());


        callActivity(DespatchSaleDetailActivity.class.getName(), args);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        onBackPressed();
        return super.onOptionsItemSelected(item);
    }


    private void callServiceDespatchSales() {
        list.clear();
        showProgressDialog();
        DespachoVentasRequest request = new DespachoVentasRequest(tipo_despacho, numero_codigo);
        despachoService.opDespachoVentasConsulta(this, request, new ServiceResponse<DespachoVentasResponse, String>() {
            @Override
            public void onResponse(DespachoVentasResponse response) {
                dismissProgressDialog();
                if (response.getCodResult().equals(Constants.SERVICES.SUCCESSFUL_CODE)) {

                    if (response.getData().getDespachos().size() > 0) {

                        for (ItemDespatchSale item : response.getData().getDespachos()) {
                            list.add(item);
                        }
                        recycler.setVisibility(View.VISIBLE);
                        llNoElementos.setVisibility(View.GONE);

                        adapter.setDocumentType(tipoString(response.getData().getTipo()));
                        adapter.notifyDataSetChanged();
                    } else {
                        recycler.setVisibility(View.GONE);
                        llNoElementos.setVisibility(View.VISIBLE);
                    }

                } else {
                    recycler.setVisibility(View.GONE);
                    llNoElementos.setVisibility(View.VISIBLE);

                    showDialog(Constants.VALUES.EMPTY, response.getMsg(), new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            onBackPressed();
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

    private String tipoString(int tipo) {
        switch (tipo) {
            case 1:
                return "QR";
            case 2:
                return "Nro de Placa";
            case 3:
                return "Nro de Ticket";
            case 4:
                return "RUC";
            case 5:
                return "DNI";
            default:
                return "";
        }
    }
}
