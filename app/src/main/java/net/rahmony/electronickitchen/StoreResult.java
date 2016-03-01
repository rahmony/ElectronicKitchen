package net.rahmony.electronickitchen;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by RaHmOnY on 3/1/2016.
 */
public class StoreResult implements Parcelable {

    private String StoreName;


    protected StoreResult(Parcel in) {
        StoreName = in.readString();
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


    public String getStoreName() {
        return StoreName;
    }

    public void setStoreName(String storeName) {
        StoreName = storeName;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int flags) {
        parcel.writeString(StoreName);
    }
}
