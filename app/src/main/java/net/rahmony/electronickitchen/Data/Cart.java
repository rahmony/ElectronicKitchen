package net.rahmony.electronickitchen.Data;

/**
 * Created by pc on 12/03/16.
 */
public class Cart {
    private int Quantity;
    private int ID;
    private int Product_ID;
    private int Store_ID;
    private int Price;
    private String ProductName;
    private String StoreName;


    public int getQuantity(){return Quantity;}
    public void setQuantity(int Quantity){this.Quantity = Quantity;}

    public int getID(){return ID;}
    public void setID(int ID){this.ID = ID;}

    public int getProduct_ID(){return Product_ID;}
    public void setProduct_ID(int Product_ID){this.Product_ID = Product_ID;}

    public int getStoreID(){return Store_ID;}
    public void setStoreID(int Store_ID){this.Store_ID = Store_ID;}

    public int getPrice(){return Price;}
    public void setPrice(int Price){this.Price = Price;}

    public String getProductName(){return ProductName;}
    public void setProductName(String ProductName){this.ProductName = ProductName;}

    public String getStoreName(){return StoreName;}
    public void setStoreName(String StoreName){this.StoreName = StoreName;}
}
