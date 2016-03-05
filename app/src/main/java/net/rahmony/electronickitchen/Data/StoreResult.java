package net.rahmony.electronickitchen.Data;


import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by RaHmOnY on 3/1/2016.
 */
public class StoreResult implements Parcelable {

    private int Store_ID;
    private String StoreName;
    private int Image;
    private String StoreDescription;
    private String Available;
    private int Seller_ID;
    private String msg;

    protected StoreResult(Parcel in) {
        Store_ID = in.readInt();
        StoreName = in.readString();
        Image = in.readInt();
        StoreDescription = in.readString();
        Available = in.readString();
        Seller_ID = in.readInt();
        msg = in.readString();
    }

    public static final Creator<StoreResult> CREATOR = new Creator<StoreResult>() {
        @Override
        public StoreResult createFromParcel(Parcel in) {
            return new StoreResult(in);
        }

        @Override
        public StoreResult[] newArray(int size) {
            return new StoreResult[size];
        }
    };

    public int getStore_ID() {
        return Store_ID;
    }

    public void setStore_ID(int store_ID) {
        Store_ID = store_ID;
    }

    public String getStoreName() {
        return StoreName;
    }

    public void setStoreName(String storeName) {
        StoreName = storeName;
    }

    public int getImage() {
        return Image;
    }

    public void setImage(int image) {
        Image = image;
    }

    public String getStoreDescription() {
        return StoreDescription;
    }

    public void setStoreDescription(String storeDescription) {
        StoreDescription = storeDescription;
    }

    public String getAvailable() {
        return Available;
    }

    public void setAvailable(String available) {
        Available = available;
    }

    public int getSeller_ID() {
        return Seller_ID;
    }

    public void setSeller_ID(int seller_ID) {
        Seller_ID = seller_ID;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public StoreResult(){}


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int flags) {
        parcel.writeString(msg);
    }
}