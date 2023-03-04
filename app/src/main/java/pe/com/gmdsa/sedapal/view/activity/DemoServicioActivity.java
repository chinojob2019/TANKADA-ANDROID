package pe.com.gmdsa.sedapal.view.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import pe.com.gmdsa.sedapal.R;
import pe.com.gmdsa.sedapal.domain.DTO.ServiceResponse;
import pe.com.gmdsa.sedapal.domain.ro.request.EventoDiarioRequest;
import pe.com.gmdsa.sedapal.domain.ro.response.EventoDiarioResponse;
import pe.com.gmdsa.sedapal.service.business.EventoDiarioService;
import pe.com.gmdsa.sedapal.service.business.impl.EventoDiarioServiceImpl;
import pe.com.gmdsa.sedapal.view.activity.BaseActivity;

public class DemoServicioActivity extends BaseActivity {

    @BindView(R.id.bt_llamar)
    Button btLlamar;
    @BindView(R.id.tv_respuesta)
    TextView tvRespuesta;

    public static final int EVENTO_APERTURAR = 1;
    public static final int EVENTO_CERRAR = 2;


    EventoDiarioService eventoDiarioService = new EventoDiarioServiceImpl();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_servicio_demo);
        ButterKnife.bind(this);
    }

    @OnClick({R.id.bt_llamar, R.id.tv_respuesta})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.bt_llamar:
                llamarServicio(EVENTO_APERTURAR);
                break;

        }
    }


    private void llamarServicio(int evento) {
        /*EventoDiarioRequest request = new EventoDiarioRequest("22/08/2018", "nombre surtidor", 14524, "base 64", evento);

        eventoDiarioService.opRegistrarEventoDiario(this, request, new ServiceResponse<EventoDiarioResponse, String>() {
            @Override
            public void onResponse(EventoDiarioResponse response) {
                if (response.getData() != null) {
                    tvRespuesta.setText(response.getData().toString());
                } else {
                    tvRespuesta.setText("data nula");
                }
            }

            @Override
            public void onFailure(String response) {
                tvRespuesta.setText(response);

            }
        });
        */
    }
}
