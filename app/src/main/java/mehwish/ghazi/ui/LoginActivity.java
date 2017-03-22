package mehwish.ghazi.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;

import mehwish.ghazi.R;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener {

    protected Button loginButton;
    protected Button forgetPassword;
    private Toolbar mToolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        super.setContentView(R.layout.activity_login);
        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setTitle("Login");
        initView();
    }

    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.login_button) {
            startActivity(new Intent(LoginActivity.this,HomeActivity.class));
            overridePendingTransition(R.anim.trans_left_in,R.anim.trans_left_out);
            finish();
        } else if (view.getId() == R.id.forget_password) {
            getSupportFragmentManager().beginTransaction().add(R.id.container_body,new ForgotPasswordFragment()).
                    addToBackStack("FORGOT_PASSWORD").commit();
        }
    }

    private void initView() {
        loginButton = (Button) findViewById(R.id.login_button);
        loginButton.setOnClickListener(LoginActivity.this);
        forgetPassword = (Button) findViewById(R.id.forget_password);
        forgetPassword.setOnClickListener(LoginActivity.this);
    }
}
