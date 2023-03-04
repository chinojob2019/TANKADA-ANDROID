package pe.com.gmdsa.sedapal.service.business;

import android.content.Context;

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

public interface DespachoService {
    public void opDespachoVentaConfirmar(Context context, DespachoVentaConfirmarRequest request, ServiceResponse<DespachoVentaConfirmarResponse, String> listener);

    public void opDespachoAnfConfirmar(Context context, DespachoAnfConfirmarRequest request, ServiceResponse<DespachoAnfConfirmarResponse, String> listener);

    public void opDespachoAnfConsulta(Context context, DespachoAnfConsultaRequest request, ServiceResponse<DespachoAnfConsultaResponse, String> listener);

    public void opConsultaDiaria(Context context, ConsultaDiariaRequest request, ServiceResponse<ConsultaDiariaResponse, String> listener);

    public void opDespachoVentasConsulta(Context context, DespachoVentasRequest request, ServiceResponse<DespachoVentasResponse, String> listener);

}
