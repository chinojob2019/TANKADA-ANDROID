package pe.com.gmdsa.sedapal.service.business;

import android.content.Context;

import pe.com.gmdsa.sedapal.domain.DTO.ServiceResponse;
import pe.com.gmdsa.sedapal.domain.ro.response.AppVersionResponse;


public interface AppVersionService {
    public void traerVersionActual(Context context, ServiceResponse<AppVersionResponse, String> listener);
}
