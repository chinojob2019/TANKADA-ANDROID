package pe.com.gmdsa.sedapal.view.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import pe.com.gmdsa.sedapal.R;
import pe.com.gmdsa.sedapal.util.Constants;
import pe.com.gmdsa.sedapal.util.Util;

public class DespatchSaleSuccessfulActivity extends BaseActivity {


    int typeView = Constants.VALUES.SALES.OPENING;
    @BindView(R.id.txtMessage)
    TextView txtMessage;
    @BindView(R.id.txtDate)
    TextView txtDate;
    @BindView(R.id.linealLayoutDate)
    LinearLayout linealLayoutDate;
    @BindView(R.id.txtTime)
    TextView txtTime;
    @BindView(R.id.linealLayoutTime)
    LinearLayout linealLayoutTime;
    @BindView(R.id.txtSurtidor)
    TextView txtSurtidor;
    @BindView(R.id.linealLayoutSurtidor)
    LinearLayout linealLayoutSurtidor;
    @BindView(R.id.txtEvent)
    TextView txtEvent;
    @BindView(R.id.linealLayoutEvent)
    LinearLayout linealLayoutEvent;
    @BindView(R.id.txtTypeDespatch)
    TextView txtTypeDespatch;
    @BindView(R.id.linealLayoutTypeDespatch)
    LinearLayout linealLayoutTypeDespatch;
    @BindView(R.id.txtPlate)
    TextView txtPlate;
    @BindView(R.id.linealLayoutPlate)
    LinearLayout linealLayoutPlate;
    @BindView(R.id.txtTicket)
    TextView txtTicket;
    @BindView(R.id.linealLayoutDocument)
    LinearLayout linealLayoutDocument;
    @BindView(R.id.txtCapacity)
    TextView txtCapacity;
    @BindView(R.id.linealLayoutCapacity)
    LinearLayout linealLayoutCapacity;
    @BindView(R.id.txtMeasure)
    TextView txtMeasure;
    @BindView(R.id.linealLayoutMeasure)
    LinearLayout linealLayoutMeasure;
    @BindView(R.id.btnRegister)
    Button btnRegister;
    @BindView(R.id.linearLayoutBottom)
    LinearLayout linearLayoutBottom;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_despatch_sale_successful);
        ButterKnife.bind(this);
        initializeView();
    }

    public void initializeView() {
        Bundle args = getIntent().getExtras();
        typeView = args.getInt(Constants.INTENT_KEY.TYPE_VIEW);


        btnRegister.setText(getString(R.string.back));
        if (typeView == Constants.VALUES.SALES.OPENING) {
            txtMessage.setText(getString(R.string.title_opening));
            linealLayoutDate.setVisibility(View.VISIBLE);
            linealLayoutTime.setVisibility(View.VISIBLE);
            linealLayoutSurtidor.setVisibility(View.VISIBLE);
            linealLayoutEvent.setVisibility(View.GONE);
            linealLayoutTypeDespatch.setVisibility(View.GONE);
            linealLayoutPlate.setVisibility(View.GONE);
            linealLayoutDocument.setVisibility(View.GONE);
            linealLayoutCapacity.setVisibility(View.GONE);
            linealLayoutMeasure.setVisibility(View.VISIBLE);
        } else if (typeView == Constants.VALUES.SALES.NORMAL) {
            txtMessage.setText(getString(R.string.title_normal));
            linealLayoutSurtidor.setVisibility(View.VISIBLE);
            linealLayoutTypeDespatch.setVisibility(View.VISIBLE);
            linealLayoutPlate.setVisibility(View.VISIBLE);
            linealLayoutDocument.setVisibility(View.VISIBLE);
            linealLayoutCapacity.setVisibility(View.VISIBLE);

            linealLayoutTime.setVisibility(View.GONE);
            linealLayoutEvent.setVisibility(View.GONE);
            linealLayoutDate.setVisibility(View.GONE);
            linealLayoutMeasure.setVisibility(View.GONE);
        } else if (typeView == Constants.VALUES.SALES.ANF) {
            txtMessage.setText(getString(R.string.title_anf));
            linealLayoutSurtidor.setVisibility(View.VISIBLE);
            linealLayoutEvent.setVisibility(View.VISIBLE);
            linealLayoutPlate.setVisibility(View.VISIBLE);
            linealLayoutCapacity.setVisibility(View.VISIBLE);
            linealLayoutDate.setVisibility(View.GONE);
            linealLayoutTime.setVisibility(View.GONE);
            linealLayoutTypeDespatch.setVisibility(View.GONE);
            linealLayoutDocument.setVisibility(View.GONE);
            linealLayoutMeasure.setVisibility(View.GONE);

        } else if (typeView == Constants.VALUES.SALES.CLOSURE) {
            txtMessage.setText(getString(R.string.title_closure));
            btnRegister.setText(getString(R.string.finish));
            linealLayoutDate.setVisibility(View.VISIBLE);
            linealLayoutTime.setVisibility(View.VISIBLE);
            linealLayoutSurtidor.setVisibility(View.VISIBLE);
            linealLayoutEvent.setVisibility(View.GONE);
            linealLayoutTypeDespatch.setVisibility(View.GONE);
            linealLayoutPlate.setVisibility(View.GONE);
            linealLayoutDocument.setVisibility(View.GONE);
            linealLayoutCapacity.setVisibility(View.GONE);
            linealLayoutMeasure.setVisibility(View.VISIBLE);
        }

        txtDate.setText(Util.getContent(args.getString(Constants.INTENT_KEY.DATE)));
        txtTime.setText(Util.getContent(args.getString(Constants.INTENT_KEY.TIME)));
        txtSurtidor.setText(Util.getContent(args.getString(Constants.INTENT_KEY.PUMP)));
        txtMeasure.setText(Util.getContent(args.getString(Constants.INTENT_KEY.M3))+" m3");
        txtEvent.setText(Util.getContent(args.getString(Constants.INTENT_KEY.EVENT)));
        txtCapacity.setText(Util.getContent(args.getString(Constants.INTENT_KEY.CAPACITY)));
        txtPlate.setText(Util.getContent(args.getString(Constants.INTENT_KEY.LICENCE)));
        txtTypeDespatch.setText(Util.getContent(args.getString(Constants.INTENT_KEY.TYPE_DESPATCH)));
        txtTicket.setText(Util.getContent(String.valueOf(args.getInt(Constants.INTENT_KEY.TICKET))));

    }

    public void onClickBack(View view) {
        onBackPressed();
    }

    @Override
    public void onBackPressed() {
        if (typeView == Constants.VALUES.SALES.CLOSURE) {
            callActivity(MaintenanceDailyActivity_2.class.getName());
            finish();
        } else

        /*
        public static int OPENING = 0;
            public static int NORMAL = 1;
            public static int ANF = 2;
            public static int CLOSURE = 3;
         */
            switch (typeView) {
                case 0: //OPENING
                    //callActivity(OptionMenuActivity.class.getName());
                    //finish();
                    Intent intent=new Intent(this,OptionMenuActivity.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(intent);
                    finish();
                    break;
                case 1: //NORMAL
                    finish();
                    break;
                case 2://ANF
                    finish();
                    break;
                case 3://CLOSURE
                    //callActivity(OptionMenuActivity.class.getName());
                    //finish();
                    Intent intent2=new Intent(this,OptionMenuActivity.class);
                    intent2.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(intent2);
                    finish();
                    break;
            }
    }
}
