package mehwish.ghazi.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
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
        } else if (view.getId() == R.id.signup_already) {
            startActivity(new Intent(LandingActivity.this,LoginActivity.class));
        }
    }

    private void initView() {
        signupWithEmail = (Button) findViewById(R.id.signup_with_email);
        signupWithEmail.setOnClickListener(LandingActivity.this);
        signupAlready = (Button) findViewById(R.id.signup_already);
        signupAlready.setOnClickListener(LandingActivity.this);
    }

}
