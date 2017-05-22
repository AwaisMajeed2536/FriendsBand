package mehwish.ghazi.helper;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Typeface;
import android.preference.PreferenceManager;
import android.support.annotation.Nullable;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.Gravity;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.google.gson.Gson;

import mehwish.ghazi.R;
import mehwish.ghazi.interfaces.AlertDialogCallback;
import mehwish.ghazi.model.UserAccountModel;

/**
 * Created by Devprovider on 22/05/2017.
 */

public class UtilHelpers {

    private static Context staticContext;
    private static ProgressDialog waitDialog = null;

    public static Context getStaticContext() {
        return staticContext;
    }

    public static void setStaticContext(Context context) {
        staticContext = context;
    }

    public static void goToActivityAsNewTask(Activity context, Class cls, int enterTransition, int exitTransition) {
        Intent intent = new Intent(context, cls);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        context.startActivity(intent);
        context.finish();
        context.overridePendingTransition(enterTransition, exitTransition);
    }

    public static void initGenericToolbar(final AppCompatActivity context, String title, boolean isHomeEnabled) {
        View viewActionBar = context.getLayoutInflater().inflate(R.layout.actionbar_home_title, null);
        ActionBar.LayoutParams params = new ActionBar.LayoutParams(
                android.app.ActionBar.LayoutParams.WRAP_CONTENT,
                android.app.ActionBar.LayoutParams.WRAP_CONTENT,
                Gravity.CENTER);
        TextView textviewTitle = (TextView) viewActionBar.findViewById(R.id.cutom_action_bar);
        textviewTitle.setText(title);
        context.getSupportActionBar().setCustomView(viewActionBar, params);
        context.getSupportActionBar().setDisplayShowCustomEnabled(true);
        context.getSupportActionBar().setDisplayShowTitleEnabled(false);
        context.getSupportActionBar().setDisplayHomeAsUpEnabled(isHomeEnabled);
    /*     staticContext.getActionBar().setCustomView(viewActionBar, params);
        staticContext.getActionBar().setDisplayShowCustomEnabled(true);
        staticContext.getActionBar().setDisplayShowTitleEnabled(false);
        staticContext.getActionBar().setDisplayHomeAsUpEnabled(false);
        staticContext.getActionBar().setHomeButtonEnabled(true);*/


    }

    public static void showAlertDialog(Context context, @Nullable String title, @Nullable String message) {
        AlertDialog dialog = new AlertDialog.Builder(context).setTitle(title)
                .setMessage("" + message).setCancelable(true)
                .setPositiveButton("Retry", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                }).create();
        dialog.show();
    }

    public static void showAlertDialog(Context context, @Nullable String title, @Nullable String message,
                                       String buttonText, final AlertDialogCallback callback) {
        AlertDialog dialog = new AlertDialog.Builder(context).setTitle(title)
                .setMessage("" + message).setCancelable(true)
                .setPositiveButton(buttonText, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        callback.onClick();
                    }
                }).create();
        dialog.show();
    }

    public static void showWaitDialog(Context context, @Nullable String title, @Nullable String message) {
        try {
            if (waitDialog != null) {
                if (!waitDialog.isShowing()) {
                    waitDialog = new ProgressDialog(context);
                    waitDialog.setTitle(title);
                    waitDialog.setMessage(message);
                    waitDialog.setIndeterminate(true);
                    waitDialog.setCancelable(false);
                    waitDialog.show();
                }
            } else {
                waitDialog = new ProgressDialog(context);
                waitDialog.setTitle(title);
                waitDialog.setMessage(message);
                waitDialog.setIndeterminate(true);
                waitDialog.setCancelable(false);
                waitDialog.show();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void dismissWaitDialog() {
        try {
            if (waitDialog != null && waitDialog.isShowing()) {
                waitDialog.dismiss();
            }
        } catch (Exception e) {
            e.printStackTrace();

        }
    }

    private static final String USER_INFO_KEY = "user_info_key";

    public static void createLoginSession(Context context, UserAccountModel userInfo) {
        String json = new Gson().toJson(userInfo);
        PreferenceManager.getDefaultSharedPreferences(context).edit().putString(USER_INFO_KEY, json).apply();
    }

    public static boolean isUserLoggedIn(Context context) {
        String userInfo = PreferenceManager.getDefaultSharedPreferences(context).getString(USER_INFO_KEY, null);
        return userInfo == null;

    }

    public static UserAccountModel getLoggedInUser() {
        String userInfo = PreferenceManager.getDefaultSharedPreferences(staticContext).getString(USER_INFO_KEY, null);
        if (userInfo == null)
            return null;
        return new Gson().fromJson(userInfo, UserAccountModel.class);
    }


    public static void endLoginSession(Context context) {
        PreferenceManager.getDefaultSharedPreferences(context).edit().clear().apply();
    }

    public static void hideKeyboardFrom(Context context, View view) {
        InputMethodManager imm = (InputMethodManager) context.getSystemService(Activity.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }


    public static Typeface getBernardFont() {
        return Typeface.createFromAsset(staticContext.getAssets(), "fonts/BernardFont.ttf");
    }

}
