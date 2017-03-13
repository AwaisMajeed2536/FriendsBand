package mehwish.ghazi.ui;

import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.InputType;
import android.util.Patterns;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioGroup;

import java.io.FileNotFoundException;

import mehwish.ghazi.R;

/**
 * Created by Devprovider on 3/11/2017.
 */

public class SignupActivity extends AppCompatActivity implements View.OnClickListener, View.OnFocusChangeListener, View.OnTouchListener {
    public static final int IMAGE_KEY = 1;
    private Bitmap chosenProfileImage;
    protected ImageView profilePictuerSignup;
    protected EditText firstName;
    protected EditText lastName;
    protected EditText email;
    protected EditText password;
    protected ImageView showPassword;
    protected EditText confirmPassword;
    protected ImageView showConfirmPassword;
    protected RadioGroup genderRg;
    protected EditText cityName;
    protected EditText mobileNumber;
    protected EditText profession;
    protected Button signupButton;
    protected Toolbar mToolbar;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        super.setContentView(R.layout.activity_signup);
        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setTitle("Sign Up");
        initView();
    }

    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.profile_pictuer_signup) {
            Intent intent = new Intent(Intent.ACTION_PICK);
            intent.setType("image/*");
            startActivityForResult(intent,IMAGE_KEY);
        } else if (view.getId() == R.id.signup_button) {

        }
    }

    private void initView() {
        profilePictuerSignup = (ImageView) findViewById(R.id.profile_pictuer_signup);
        profilePictuerSignup.setOnClickListener(SignupActivity.this);
        firstName = (EditText) findViewById(R.id.first_name);
        firstName.setOnFocusChangeListener(this);
        lastName = (EditText) findViewById(R.id.last_name);
        lastName.setOnFocusChangeListener(this);
        email = (EditText) findViewById(R.id.email);
        email.setOnFocusChangeListener(this);
        password = (EditText) findViewById(R.id.password);
        password.setOnClickListener(this);
        password.setOnFocusChangeListener(this);
        showPassword = (ImageView) findViewById(R.id.show_password);
        showPassword.setOnTouchListener(this);
        confirmPassword = (EditText) findViewById(R.id.confirm_password);
        confirmPassword.setOnFocusChangeListener(this);
        showConfirmPassword = (ImageView) findViewById(R.id.show_confirm_password);
        showConfirmPassword.setOnTouchListener(this);
        genderRg = (RadioGroup) findViewById(R.id.gender_rg);
        cityName = (EditText) findViewById(R.id.city_name);
        cityName.setOnFocusChangeListener(this);
        mobileNumber = (EditText) findViewById(R.id.mobile_number);
        mobileNumber.setOnFocusChangeListener(this);
        profession = (EditText) findViewById(R.id.profession);
        profession.setOnFocusChangeListener(this);
        signupButton = (Button) findViewById(R.id.signup_button);
        signupButton.setOnClickListener(SignupActivity.this);
    }

    @Override
    public void onFocusChange(View v, boolean hasFocus) {
        String check;
        switch (v.getId()){
            case R.id.first_name:
                if (!hasFocus && firstName.getText().toString().isEmpty())
                    firstName.setError("First Name is required!");
                break;
            case R.id.last_name:
                if (!hasFocus && lastName.getText().toString().isEmpty())
                    lastName.setError("Last Name is required!");
                break;
            case R.id.email:
                check = email.getText().toString();
                if (!hasFocus) {
                    if (check.isEmpty() || !Patterns.EMAIL_ADDRESS.matcher(check).matches())
                        email.setError("Enter a valid email address");
                }
                break;
            case R.id.password:
                if(!hasFocus && password.getText().toString().length() < 6)
                    password.setError("Password should be six characters long");
                break;
            case R.id.confirm_password:
                check = password.getText().toString();
                if (!hasFocus) {
                    if (confirmPassword.getText().toString().isEmpty() || !confirmPassword.getText().toString().equals(check)) {
                        confirmPassword.setError("Passwords do not match");
                        showConfirmPassword.setVisibility(View.INVISIBLE);
                    }
                }
                else
                    showConfirmPassword.setVisibility(View.VISIBLE);
                break;
            case R.id.city_name:
                if (!hasFocus && cityName.getText().toString().isEmpty())
                    cityName.setError("City Name is required!");
                break;
            case R.id.mobile_number:
                if (!hasFocus && mobileNumber.getText().toString().isEmpty())
                    mobileNumber.setError("Mobile Number is required!");
                break;
            case R.id.profession:
                if (!hasFocus && profession.getText().toString().isEmpty())
                    profession.setError("Profession is required!");
                break;
            default:
                break;
        }
    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        if (v.getId() == R.id.show_password) {
            switch (event.getAction()) {
                case MotionEvent.ACTION_DOWN:
                    password.setInputType(InputType.TYPE_CLASS_TEXT);
                    return true;
                case MotionEvent.ACTION_UP:
                    password.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
                    return true;
            }
        } else if (v.getId() == R.id.show_confirm_password) {
            switch (event.getAction()) {
                case MotionEvent.ACTION_DOWN:
                    confirmPassword.setInputType(InputType.TYPE_CLASS_TEXT);
                    return true;
                case MotionEvent.ACTION_UP:
                    confirmPassword.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
                    return true;
            }
        }
        return false;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == IMAGE_KEY && resultCode == RESULT_OK  && data != null){
            Uri imageUri = data.getData();
            try{
                Bitmap imageBitmap = decodeUri(imageUri);
                profilePictuerSignup.setImageBitmap(imageBitmap);
            }catch (FileNotFoundException e){
                e.printStackTrace();
            }
        }
    }
    private Bitmap decodeUri(Uri selectedImage) throws FileNotFoundException{
        //Decode image size
        BitmapFactory.Options factoryOptions = new BitmapFactory.Options();
        factoryOptions.inJustDecodeBounds = true;
        BitmapFactory.decodeStream(getContentResolver().openInputStream(selectedImage), null, factoryOptions);

        //the new size we want to scale to
        final int REQUIRED_SIZE = 140;

        //find the correct scale value. It should be the power of 2.
        int widthTemp = factoryOptions.outWidth, heightTemp = factoryOptions.outHeight;
        int scale = 1;

        while (true){
            if (widthTemp /2 < REQUIRED_SIZE || heightTemp / 2 <REQUIRED_SIZE)
                break;
            widthTemp /=2;
            heightTemp /=2;
            scale *= 2;
        }
        //Decode with in sample size
        BitmapFactory.Options factoryOptions2 = new BitmapFactory.Options();
        factoryOptions2.inSampleSize = scale;
        return BitmapFactory.decodeStream(getContentResolver().openInputStream(selectedImage),null,factoryOptions2);
    }
}
