package pe.com.gmdsa.sedapal.util;

import android.widget.EditText;

public class Util {
    public static String getContent(String string) {
        return string == null || string.isEmpty() || string.equals("null") ? "" : string;
    }

    public static String getContent(EditText editText) {
        return getContent(editText.getText().toString());
    }
}
