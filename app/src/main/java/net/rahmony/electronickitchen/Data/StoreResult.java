package net.rahmony.electronickitchen.Data;


import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by RaHmOnY on 3/1/2016.
 */
public class StoreResult implements Parcelable {

    private int store_ID;
    private String storeName;
    private int image;
    private String storeDescription;
    private String available;
    private int seller_ID;
    private String msg;

    protected StoreResult(Parcel in) {
        store_ID = in.readInt();
        storeName = in.readString();
        image = in.readInt();
        storeDescription = in.readString();
        available = in.readString();
        seller_ID = in.readInt();
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

    public int getStoreID(){return store_ID;}
    public void setStoreID(int store_ID){this.store_ID = store_ID;}

    public String getStoreName(){return storeName;}
    public void setStoreName(String storeName){this.storeName = storeName;}

    public int getImage(){return image;}
    public void setImage(int Image){this.image = image;}

    public String getStoreDescription(){return storeDescription;}
    public void setStoreDescription(String storeDescription){this.storeDescription = storeDescription;}

    public String getAvailable(){return available;}
    public void setAvailable(String available){this.available = available;}

    public int getSeller_ID(){return seller_ID;}
    public void setSeller_ID(int seller_ID){this.seller_ID = seller_ID;}

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