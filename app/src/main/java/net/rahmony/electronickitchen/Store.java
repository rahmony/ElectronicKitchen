package net.rahmony.electronickitchen;

import java.security.PrivateKey;

/**
 * Created by pc on 26/02/16.
 */
public class Store {
    private String StoreName;
    private String StoreDescription;

    public String getStoreName(){return StoreName;}

    public void setStoreName(String storeName){StoreName = storeName;}

    public String getStoreDescription(){return StoreDescription;}

    public void setStoreDescription(String storeDescription){StoreDescription = storeDescription;}
}
