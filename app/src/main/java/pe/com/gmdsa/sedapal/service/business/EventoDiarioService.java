package pe.com.gmdsa.sedapal.service.business;

import android.content.Context;


import pe.com.gmdsa.sedapal.domain.DTO.ServiceResponse;
import pe.com.gmdsa.sedapal.domain.ro.request.EventoDiarioRequest;
import pe.com.gmdsa.sedapal.domain.ro.response.EventoDiarioResponse;

public interface EventoDiarioService {
    public void opRegistrarEventoDiario(Context context, EventoDiarioRequest request, ServiceResponse<EventoDiarioResponse, String> listener);
}

