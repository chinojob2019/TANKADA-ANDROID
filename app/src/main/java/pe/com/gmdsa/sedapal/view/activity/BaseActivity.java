package pe.com.gmdsa.sedapal.view.activity;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Toast;

import java.util.List;

import pe.com.gmdsa.sedapal.R;
import pe.com.gmdsa.sedapal.domain.model.User;
import pe.com.gmdsa.sedapal.service.business.UserService;
import pe.com.gmdsa.sedapal.service.business.impl.UserServiceImpl;
import pe.com.gmdsa.sedapal.view.AndroidApplication;

/**
 * Created by innovagmd on 25/01/17.
 */

public class BaseActivity extends AppCompatActivity {

    protected ProgressDialog progressDialog;
    BaseActivity activity;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activity = this;
    }

    @Override
    protected void onResume() {
        super.onResume();
        hideKeyboard();
    }

    protected User getCurrentUser() {
        //UserService userService = new UserServiceImpl();
        //User user = userService.getCurrentUser();

        User user = AndroidApplication.getInstance().getUser();
        return user;
    }

    protected void callActivity(final String classCalled) {
        Bundle bundle = new Bundle();
        callActivity(classCalled, bundle);
    }

    protected void callActivity(final String classCalled, Bundle bundle) {
        Intent intent = new Intent();
        intent.setClassName(this, classCalled);
        intent.putExtras(bundle);
        startActivity(intent);
    }

    protected void callActivity(final String classCalled, Bundle bundle,
                                final int requestCode) {
        Intent intent = new Intent();
        intent.setClassName(this, classCalled);
        if (bundle != null)
            intent.putExtras(bundle);
        startActivityForResult(intent, requestCode);
    }

    protected ProgressDialog showProgressDialog() {
        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage(getString(R.string.message_loading));
        progressDialog.show();
        return progressDialog;
    }

    protected ProgressDialog showProgressDialog(CharSequence message) {
        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage(message);
        progressDialog.show();

        return progressDialog;
    }

    protected void dismissProgressDialog() {
        if (progressDialog != null && progressDialog.isShowing()) {
            progressDialog.dismiss();
        }
    }

    public void hideKeyboardFromView(View view) {
        //View view = this.getCurrentFocus();
        if (view != null) {
            InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }
    }

    public void hideKeyboard() {
        this.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
    }

    protected AlertDialog showDialog(String title, String mensaje, DialogInterface.OnClickListener si) {
        AlertDialog.Builder builder;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            builder = new AlertDialog.Builder(this, R.style.Theme_AppCompat_Light_Dialog);
        } else {
            builder = new AlertDialog.Builder(this);
        }
        if (title != null)
            builder.setTitle(title);
        builder.setCancelable(false);
        builder.setMessage(mensaje);

        builder.setPositiveButton("  " + getString(R.string.accept) + "  ", si);

        final AlertDialog dialog = builder.create();
        dialog.setOnShowListener(new DialogInterface.OnShowListener() {
            @Override
            public void onShow(DialogInterface dg) {
                Button si = dialog.getButton(DialogInterface.BUTTON_POSITIVE);
                si.setBackgroundColor(getResources().getColor(R.color.transparent));
                si.setTextColor(getResources().getColor(R.color.light_blue));
            }
        });
        dialog.show();
        return dialog;
    }

    protected AlertDialog showDialogOptions(String title, String mensaje, DialogInterface.OnClickListener ok, DialogInterface.OnClickListener no, String texto_ok, String texto_no) {
        AlertDialog.Builder builder;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            builder = new AlertDialog.Builder(this, R.style.Theme_AppCompat_Light_Dialog);
        } else {
            builder = new AlertDialog.Builder(this);
        }
        if (title != null)
            builder.setTitle(title);
        builder.setCancelable(false);
        builder.setMessage(mensaje);
        builder.setPositiveButton("  " + texto_ok + "  ", ok);
        builder.setNegativeButton(" " + texto_no + " ", no);
        final AlertDialog dialog = builder.create();
        dialog.setOnShowListener(new DialogInterface.OnShowListener() {
            @Override
            public void onShow(DialogInterface db) {
                Button si = dialog.getButton(DialogInterface.BUTTON_POSITIVE);
                si.setBackgroundColor(getResources().getColor(R.color.transparent));
                si.setTextColor(getResources().getColor(R.color.light_blue));
                Button no = dialog.getButton(DialogInterface.BUTTON_NEGATIVE);
                no.setTextColor(getResources().getColor(R.color.gray));
            }
        });

        dialog.show();
        return dialog;
    }

    protected AlertDialog showDialogRadioOptions(String title, List<String> items, int item_default, DialogInterface.OnClickListener clickItem, DialogInterface.OnClickListener ok, DialogInterface.OnClickListener no) {
        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<>(BaseActivity.this, R.layout.item_check);
        arrayAdapter.addAll(items);

        AlertDialog.Builder builder;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            builder = new AlertDialog.Builder(this, R.style.Theme_AppCompat_Light_Dialog);
        } else {
            builder = new AlertDialog.Builder(this);
        }
        if (title != null)
            builder.setTitle(title);
        builder.setCancelable(false);
        builder.setPositiveButton("  " + getString(R.string.accept) + "  ", ok);
        builder.setNegativeButton(" " + getString(R.string.cancel) + " ", no);
        builder.setSingleChoiceItems(arrayAdapter, item_default, clickItem);

        final AlertDialog dialog = builder.create();
        dialog.setOnShowListener(new DialogInterface.OnShowListener() {
            @Override
            public void onShow(DialogInterface db) {
                Button si = dialog.getButton(DialogInterface.BUTTON_POSITIVE);
                si.setBackgroundColor(getResources().getColor(R.color.transparent));
                si.setTextColor(getResources().getColor(R.color.light_blue));
                Button no = dialog.getButton(DialogInterface.BUTTON_NEGATIVE);
                no.setTextColor(getResources().getColor(R.color.gray));
            }
        });

        dialog.show();
        return dialog;
    }

    public void toastShow(int resId) {
        Toast.makeText(activity, resId, Toast.LENGTH_SHORT).show();
    }

    public void toastShow(String resId) {
        Toast.makeText(activity, resId, Toast.LENGTH_SHORT).show();
    }

}