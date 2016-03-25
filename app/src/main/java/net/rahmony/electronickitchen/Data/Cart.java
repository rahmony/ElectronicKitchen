package net.rahmony.electronickitchen.Data;

/**
 * Created by pc on 12/03/16.
 */
public class Cart {
    private int Quantity;
    private int Product_ID;
    private int ID;
    private int Store_ID;
    private int Price;
    private String ProductName;
    private String StoreName;


    public int getQuantity() {
        return Quantity;
    }

    public void setQuantity(int quantity) {
        Quantity = quantity;
    }

    public int getProduct_ID() {
        return Product_ID;
    }

    public void setProduct_ID(int product_ID) {
        Product_ID = product_ID;
    }

    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public int getStore_ID() {
        return Store_ID;
    }

    public void setStore_ID(int store_ID) {
        Store_ID = store_ID;
    }

    public int getPrice() {
        return Price;
    }

    public void setPrice(int price) {
        Price = price;
    }

    public String getProductName() {
        return ProductName;
    }

    public void setProductName(String productName) {
        ProductName = productName;
    }

    public String getStoreName() {
        return StoreName;
    }

    public void setStoreName(String storeName) {
        StoreName = storeName;
    }
}
