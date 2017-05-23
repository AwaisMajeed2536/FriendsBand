package mehwish.ghazi.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import mehwish.ghazi.R;
import mehwish.ghazi.helper.UtilHelpers;
import mehwish.ghazi.interfaces.AlertDialogCallback;

/**
 * Created by Devprovider on 3/23/2017.
 */

public class ForgotPasswordActivity extends AppCompatActivity implements View.OnClickListener{

    protected EditText fpEmailEt;
    protected Button fpEmailSubmitButton;
    private static boolean buttonClicked  = false;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot_password);
        initView();
    }

    @Override
    protected void onResume() {
        super.onResume();
        if(buttonClicked){
            UtilHelpers.showAlertDialog(this, "Email sent", "please check your inbox to get the new password",
                    "dismiss", new AlertDialogCallback() {
                        @Override
                        public void onClick() {
                            finish();
                        }
                    });
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        buttonClicked = false;
    }

    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.fp_email_submit_button) {
            buttonClicked = true;
            String email = fpEmailEt.getText().toString();
            Intent i = new Intent(Intent.ACTION_SEND);
            i.setType("message/rfc822");
            i.putExtra(Intent.EXTRA_EMAIL  , new String[]{"mehwish.g2536@gmail.com"});
            i.putExtra(Intent.EXTRA_SUBJECT, "Password Reset Request");
            i.putExtra(Intent.EXTRA_TEXT   , "Please reset the password for my following account...\n"+email);
            try {
                startActivity(Intent.createChooser(i, "Password Reset..."));
            } catch (android.content.ActivityNotFoundException ex) {
                Toast.makeText(this, "There are no email clients installed.", Toast.LENGTH_SHORT).show();
            }
        }
    }


    private void initView() {
        fpEmailEt = (EditText) findViewById(R.id.fp_email_et);
        fpEmailSubmitButton = (Button) findViewById(R.id.fp_email_submit_button);
        fpEmailSubmitButton.setOnClickListener(this);
    }
}
