package net.rahmony.electronickitchen.Data;



/**
 * Created by RaHmOnY on 3/1/2016.
 */
public class StoreResult{

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

    public StoreResult(){}


}