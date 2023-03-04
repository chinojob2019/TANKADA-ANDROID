package pe.com.gmdsa.sedapal.view.fragment;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.widget.DatePicker;

import java.util.Calendar;

import pe.com.gmdsa.sedapal.R;

public class DatePickerFragment extends DialogFragment {

    private DatePickerDialog.OnDateSetListener listener;
    private DatePickerDialog picker;

    public static DatePickerFragment newInstance(DatePickerDialog.OnDateSetListener listener) {
        DatePickerFragment fragment = new DatePickerFragment();
        fragment.setListener(listener);
        return fragment;
    }

    public void setListener(DatePickerDialog.OnDateSetListener listener) {
        this.listener = listener;
    }

    @Override
    @NonNull
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        // Use the current date as the default date in the picker
        final Calendar c = Calendar.getInstance();
        int year = c.get(Calendar.YEAR);
        int month = c.get(Calendar.MONTH);
        int day = c.get(Calendar.DAY_OF_MONTH);

        picker = new DatePickerDialog(getContext(), listener, year, month, day);
        picker.setButton(DatePickerDialog.BUTTON_POSITIVE, getString(R.string.accept), picker);
        picker.setButton(DatePickerDialog.BUTTON_NEGATIVE, getString(R.string.cancel), picker);
        // Create a new instance of DatePickerDialog and return it
        return picker;
    }
}