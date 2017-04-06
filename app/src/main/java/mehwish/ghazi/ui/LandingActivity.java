package mehwish.ghazi.ui;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import mehwish.ghazi.R;

/**
 * Created by Devprovider on 3/11/2017.
 */

public class LandingActivity extends AppCompatActivity implements View.OnClickListener {
    protected Button signupWithEmail;
    protected Button signupAlready;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        super.setContentView(R.layout.activity_landing);
        initView();

    }

    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.signup_with_email) {
            startActivity(new Intent(LandingActivity.this,SignupActivity.class));
            overridePendingTransition(R.anim.trans_left_in,R.anim.trans_left_out);
        } else if (view.getId() == R.id.signup_already) {
            startActivity(new Intent(LandingActivity.this,LoginActivity.class));
            overridePendingTransition(R.anim.trans_left_in,R.anim.trans_left_out);
        }
    }

    private void initView() {
        signupWithEmail = (Button) findViewById(R.id.signup_with_email);
        signupWithEmail.setOnClickListener(LandingActivity.this);
        signupAlready = (Button) findViewById(R.id.signup_already);
        signupAlready.setOnClickListener(LandingActivity.this);
    }

    @Override
    public void onBackPressed() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this).setIcon(R.mipmap.warning_icon)
                .setTitle("EXIT?").setMessage("Do you want to exit the app?")
                .setPositiveButton("YES", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                        finish();
                    }
                }).setNegativeButton("CANCEL", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
        AlertDialog exitConfirmationDialog = builder.create();
        exitConfirmationDialog.show();
    }
}
