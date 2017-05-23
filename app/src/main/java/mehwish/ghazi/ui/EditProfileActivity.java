package mehwish.ghazi.ui;

import android.graphics.Bitmap;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.NumberPicker;
import android.widget.RadioGroup;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import mehwish.ghazi.R;
import mehwish.ghazi.helper.UtilHelpers;
import mehwish.ghazi.model.UserAccountModel;

public class EditProfileActivity extends AppCompatActivity implements View.OnClickListener {

    private Bitmap chosenProfileImage;
    protected EditText firstName;
    protected ImageView profilePictuerSignup;
    protected EditText lastName;
    protected EditText password;
    protected EditText cityName;
    protected EditText mobileNumber;
    protected EditText profession;
    protected Button signupButton;
    protected Toolbar mToolbar;
    private UserAccountModel oldData;
    private DatabaseReference updateRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_profile);
        updateRef = FirebaseDatabase.getInstance().getReferenceFromUrl("https://friendsband-a3dc9.firebaseio.com/root/userData/");
        initView();
        displayProfileData();
    }

    private void initView() {
        profilePictuerSignup = (ImageView) findViewById(R.id.profile_pictuer_signup);
        profilePictuerSignup.setOnClickListener(this);
        firstName = (EditText) findViewById(R.id.first_name);
        lastName = (EditText) findViewById(R.id.last_name);
        password = (EditText) findViewById(R.id.password);
        cityName = (EditText) findViewById(R.id.city_name);
        mobileNumber = (EditText) findViewById(R.id.mobile_number);
        profession = (EditText) findViewById(R.id.profession);
        signupButton = (Button) findViewById(R.id.signup_button);
        signupButton.setOnClickListener(this);
    }

    private void displayProfileData(){
        NullPointerException up = new NullPointerException();
        try{
            oldData = UtilHelpers.getLoggedInUser();
            if (oldData != null) {
                firstName.setText(oldData.getFirstName());
                lastName.setText(oldData.getLastName());
                password.setText(oldData.getPassword());
                cityName.setText(oldData.getCityName());
                mobileNumber.setText(oldData.getMobileNo());
                profession.setText(oldData.getProfession());
            } else throw up;

        }catch (NullPointerException e){
            e.printStackTrace();
        }

    }

    @Override
    public void onClick(View v) {
        if(v.getId() == R.id.profile_update_button){
            UserAccountModel newData = new UserAccountModel();
            newData.setFirstName(firstName.getText().toString());
            newData.setLastName(lastName.getText().toString());
            newData.setEmail(oldData.getEmail());
            newData.setPassword(password.getText().toString());
            newData.setGender(oldData.getGender());
            newData.setDob(oldData.getDob());
            newData.setCityName(cityName.getText().toString());
            newData.setMobileNo(mobileNumber.getText().toString());
            newData.setProfession(profession.getText().toString());
            updateRef.child(newData.getEmail()).setValue(newData);
        }
    }
}
