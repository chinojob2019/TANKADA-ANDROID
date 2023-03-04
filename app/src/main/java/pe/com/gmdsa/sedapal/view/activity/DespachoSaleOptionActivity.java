package pe.com.gmdsa.sedapal.view.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.FrameLayout;

import butterknife.BindView;
import butterknife.ButterKnife;
import pe.com.gmdsa.sedapal.R;
import pe.com.gmdsa.sedapal.util.Constants;
import pe.com.gmdsa.sedapal.view.activity.DespatchAnfActivity;
import pe.com.gmdsa.sedapal.view.activity.BaseActivity;

public class DespachoSaleOptionActivity extends BaseActivity {

    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.frameLayoutQR)
    FrameLayout frameLayoutQR;
    @BindView(R.id.frameLayoutDetailSale)
    FrameLayout frameLayoutDetailSale;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_despacho_sale_option);
        ButterKnife.bind(this);
        initializeToolbar();
        initializeView();
    }

    private void initializeToolbar() {
        setSupportActionBar(toolbar);
        toolbar.setTitle(Constants.VALUES.EMPTY);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        setTitle(getString(R.string.despatch_sale));
    }

    private void initializeView() {
        frameLayoutQR.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                callActivity(DespatchSaleQRActivity.class.getName());
            }
        });
        frameLayoutDetailSale.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                callActivity(DespatchSaleActivity.class.getName());
            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        onBackPressed();
        return super.onOptionsItemSelected(item);
    }
}
