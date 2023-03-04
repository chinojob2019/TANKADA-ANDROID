package pe.com.gmdsa.sedapal.service.business.impl;

import android.content.Context;

import pe.com.gmdsa.sedapal.domain.DTO.ServiceResponse;
import pe.com.gmdsa.sedapal.domain.ro.request.EventoDiarioRequest;
import pe.com.gmdsa.sedapal.domain.ro.response.EventoDiarioResponse;
import pe.com.gmdsa.sedapal.service.business.EventoDiarioService;
import pe.com.gmdsa.sedapal.util.Constants;
import pe.com.gmdsa.sedapal.view.AndroidApplication;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class EventoDiarioServiceImpl implements EventoDiarioService {
    @Override
    public void opRegistrarEventoDiario(Context context, EventoDiarioRequest request, final ServiceResponse<EventoDiarioResponse, String> listener) {

        Call<EventoDiarioResponse> call = AndroidApplication.getInstance().getService().postEventoDiario(request);
        call.enqueue(new Callback<EventoDiarioResponse>() {
            @Override
            public void onResponse(Call<EventoDiarioResponse> call, Response<EventoDiarioResponse> response) {
                if (response.code() == 200 || response.code() == 202) {
                    if (response.body()!=null) {
                        listener.onResponse(response.body());
                    }else {
                        listener.onFailure(Constants.SERVICES.ERROR_JSON);
                    }
                } else {
                    if (response.body() != null) {
                        listener.onFailure(response.body().getMsg());
                    }else {
                        listener.onFailure(Constants.SERVICES.ERROR_SERVIDOR);
                    }
                }
            }

            @Override
            public void onFailure(Call<EventoDiarioResponse> call, Throwable t) {
                listener.onFailure(Constants.SERVICES.ERROR_CONECTION);
            }
        });
    }


}
