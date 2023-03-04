package pe.com.gmdsa.sedapal.view.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import butterknife.BindView;
import butterknife.ButterKnife;
import pe.com.gmdsa.sedapal.R;
import pe.com.gmdsa.sedapal.domain.DTO.ServiceResponse;
import pe.com.gmdsa.sedapal.domain.model.Despacho;
import pe.com.gmdsa.sedapal.domain.model.ItemDespatchSale;
import pe.com.gmdsa.sedapal.domain.ro.request.ConsultaDiariaRequest;
import pe.com.gmdsa.sedapal.domain.ro.response.ConsultaDiariaResponse;
import pe.com.gmdsa.sedapal.service.business.DespachoService;
import pe.com.gmdsa.sedapal.service.business.impl.DespachoServiceImpl;
import pe.com.gmdsa.sedapal.util.Constants;
import pe.com.gmdsa.sedapal.util.Util;
import pe.com.gmdsa.sedapal.view.AndroidApplication;
import pe.com.gmdsa.sedapal.view.adapter.DespatchQueryAdapter;

public class QueryDailyListActivity extends AppCompatActivity implements DespatchQueryAdapter.OnClickItem {

    @BindView(R.id.toolbar)
    Toolbar toolbar;
    String day;
    DespachoService despachoService = new DespachoServiceImpl();
    @BindView(R.id.tv_surtidor)
    TextView tvSurtidor;
    @BindView(R.id.tv_fecha)
    TextView tvFecha;
    @BindView(R.id.rv_items)
    RecyclerView rvItems;
    @BindView(R.id.tv_total)
    TextView tvTotal;
    @BindView(R.id.tv_diferencia)
    TextView tvDiferencia;
    @BindView(R.id.btnRegister)
    Button btnRegister;
    @BindView(R.id.linearLayoutRegisterButton)
    LinearLayout linearLayoutRegisterButton;
    @BindView(R.id.linearLayoutBottom)
    LinearLayout linearLayoutBottom;

    private List<Despacho> items = new ArrayList<>();
    DespatchQueryAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_query_daily_list);
        day = getIntent().getExtras().getString(Constants.INTENT_KEY.DATE);
        ButterKnife.bind(this);


        initializeToolbar();
        initView();
        callServiceConsultaDiaria();
    }


    private void initView() {
        rvItems.setHasFixedSize(true);
        rvItems.setLayoutManager(new LinearLayoutManager(this));
        adapter = new DespatchQueryAdapter(items, this, this);
        rvItems.setAdapter(adapter);

        tvSurtidor.setText(Util.getContent(AndroidApplication.getInstance().getUser().getSurtidor()));
        tvFecha.setText(day);
    }

    private void initializeToolbar() {
        setSupportActionBar(toolbar);
        toolbar.setTitle(Constants.VALUES.EMPTY);
        setTitle(getString(R.string.query_daily));
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        onBackPressed();
        return super.onOptionsItemSelected(item);
    }

    public void onClickBack(View view) {

    }

    public void callServiceConsultaDiaria() {
        final ConsultaDiariaRequest request = new ConsultaDiariaRequest(day);
        despachoService.opConsultaDiaria(this, request, new ServiceResponse<ConsultaDiariaResponse, String>() {
            @Override
            public void onResponse(ConsultaDiariaResponse response) {
                paintData(response);
            }

            @Override
            public void onFailure(String response) {

            }
        });
    }

    private void paintData(ConsultaDiariaResponse response) {
        tvTotal.setText(Util.getContent(response.getData().getTotalDespacho()));
        tvDiferencia.setText(Util.getContent(response.getData().getDiferenciaAperturada()));
        if (response.getData().getResultado() != null) {
            items.addAll(response.getData().getResultado());
            adapter.notifyDataSetChanged();
        }

    }

    @Override
    public void onClick(View view, Despacho item) {

    }
}
