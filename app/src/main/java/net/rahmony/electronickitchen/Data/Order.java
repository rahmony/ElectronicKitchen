package net.rahmony.electronickitchen.Data;

/**
 * Created by pc on 07/03/16.
 */
public class Order {
    private int Quantity;
    private int ID;
    private int Product_ID;

    public int getQuantity(){return Quantity;}
    public void setQuantity(int Quantity){this.Quantity = Quantity;}

    public int getID(){return ID;}
    public void setID(int ID){this.ID = ID;}

    public int getProduct_ID(){return Product_ID;}
    public void setProduct_ID(int Product_ID){this.Product_ID = Product_ID;}

}
