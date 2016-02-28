package net.rahmony.electronickitchen;

/**
 * Created by RaHmOnY on 2/29/2016.
 */

        import android.os.Parcel;
        import android.os.Parcelable;

public class LogInResult implements Parcelable {


    private String msg;
    private String firstName ;
    private String lastName ;
    private String email ;
    private String phoneNumber ;
    private String city ;
    private String address ;

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    private float lon ;
    private float lat ;

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public float getLon() {
        return lon;
    }

    public void setLon(float lon) {
        this.lon = lon;
    }

    public float getLat() {
        return lat;
    }

    public void setLat(float lat) {
        this.lat = lat;
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


    public String getFirstName() {
        return firstName ;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

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