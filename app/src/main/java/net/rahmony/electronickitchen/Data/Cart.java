package net.rahmony.electronickitchen.Data;

/**
 * Created by pc on 12/03/16.
 */
public class Cart {
    private int Invoice_ID;
    private int Quantity;
    private int Product_ID;
    private int ID;
    private int Store_ID;
    private int Price;
    private int Seller_ID;
    private String ProductName;
    private String StoreName;
    private String FirstName;
    private String LastName;
    private String PhoneNumber;
    private String Address;
    private String Status;


    public int getInvoice_ID() {
        return Invoice_ID;
    }
    public void setInvoice_ID(int Invoice_ID) {
        this.Invoice_ID = Invoice_ID;
    }


    public int getQuantity() {
        return Quantity;
    }
    public void setQuantity(int Quantity) {
        this.Quantity = Quantity;
    }


    public int getProduct_ID() {
        return Product_ID;
    }
    public void setProduct_ID(int Product_ID) {
        this.Product_ID = Product_ID;
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
    public void setStore_ID(int Store_ID) {
        this.Store_ID = Store_ID;
    }


    public int getPrice() {
        return Price;
    }
    public void setPrice(int Price) {
        this.Price = Price;
    }

    public int getSeller_ID() {
        return Seller_ID;
    }
    public void setSeller_ID(int Seller_ID) {
        this.Seller_ID = Seller_ID;
    }

    public String getProductName() {
        return ProductName;
    }
    public void setProductName(String ProductName) {
        this.ProductName = ProductName;
    }

    public String getStoreName() {
        return StoreName;
    }
    public void setStoreName(String StoreName) {
        this.StoreName = StoreName;
    }

    public String getFirstName() {
        return FirstName;
    }
    public void setFirstName(String FirstName) {
        this.FirstName = FirstName;
    }

    public String getLastName() {
        return LastName;
    }
    public void setLastName(String LastName) {
        this.LastName = LastName;
    }

    public String getPhoneNumber() {
        return PhoneNumber;
    }
    public void setPhoneNumber(String PhoneNumber) {
        this.PhoneNumber = PhoneNumber;
    }

    public String getAddress() {
        return Address;
    }
    public void setAddress(String Address) {
        this.Address = Address;
    }

    public String getStatus() {
        return Status;
    }

    public void setStatus(String status) {
        Status = status;
    }
}
