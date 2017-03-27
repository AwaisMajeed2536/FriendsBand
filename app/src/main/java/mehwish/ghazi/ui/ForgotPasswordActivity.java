package mehwish.ghazi.ui;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import mehwish.ghazi.R;

/**
 * Created by Devprovider on 3/23/2017.
 */

public class ForgotPasswordActivity extends AppCompatActivity implements View.OnClickListener{

    protected EditText fpEmailEt;
    protected Button fpEmailSubmitButton;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot_password);
        initView();
    }

    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.fp_email_submit_button) {
            Toast.makeText(ForgotPasswordActivity.this, "Check your email to reset password!", Toast.LENGTH_SHORT).show();
            overridePendingTransition(R.anim.trans_right_out,R.anim.trans_right_in);
            finish();
        }
    }


    private void initView() {
        fpEmailEt = (EditText) findViewById(R.id.fp_email_et);
        fpEmailSubmitButton = (Button) findViewById(R.id.fp_email_submit_button);
        fpEmailSubmitButton.setOnClickListener(this);
    }
}
