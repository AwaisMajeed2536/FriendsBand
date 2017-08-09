package mehwish.ghazi.helper;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.icu.text.SimpleDateFormat;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.preference.PreferenceManager;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;

import com.google.gson.Gson;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.ParseException;
import java.util.Date;

import mehwish.ghazi.interfaces.AlertDialogCallback;
import mehwish.ghazi.interfaces.CLICKTYPE;
import mehwish.ghazi.interfaces.TwoButtonAlertDialogCallback;
import mehwish.ghazi.model.UserAccountModel;

/**
 * Created by Devprovider on 23/05/2017.
 */

public class UtilHelpers {


    private static final String LOG_TAG = "ERRORS_LOG";
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
    public static void showAlertDialog(Context context, @Nullable String title, @Nullable String message,
                                       String positiveBtnTxt, String negativeBtnTxt, final TwoButtonAlertDialogCallback callback) {
        AlertDialog dialog = new AlertDialog.Builder(context).setTitle(title)
                .setMessage("" + message).setCancelable(true)
                .setPositiveButton(positiveBtnTxt, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        callback.onClick(CLICKTYPE.POSITIVE);
                    }
                }).setNegativeButton(negativeBtnTxt, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        callback.onClick(CLICKTYPE.NEGATIVE);
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
        staticContext = context;
        String json = new Gson().toJson(userInfo);
        PreferenceManager.getDefaultSharedPreferences(context).edit().putString(USER_INFO_KEY, json).apply();
    }

    public static boolean isUserLoggedIn(Context context) {
        staticContext = context;
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

    public static boolean hasActiveInternetConnection(Context context) {
        if (isNetworkAvailable(context)) {
            try {
                HttpURLConnection urlc = (HttpURLConnection) (new URL("http://www.google.com").openConnection());
                urlc.setRequestProperty("User-Agent", "Test");
                urlc.setRequestProperty("Connection", "close");
                urlc.setConnectTimeout(1500);
                urlc.connect();
                return (urlc.getResponseCode() == 200);
            } catch (IOException e) {
                Log.e(LOG_TAG, "Error checking internet connection", e);
            }
        } else {
            Log.d(LOG_TAG, "No network available!");
        }
        return false;
    }

    public static boolean isNetworkAvailable(Context context) {
        ConnectivityManager connectivityManager
                = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null;
    }


    public static void showNoInternetDialog(Context context) {
        AlertDialog dialog = new AlertDialog.Builder(context).setTitle("No Internet!")
                .setMessage("your device is not connected to Internet...").setCancelable(true)
                .setPositiveButton("retry", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                       dialog.dismiss();
                    }
                }).create();
        dialog.show();
    }
}
