package pe.com.gmdsa.sedapal.domain.DTO;

/**
 * Created by glarab on 7/03/2017.
 */

public interface ServiceResponse<T, K> {
    public void onResponse(T response);

    public void onFailure(K response);
}
