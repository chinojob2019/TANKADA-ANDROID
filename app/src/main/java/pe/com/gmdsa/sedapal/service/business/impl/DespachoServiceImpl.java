package pe.com.gmdsa.sedapal.service.business.impl;

import android.content.Context;
import android.util.Log;

import com.google.gson.Gson;

import pe.com.gmdsa.sedapal.R;
import pe.com.gmdsa.sedapal.domain.DTO.ServiceResponse;


import pe.com.gmdsa.sedapal.domain.ro.request.ConsultaDiariaRequest;
import pe.com.gmdsa.sedapal.domain.ro.request.DespachoAnfConfirmarRequest;
import pe.com.gmdsa.sedapal.domain.ro.request.DespachoAnfConsultaRequest;
import pe.com.gmdsa.sedapal.domain.ro.request.DespachoVentaConfirmarRequest;
import pe.com.gmdsa.sedapal.domain.ro.request.DespachoVentasRequest;
import pe.com.gmdsa.sedapal.domain.ro.response.ConsultaDiariaResponse;
import pe.com.gmdsa.sedapal.domain.ro.response.DespachoAnfConfirmarResponse;
import pe.com.gmdsa.sedapal.domain.ro.response.DespachoAnfConsultaResponse;
import pe.com.gmdsa.sedapal.domain.ro.response.DespachoVentaConfirmarResponse;
import pe.com.gmdsa.sedapal.domain.ro.response.DespachoVentasResponse;
import pe.com.gmdsa.sedapal.service.business.DespachoService;
import pe.com.gmdsa.sedapal.util.Constants;
import pe.com.gmdsa.sedapal.view.AndroidApplication;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DespachoServiceImpl implements DespachoService {

    @Override
    public void opDespachoVentaConfirmar(Context context, DespachoVentaConfirmarRequest request, final ServiceResponse<DespachoVentaConfirmarResponse, String> listener) {
        Call<DespachoVentaConfirmarResponse> call = AndroidApplication.getInstance().getService().postDespachoVentaConfirmar(request);
        call.enqueue(new Callback<DespachoVentaConfirmarResponse>() {
            @Override
            public void onResponse(Call<DespachoVentaConfirmarResponse> call, Response<DespachoVentaConfirmarResponse> response) {
                if (response.code() == 200 || response.code() == 202) {
                    if (response.body()!=null) {
                        listener.onResponse(response.body());
                    }else {
                        listener.onFailure(Constants.SERVICES.ERROR_JSON);
                    }
                } else {
                    if (response.body()!=null) {
                        listener.onFailure(response.body().getMsg());
                    }else {
                        listener.onFailure(Constants.SERVICES.ERROR_SERVIDOR);
                    }
                }
            }

            @Override
            public void onFailure(Call<DespachoVentaConfirmarResponse> call, Throwable t) {
                listener.onFailure(Constants.SERVICES.ERROR_CONECTION);
            }
        });
    }

    @Override
    public void opDespachoAnfConfirmar(Context context, DespachoAnfConfirmarRequest request, final ServiceResponse<DespachoAnfConfirmarResponse, String> listener) {
        Call<DespachoAnfConfirmarResponse> call = AndroidApplication.getInstance().getService().postDespachoAnfConfirmar(request);
        call.enqueue(new Callback<DespachoAnfConfirmarResponse>() {
            @Override
            public void onResponse(Call<DespachoAnfConfirmarResponse> call, Response<DespachoAnfConfirmarResponse> response) {
                if (response.code() == 200 || response.code() == 202) {
                    if (response.body()!=null) {
                        listener.onResponse(response.body());
                    }else {
                        listener.onFailure(Constants.SERVICES.ERROR_JSON);
                    }
                } else {
                    if (response.body()!=null){
                        listener.onFailure(response.body().getMsg());
                    }else {
                        listener.onFailure(Constants.SERVICES.ERROR_SERVIDOR);
                    }
                }
            }

            @Override
            public void onFailure(Call<DespachoAnfConfirmarResponse> call, Throwable t) {
                listener.onFailure(Constants.SERVICES.ERROR_CONECTION);
            }
        });
    }

    @Override
    public void opDespachoAnfConsulta(Context context, final DespachoAnfConsultaRequest request, final ServiceResponse<DespachoAnfConsultaResponse, String> listener) {
        Call<DespachoAnfConsultaResponse> call = AndroidApplication.getInstance().getService().postDespachoAnfConsulta(request);
        Log.w("opDespachoAnfConsulta",""+new Gson().toJson(request));
        call.enqueue(new Callback<DespachoAnfConsultaResponse>() {
            @Override
            public void onResponse(Call<DespachoAnfConsultaResponse> call, Response<DespachoAnfConsultaResponse> response) {
                if (response.code() == 200 || response.code() == 202) {
                    if (response.body()!=null) {
                        listener.onResponse(response.body());
                    }else {
                        listener.onFailure(Constants.SERVICES.ERROR_JSON);
                    }
                } else {
                    if (response.body()!=null) {
                        listener.onFailure(response.body().getMsg());
                    }else {
                        listener.onFailure(Constants.SERVICES.ERROR_SERVIDOR);
                    }
                }
            }

            @Override
            public void onFailure(Call<DespachoAnfConsultaResponse> call, Throwable t) {
                listener.onFailure(Constants.SERVICES.ERROR_CONECTION);
            }
        });
    }

    @Override
    public void opConsultaDiaria(Context context, final ConsultaDiariaRequest request, final ServiceResponse<ConsultaDiariaResponse, String> listener) {
        Call<ConsultaDiariaResponse> call = AndroidApplication.getInstance().getService().postConsultaDiaria(request);
        call.enqueue(new Callback<ConsultaDiariaResponse>() {
            @Override
            public void onResponse(Call<ConsultaDiariaResponse> call, Response<ConsultaDiariaResponse> response) {
                if (response.code() == 200 || response.code() == 202) {
                    if (response.body()!=null) {
                        listener.onResponse(response.body());
                    }else {
                        listener.onFailure(Constants.SERVICES.ERROR_JSON);
                    }
                } else {
                    if (response.body()!=null) {
                        listener.onFailure(response.body().getMsg());
                    }else {
                        listener.onFailure(Constants.SERVICES.ERROR_SERVIDOR);
                    }
                }
            }

            @Override
            public void onFailure(Call<ConsultaDiariaResponse> call, Throwable t) {
                listener.onFailure(Constants.SERVICES.ERROR_CONECTION);
            }
        });
    }

    @Override
    public void opDespachoVentasConsulta(Context context, DespachoVentasRequest request, final ServiceResponse<DespachoVentasResponse, String> listener) {
        Call<DespachoVentasResponse> call = AndroidApplication.getInstance().getService().postDespachoVentas(request);
        call.enqueue(new Callback<DespachoVentasResponse>() {
            @Override
            public void onResponse(Call<DespachoVentasResponse> call, Response<DespachoVentasResponse> response) {
                if (response.code() == 200 || response.code() == 202) {
                    if (response.body()!=null) {
                        listener.onResponse(response.body());
                    }else {
                        listener.onFailure(Constants.SERVICES.ERROR_JSON);
                    }
                } else {
                    if (response.body()!=null) {
                        listener.onFailure(response.body().getMsg());
                    }else {
                        listener.onFailure(Constants.SERVICES.ERROR_SERVIDOR);
                    }
                }
            }

            @Override
            public void onFailure(Call<DespachoVentasResponse> call, Throwable t) {
                listener.onFailure(Constants.SERVICES.ERROR_CONECTION);
            }
        });
    }




}
