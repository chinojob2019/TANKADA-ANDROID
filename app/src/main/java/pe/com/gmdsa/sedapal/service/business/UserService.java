package pe.com.gmdsa.sedapal.service.business;

import android.content.Context;

import java.util.ArrayList;


import pe.com.gmdsa.sedapal.domain.DTO.ServiceResponse;
import pe.com.gmdsa.sedapal.domain.ro.request.CerrarSesionRequest;
import pe.com.gmdsa.sedapal.domain.ro.request.RecuperarContrasenaRequest;
import pe.com.gmdsa.sedapal.domain.ro.response.BaseResponse;
import pe.com.gmdsa.sedapal.domain.ro.response.CerrarSesionResponse;
import pe.com.gmdsa.sedapal.domain.ro.response.LoginResponse;
import pe.com.gmdsa.sedapal.domain.ro.response.RecuperarContrasenaResponse;
import pe.com.gmdsa.sedapal.domain.ro.response.SesionTokenResponse;
import pe.com.gmdsa.sedapal.domain.ro.request.LoginRequest;

/**
 * Created by jmauriciog on 01/06/2016.
 */
public interface UserService {
    public void opLogin(Context context, LoginRequest request, ServiceResponse<LoginResponse, String> listener);

    public void opCerrarSesion(Context context,ServiceResponse<CerrarSesionResponse, String> listener);

    public void opRecuperarContrasena(Context context, RecuperarContrasenaRequest request, ServiceResponse<BaseResponse, String> listener);

    public void opSesionToken(Context context, ServiceResponse<SesionTokenResponse, String> listener);

}
