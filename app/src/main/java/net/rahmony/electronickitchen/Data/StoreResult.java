package net.rahmony.electronickitchen.Data;



/**
 * Created by RaHmOnY on 3/1/2016.
 */
public class StoreResult{

    private int store_ID;
    private String storeName;
    private int image;
    private String storeDescription;
    private String available;
    private int seller_ID;
    private String msg;

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


}