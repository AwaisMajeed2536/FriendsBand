package mehwish.ghazi.model;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by Devprovider on 3/11/2017.
 */

public class UserAccountModel implements Parcelable{
    private String firstName;
    private String lastName;
    private String email;
    private String password;
    private Gender gender;
    private String dob;
    private String cityName;
    private String mobileNo;
    private String profession;

    public UserAccountModel() {
    }

    public UserAccountModel(String firstName, String lastName, String email, String password, Gender gender, String dob, String cityName, String mobileNo, String profession) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.password = password;
        this.gender = gender;
        this.dob = dob;
        this.cityName = cityName;
        this.mobileNo = mobileNo;
        this.profession = profession;
    }

    public String getDob() {
        return dob;
    }

    public void setDob(String dob) {
        this.dob = dob;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getCityName() {
        return cityName;
    }

    public void setCityName(String cityName) {
        this.cityName = cityName;
    }

    public String getMobileNo() {
        return mobileNo;
    }

    public void setMobileNo(String mobileNo) {
        this.mobileNo = mobileNo;
    }

    public String getProfession() {
        return profession;
    }

    public void setProfession(String profession) {
        this.profession = profession;
    }

    public Gender getGender() {
        return gender;
    }

    public void setGender(Gender gender) {
        this.gender = gender;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {

    }

    public static enum Gender {MALE, FEMALE, OTHER}
}
