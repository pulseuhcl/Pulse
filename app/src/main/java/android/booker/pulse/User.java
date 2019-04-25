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
    }

    public User() {
    }

    protected User(Parcel in) {
        this.userName = in.readString();
        this.password = in.readString();
        this.firstName = in.readString();
        this.lastName = in.readString();
        this.initials = in.readString();
        this.phoneNumber = in.readString();
        this.pinNumber = in.readString();
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
