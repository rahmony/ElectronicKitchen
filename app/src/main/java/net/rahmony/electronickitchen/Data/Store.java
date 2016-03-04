package net.rahmony.electronickitchen.Data;

import android.os.Parcel;
import android.os.Parcelable;

import java.security.PrivateKey;

/**
 * Created by pc on 26/02/16.
 */
public class Store {
    private int Store_ID;
    private String StoreName;
    private int Image;
    private String StoreDescription;
    private String Available;
    private int Seller_ID;
    private String msg;

    public int getStoreID(){return Store_ID;}
    public void setStoreID(int Store_ID){this.Store_ID = Store_ID;}

    public String getStoreName(){return StoreName;}
    public void setStoreName(String StoreName){this.StoreName = StoreName;}

    public int getImage(){return Image;}
    public void setImage(int Image){this.Image = Image;}

    public String getStoreDescription(){return StoreDescription;}
    public void setStoreDescription(String StoreDescription){this.StoreDescription = StoreDescription;}

    public String getAvailable(){return Available;}
    public void setAvailable(String Available){this.Available = Available;}

    public int getSeller_ID(){return Seller_ID;}
    public void setSeller_ID(int Seller_ID){this.Seller_ID = Seller_ID;}
/*
    protected LogInResult(Parcel in) {
        msg = in.readString();
    }

    public static final Parcelable.Creator<LogInResult> CREATOR = new Parcelable.Creator<LogInResult>() {
        @Override
        public LogInResult createFromParcel(Parcel in) {
            return new LogInResult(in);
        }

        @Override
        public LogInResult[] newArray(int size) {
            return new LogInResult[size];
        }
    };
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

    }*/
}
