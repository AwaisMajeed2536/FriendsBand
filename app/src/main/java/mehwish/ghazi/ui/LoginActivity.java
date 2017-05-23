package mehwish.ghazi.ui;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;
import java.util.Timer;
import java.util.TimerTask;

import mehwish.ghazi.R;
import mehwish.ghazi.helper.UtilHelpers;
import mehwish.ghazi.model.UserAccountModel;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener, ValueEventListener {

    protected Button loginButton;
    protected Button forgetPassword;
    protected EditText userEmailET;
    protected EditText userPasswordET;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        super.setContentView(R.layout.activity_login);
        try {
            Toolbar mToolbar = (Toolbar) findViewById(R.id.toolbar);
            setSupportActionBar(mToolbar);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
            getSupportActionBar().setTitle("Login");
        } catch (Exception e) {
            Log.e("Login Activity", e.getMessage());
        }
        initView();
    }

    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.login_button) {
            if (checkInputs()) {
                UtilHelpers.showWaitDialog(this, "Checking Credentials...", "Please wait.");
                String email = userEmailET.getText().toString();
                email = email.replace(".", "_");
                DatabaseReference mRef = FirebaseDatabase.getInstance().
                        getReferenceFromUrl("https://friendsband-a3dc9.firebaseio.com/root/userData/" + email);
                mRef.keepSynced(true);
                mRef.addValueEventListener(this);
            }
        } else if (view.getId() == R.id.forget_password) {
            startActivity(new Intent(LoginActivity.this, ForgotPasswordActivity.class));
            overridePendingTransition(R.anim.trans_left_in, R.anim.trans_left_out);
        }
    }

    public boolean checkInputs() {
        if (!Patterns.EMAIL_ADDRESS.matcher(userEmailET.getText()).matches()) {
            userEmailET.setError("Enter a valid email address");
            userEmailET.requestFocus();
            return false;
        } else if (userPasswordET.getText().toString().isEmpty()) {
            userPasswordET.setError("Enter a password!");
            userPasswordET.requestFocus();
            return false;
        }
        return true;
    }

    private void initView() {
        loginButton = (Button) findViewById(R.id.login_button);
        loginButton.setOnClickListener(LoginActivity.this);
        forgetPassword = (Button) findViewById(R.id.forget_password);
        forgetPassword.setOnClickListener(LoginActivity.this);
        userEmailET = (EditText) findViewById(R.id.user_email_edittext);
        userPasswordET = (EditText) findViewById(R.id.user_password_edittext);
    }

    private final boolean checkCredentials(String password) {
        return userPasswordET.getText().toString().equals(password);
    }

    @Override
    public void onDataChange(DataSnapshot dataSnapshot) {
        UtilHelpers.dismissWaitDialog();
        if (dataSnapshot.getValue() == null) {
            UtilHelpers.showAlertDialog(LoginActivity.this, "Login Failed", "Invalid Credentials...");
        } else {
            try {
                UserAccountModel model =  dataSnapshot.getValue(UserAccountModel.class);
                if (checkCredentials(model.getPassword())) {
                    UtilHelpers.createLoginSession(LoginActivity.this, model);
                    startActivity(new Intent(LoginActivity.this, HomeActivity.class));
                    overridePendingTransition(R.anim.trans_left_in, R.anim.trans_left_out);
                    finish();
                }

            } catch (Exception e) {
                Log.e("LoginActivity", e.getMessage());
            }
        }
    }

    @Override
    public void onCancelled(DatabaseError databaseError) {

    }

    @Override
    public void onBackPressed() {
        finish();
        overridePendingTransition(0, R.anim.exit_activity);
        super.onBackPressed();
    } // onBackPressed
}
