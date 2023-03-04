package pe.com.gmdsa.sedapal.view.activity;

import android.app.DatePickerDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.Objects;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import pe.com.gmdsa.sedapal.R;
import pe.com.gmdsa.sedapal.util.Constants;
import pe.com.gmdsa.sedapal.util.Util;
import pe.com.gmdsa.sedapal.view.AndroidApplication;
import pe.com.gmdsa.sedapal.view.fragment.DatePickerFragment;

public class QueryDailyActivity extends BaseActivity {

    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.txtDate)
    EditText txtDate;
    @BindView(R.id.txtSurtidor)
    TextView txtSurtidor;
    int typeView;
    @BindView(R.id.imageButton)
    ImageButton imageButton;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_query_daily);
        ButterKnife.bind(this);
        initializeToolbar();
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
        typeView = getIntent().getExtras().getInt(Constants.INTENT_KEY.TYPE_VIEW);

        if (typeView == Constants.VALUES.CURRENT_STATUS_CERRADO) {
            imageButton.setImageResource(R.drawable.icon_import_disabled);
        } else {
            imageButton.setImageResource(R.drawable.icon_import_enabled);
        }
    }

    private void initializeToolbar() {
        setSupportActionBar(toolbar);
        toolbar.setTitle(Constants.VALUES.EMPTY);
        setTitle(getString(R.string.query_daily));
        txtSurtidor.setText(Util.getContent(AndroidApplication.getInstance().getUser().getSurtidor()));
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        onBackPressed();
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        callActivity(OptionMenuActivity.class.getName());
        finish();
    }

    @Override
    protected void onResume() {
        super.onResume();
        txtDate.setText("");
    }

    private void showDatePickerDialog() {
        DatePickerFragment newFragment = DatePickerFragment.newInstance(new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                // +1 because january is zero
                final String selectedDate = day + " / " + (month + 1) + " / " + year;
                txtDate.setText(selectedDate);

                Bundle b = new Bundle();
                b.putString(Constants.INTENT_KEY.DATE, selectedDate);
                callActivity(QueryDailyListActivity.class.getName(), b);
                finish();
            }
        });
        newFragment.show(this.getSupportFragmentManager(), "datePicker");
    }

    private void goToDailyClosure() {
        Bundle args = new Bundle();
        args.putString(Constants.INTENT_KEY.TYPE_VIEW, String.valueOf(Constants.VALUES.CURRENT_STATUS_SIN_CIERRE_ANTERIOR));
        args.putBoolean(Constants.INTENT_KEY.OPCION_CIERRE_DIARIO, true);

        callActivity(MaintenanceDailyActivity_2.class.getName(), args);
        finish();
    }

    private void showCloseSessionMessage() {
        if (typeView == Constants.VALUES.CURRENT_STATUS_CERRADO) {
            showDialog("Mensaje", "Ya realizó el cierre diario, mañana podrá realizar la apertura.", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {

                }
            });
        } else {
            showDialogOptions(getString(R.string.title_message_are_you_sure), getString(R.string.message_closure_daily), new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    goToDailyClosure();
                }
            }, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {

                }
            }, getString(R.string.accept), getString(R.string.cancel));
        }

    }

    public void onClickDate(View view) {
        showDatePickerDialog();
    }



    public void onClickCloseSession(View view) {
        showCloseSessionMessage();
    }

}
