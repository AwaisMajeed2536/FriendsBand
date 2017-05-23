package mehwish.ghazi.ui;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.preference.PreferenceManager;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import mehwish.ghazi.R;
import mehwish.ghazi.helper.UtilHelpers;

/**
 * Created by Devprovider on 3/11/2017.
 */

public class SplashActivity extends AppCompatActivity {
    private Context context;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        context = SplashActivity.this;
        UtilHelpers.staticContext = context;
        checkLogin();
    }

    @Override
    protected void onResume() {
        super.onResume();
        checkLogin();
    }

    private void checkLogin(){
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {

                if (UtilHelpers.isUserLoggedIn(SplashActivity.this)){
                    startActivity(new Intent(context, HomeActivity.class));
                    overridePendingTransition(R.anim.trans_left_in, R.anim.trans_left_out);
                    finish();
                }else {
                    startActivity(new Intent(context, LandingActivity.class));
                    overridePendingTransition(R.anim.trans_left_in, R.anim.trans_left_out);
                    finish();
                }
            }
        },1500);
    }
}
