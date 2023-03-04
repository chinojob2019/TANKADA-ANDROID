package pe.com.gmdsa.sedapal.service.business.impl;

import android.content.Context;
import android.util.Log;


import com.google.gson.Gson;

import pe.com.gmdsa.sedapal.domain.DTO.ServiceResponse;
import pe.com.gmdsa.sedapal.domain.model.User;
import pe.com.gmdsa.sedapal.domain.ro.request.CerrarSesionRequest;
import pe.com.gmdsa.sedapal.domain.ro.request.LoginRequest;
import pe.com.gmdsa.sedapal.domain.ro.request.RecuperarContrasenaRequest;
import pe.com.gmdsa.sedapal.domain.ro.request.SesionTokenRequest;
import pe.com.gmdsa.sedapal.domain.ro.response.BaseResponse;
import pe.com.gmdsa.sedapal.domain.ro.response.CerrarSesionResponse;
import pe.com.gmdsa.sedapal.domain.ro.response.LoginResponse;
import pe.com.gmdsa.sedapal.domain.ro.response.RecuperarContrasenaResponse;
import pe.com.gmdsa.sedapal.domain.ro.response.SesionTokenResponse;
import pe.com.gmdsa.sedapal.service.business.UserService;
import pe.com.gmdsa.sedapal.util.Constants;
import pe.com.gmdsa.sedapal.util.LogUtil;
import pe.com.gmdsa.sedapal.view.AndroidApplication;


import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by jmauriciog on 01/06/2016.
 */
public class UserServiceImpl implements UserService {

    @Override
    public void opLogin(Context context, LoginRequest request, final ServiceResponse<LoginResponse, String> listener) {
        Call<LoginResponse> call = AndroidApplication.getInstance().getService().postLogin(request);
        call.enqueue(new Callback<LoginResponse>() {
            @Override
            public void onResponse(Call<LoginResponse> call, Response<LoginResponse> response) {
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
            public void onFailure(Call<LoginResponse> call, Throwable t) {
                listener.onFailure("Error internet");
            }
        });
    }

    @Override
    public void opCerrarSesion(Context context, final ServiceResponse<CerrarSesionResponse, String> listener) {
        Call<CerrarSesionResponse> call = AndroidApplication.getInstance().getService().postCerrarSesion();
        call.enqueue(new Callback<CerrarSesionResponse>() {
            @Override
            public void onResponse(Call<CerrarSesionResponse> call, Response<CerrarSesionResponse> response) {
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
            public void onFailure(Call<CerrarSesionResponse> call, Throwable t) {
                listener.onFailure(Constants.SERVICES.ERROR_CONECTION);
            }
        });
    }

    public void opRecuperarContrasena(Context context, RecuperarContrasenaRequest request, final ServiceResponse<BaseResponse, String> listener) {
        Call<BaseResponse> call = AndroidApplication.getInstance().getService().postRecuperarContrasena(request);

        call.enqueue(new Callback<BaseResponse>() {
            @Override
            public void onResponse(Call<BaseResponse> call, Response<BaseResponse> response) {
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
            public void onFailure(Call<BaseResponse> call, Throwable t) {
                listener.onFailure(Constants.SERVICES.ERROR_CONECTION);
            }
        });
    }

    @Override
    public void opSesionToken(Context context, final ServiceResponse<SesionTokenResponse, String> listener) {


        Gson gson = new Gson();
        Log.w("ERROR TOKEN",""+gson.toJson(listener));

        Call<SesionTokenResponse> call = AndroidApplication.getInstance().getService().postSesionToken(new Object());

        call.enqueue(new Callback<SesionTokenResponse>() {
            @Override
            public void onResponse(Call<SesionTokenResponse> call, Response<SesionTokenResponse> response) {
                if (response.code() == 200 || response.code() == 202) {
                    if (response.body()!=null) {
                        listener.onResponse(response.body());
                    }else {
                        listener.onFailure(Constants.SERVICES.ERROR_JSON);
                    }
                } else {
                    if(response.body()!=null) {
                        listener.onFailure(response.body().getMsgError());
                    }else {
                        listener.onFailure(Constants.SERVICES.ERROR_SERVIDOR);
                    }
                }
            }
            @Override
            public void onFailure(Call<SesionTokenResponse> call, Throwable t) {
                listener.onFailure(Constants.SERVICES.ERROR_CONECTION);
            }
        });
    }

}
