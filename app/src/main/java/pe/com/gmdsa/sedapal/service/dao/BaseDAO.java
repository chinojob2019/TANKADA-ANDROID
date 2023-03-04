package pe.com.gmdsa.sedapal.service.dao;


import pe.com.gmdsa.sedapal.util.DatabaseManager;
import pe.com.gmdsa.sedapal.view.AndroidApplication;

/**
 * Created by jmauriciog on 14/12/2015.
 */
public class BaseDAO {

    protected DatabaseManager db;

    protected BaseDAO() {
        AndroidApplication application = AndroidApplication.getInstance();
        db = DatabaseManager.getInstance(application);
        db.getWritableDatabase();
    }
}
