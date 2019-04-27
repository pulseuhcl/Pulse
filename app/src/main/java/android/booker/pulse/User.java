package android.booker.pulse;

import android.os.Parcel;
import android.os.Parcelable;

public class User implements Parcelable {
    private String userName;
    private String password;
    private String firstName;
    private String lastName;
    private String initials;
    private String phoneNumber;
    private String pinNumber;
    private String newPassword;
    private String confirmPassword;

    public String getUserName(){
        return userName;
    }

    public String getPassword(){
        return password;
    }

    public String getPhoneNumber() {return phoneNumber; }

    public String getFirstName(){
        return firstName;
    }

    public String getLastName(){
        return lastName;
    }

    public String getInitials() { return initials; }

    public String getPinNumber() {return pinNumber; }

    public String getNewPassword() {return newPassword; }

    public String getConfirmPassword() {return confirmPassword; }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setPhoneNumber(String phoneNumber) {this.phoneNumber = phoneNumber;
    }
    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public void setInitials(String initials) {
        this.initials = initials;
    }

    public void setPinNumber(String pinNumber) {this.pinNumber = pinNumber; }

    public void setNewPassword(String newPinNumber) {this.newPassword = newPinNumber; }

    public void setConfirmPassword(String confirmPinNumber) {this.confirmPassword = confirmPinNumber; }

    public User(String username, String password, String firstName, String lastName, String initials,
                String phoneNumber, String pinNumber, String newPassword, String confirmPassword) {
        this.userName = username;
        this.password = password;
        this.firstName = firstName;
        this.lastName = lastName;
        this.initials = initials;
        this.phoneNumber = phoneNumber;
        this.pinNumber = pinNumber;
        this.newPassword = newPassword;
        this.confirmPassword = confirmPassword;

    }

    public User(){}

    // constructor to be used on Login_Page
    public User(String userName, String password){
        this.userName = userName;
        this.password = password;
    }

    // constructor to be used on Profile_Page
    public User(String firstName, String lastName, String initials, String userName, String password, String newPassword,
                String confirmPassword, String phoneNumber){
        this.firstName = firstName;
        this.lastName = lastName;
        this.initials = initials;
        this.userName = userName;
        this.password = password;
        this.newPassword = newPassword;
        this.confirmPassword = confirmPassword;
        this.phoneNumber = phoneNumber;

//        currentUser.setUserName(userNameEditText.getText().toString());
//        currentUser.setPassword(userNameEditText.getText().toString());
//        currentUser.setNewPassword(currentPasswordEditText.getText().toString());
//        currentUser.setConfirmPassword(currentPasswordEditText.getText().toString());
//        currentUser.setPhoneNumber(phoneEditText.getText().toString());
    }
    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel out, int flags) {
        out.writeString(this.userName);
        out.writeString(this.password);
        out.writeString(this.firstName);
        out.writeString(this.lastName);
        out.writeString(this.initials);
        out.writeString(this.phoneNumber);
        out.writeString(this.pinNumber);
        out.writeString(this.newPassword);
        out.writeString(this.confirmPassword);
    }

    protected User(Parcel in) {
        this.userName = in.readString();
        this.password = in.readString();
        this.firstName = in.readString();
        this.lastName = in.readString();
        this.initials = in.readString();
        this.phoneNumber = in.readString();
        this.pinNumber = in.readString();
        this.newPassword = in.readString();
        this.confirmPassword = in.readString();
    }

    public static final Creator<User> CREATOR = new Creator<User>() {
        @Override
        public User createFromParcel(Parcel in) {
            return new User(in);
        }

        @Override
        public User[] newArray(int size) {
            return new User[size];
        }
    };
}
