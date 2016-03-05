package net.rahmony.electronickitchen.Data;

/**
 * Created by RaHmOnY on 2/29/2016.
 */

        import android.os.Parcel;
        import android.os.Parcelable;

public class LogInResult implements Parcelable {


    private String msg;
    private int ID;
    private String FirstName ;
    private String LastName ;
    private String Email ;
    private String PhoneNumber ;
    private String City ;
    private String Address ;
    private float Lon ;
    private float Lat ;

    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public String getFirstName() {
        return FirstName;
    }

    public void setFirstName(String firstName) {
        FirstName = firstName;
    }

    public String getLastName() {
        return LastName;
    }

    public void setLastName(String lastName) {
        LastName = lastName;
    }

    public String getEmail() {
        return Email;
    }

    public void setEmail(String email) {
        Email = email;
    }

    public String getPhoneNumber() {
        return PhoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        PhoneNumber = phoneNumber;
    }

    public String getCity() {
        return City;
    }

    public void setCity(String city) {
        City = city;
    }

    public String getAddress() {
        return Address;
    }

    public void setAddress(String address) {
        Address = address;
    }

    public float getLon() {
        return Lon;
    }

    public void setLon(float lon) {
        Lon = lon;
    }

    public float getLat() {
        return Lat;
    }

    public void setLat(float lat) {
        Lat = lat;
    }

    protected LogInResult(Parcel in) {
        msg = in.readString();
    }

    public static final Creator<LogInResult> CREATOR = new Creator<LogInResult>() {
        @Override
        public LogInResult createFromParcel(Parcel in) {
            return new LogInResult(in);
        }

        @Override
        public LogInResult[] newArray(int size) {
            return new LogInResult[size];
        }
    };


    public void setMsg(String msg) {

        this.msg = msg;
    }


    public String getMsg() {
        return msg;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(msg);

    }
}