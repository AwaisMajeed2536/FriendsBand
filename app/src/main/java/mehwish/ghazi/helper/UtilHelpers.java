package mehwish.ghazi.helper;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.icu.text.SimpleDateFormat;
import android.preference.PreferenceManager;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;

import com.google.gson.Gson;

import java.text.ParseException;
import java.util.Date;

import mehwish.ghazi.interfaces.AlertDialogCallback;
import mehwish.ghazi.model.UserAccountModel;

/**
 * Created by Devprovider on 23/05/2017.
 */

public class UtilHelpers {


    private static ProgressDialog waitDialog;
    public static Context staticContext;

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

    public static void showWaitDialog(Context context,@Nullable  String title,@Nullable  String message) {
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
        return userInfo != null;
    }

    @Nullable
    public static UserAccountModel getLoggedInUser(){
        String userInfo = PreferenceManager.getDefaultSharedPreferences(staticContext).getString(USER_INFO_KEY, null);
        if (userInfo == null)
            return null;
        return new Gson().fromJson(userInfo, UserAccountModel.class);
    }

    public static void endLoginSession(Context context) {
        PreferenceManager.getDefaultSharedPreferences(context).edit().clear().apply();
    }
}
