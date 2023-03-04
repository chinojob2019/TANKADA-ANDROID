package pe.com.gmdsa.sedapal.domain.ro;

/**
 * Created by jmauriciog on 30/12/2016.
 * Interface Retrofit declare services
 */

//import gmd.plantilla.androidapp.domain.ro.request.DespachoVentasRequest;

import pe.com.gmdsa.sedapal.domain.ro.request.CerrarSesionRequest;
import pe.com.gmdsa.sedapal.domain.ro.request.ConsultaDiariaRequest;
import pe.com.gmdsa.sedapal.domain.ro.request.DespachoAnfConfirmarRequest;
import pe.com.gmdsa.sedapal.domain.ro.request.DespachoAnfConsultaRequest;
import pe.com.gmdsa.sedapal.domain.ro.request.DespachoVentaConfirmarRequest;
import pe.com.gmdsa.sedapal.domain.ro.request.DespachoVentasRequest;
import pe.com.gmdsa.sedapal.domain.ro.request.EventoDiarioRequest;
import pe.com.gmdsa.sedapal.domain.ro.request.LoginRequest;
import pe.com.gmdsa.sedapal.domain.ro.request.RecuperarContrasenaRequest;
//import gmd.plantilla.androidapp.domain.ro.response.DespachoVentasResponse;
import pe.com.gmdsa.sedapal.domain.ro.response.BaseResponse;
import pe.com.gmdsa.sedapal.domain.ro.response.CerrarSesionResponse;
import pe.com.gmdsa.sedapal.domain.ro.response.ConsultaDiariaResponse;
import pe.com.gmdsa.sedapal.domain.ro.response.DespachoAnfConfirmarResponse;
import pe.com.gmdsa.sedapal.domain.ro.response.DespachoAnfConsultaResponse;
import pe.com.gmdsa.sedapal.domain.ro.response.DespachoVentaConfirmarResponse;
import pe.com.gmdsa.sedapal.domain.ro.response.DespachoVentasResponse;
import pe.com.gmdsa.sedapal.domain.ro.response.EventoDiarioResponse;
import pe.com.gmdsa.sedapal.domain.ro.response.LoginResponse;
import pe.com.gmdsa.sedapal.domain.ro.response.RecuperarContrasenaResponse;
import pe.com.gmdsa.sedapal.domain.ro.response.SesionTokenResponse;
import pe.com.gmdsa.sedapal.util.Constants;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Headers;
import retrofit2.http.POST;

public interface RetrofitService {

    @POST(Constants.SERVICES.LOGIN_URL)
    Call<LoginResponse> login(@Body LoginRequest request);


    @Headers({
            "Content-Type:application/json",
            "Imei:3569gfhfh38035643809",
            "Device:AND",
            "Token:token1"
    })
    @POST(Constants.SERVICES.POST_EVENTO_DIARIO)
    Call<EventoDiarioResponse> postEventoDiario(@Body EventoDiarioRequest request);

    @Headers({
            "Content-Type:application/json",
            "Token:token1"
    })
    @POST(Constants.SERVICES.POST_SESION_TOKEN)
    Call<SesionTokenResponse> postSesionToken(@Body Object obj);


    /*
    @Headers({
            "Content-Type:application/json",
            "Imei:3569gfhfh38035643809",
            "Device:AND",
            "Token:token1"
    })
    @POST(Constants.SERVICES.POST_DESPACHO_VENTAS)
    Call<DespachoVentasResponse> postDespachoVentas(@Body DespachoVentasRequest request);*/

    @Headers({
            "Content-Type:application/json",
            "Imei:3569gfhfh38035643809",
            "Device:AND",
            "Token:token1"
    })
    @POST(Constants.SERVICES.POST_LOGIN)
    Call<LoginResponse> postLogin(@Body LoginRequest request);


    @Headers({
            "Content-Type:application/json",
            "Imei:3569gfhfh38035643809",
            "Device:AND",
            "Token:token1"
    })
    @POST(Constants.SERVICES.POST_CERRAR_SESION)
    Call<CerrarSesionResponse> postCerrarSesion();


    @Headers({
            "Content-Type:application/json",
            "Imei:3569gfhfh38035643809",
            "Device:AND",
            "Token:token1"
    })
    @POST(Constants.SERVICES.POST_DESPACHO_VENTA_CONFIRMAR)
    Call<DespachoVentaConfirmarResponse> postDespachoVentaConfirmar(@Body DespachoVentaConfirmarRequest request);

    @Headers({
            "Content-Type:application/json",
            "Imei:3569gfhfh38035643809",
            "Device:AND",
            "Token:token1"
    })
    @POST(Constants.SERVICES.POST_RECUPERAR_CONTRASENA)
    Call<BaseResponse> postRecuperarContrasena(@Body RecuperarContrasenaRequest request);


    @Headers({
            "Content-Type:application/json",
            "Imei:3569gfhfh38035643809",
            "Device:AND",
            "Token:token1"
    })
    @POST(Constants.SERVICES.POST_DESPACHO_ANF_CONFIRMAR)
    Call<DespachoAnfConfirmarResponse> postDespachoAnfConfirmar(@Body DespachoAnfConfirmarRequest request);

    @Headers({
            "Content-Type:application/json",
            "Imei:3569gfhfh38035643809",
            "Device:AND",
            "Token:token1"
    })
    @POST(Constants.SERVICES.POST_DESPACHO_ANF_CONSULTA)
    Call<DespachoAnfConsultaResponse> postDespachoAnfConsulta(@Body DespachoAnfConsultaRequest request);

    @Headers({
            "Content-Type:application/json",
            "Imei:3569gfhfh38035643809",
            "Device:AND",
            "Token:token1"
    })
    @POST(Constants.SERVICES.POST_CONSULTA_DIARIA)
    Call<ConsultaDiariaResponse> postConsultaDiaria(@Body ConsultaDiariaRequest request);


    @Headers({
            "Content-Type:application/json",
            "Imei:3569gfhfh38035643809",
            "Device:AND",
            "Token:token1"
    })
    @POST(Constants.SERVICES.POST_DESPACHO_VENTAS)
    Call<DespachoVentasResponse> postDespachoVentas(@Body DespachoVentasRequest request);

}