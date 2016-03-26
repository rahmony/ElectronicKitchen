package net.rahmony.electronickitchen;

import net.rahmony.electronickitchen.Data.Cart;
import net.rahmony.electronickitchen.Data.LogInResult;
import net.rahmony.electronickitchen.Data.Product;
import net.rahmony.electronickitchen.Data.Results;
import net.rahmony.electronickitchen.Data.Store;
import net.rahmony.electronickitchen.Data.StoreResult;
import net.rahmony.electronickitchen.Data.User;

import java.util.List;

import retrofit.*;
import retrofit.http.*;

/**
 * Created by RaHmOnY on 2/14/2016.
 */
public interface APIService {


    @GET("getAllUsers")
    Call<Results> getAllUsers();

    @POST("signup")
    Call<Results> signup(@Body User user);

    @POST("login")
    Call<LogInResult> login(@Body User user);

    @POST("addNewCostumer")
    Call<LogInResult> addNewCostumer(@Body User user);

    @POST("addNewSeller")
    Call<LogInResult> addNewSeller(@Body User user);

    @POST("addNewDriver")
    Call<LogInResult> addNewDriver(@Body User user);

    @POST("createStore")
    Call<StoreResult>  createStore(@Body Store store);

    @GET("getAllStores")
    Call<List<Store>> getAllStores();

    @POST("getMyProducts")
    Call<List<Product>> getMyProducts(@Body Product product);

    @POST("addCart")
    Call<Cart> addCart(@Body Cart cart);

    @POST("getCart")
    Call<List<Cart>> getCart(@Body Cart cart);

    @POST("hasStore")
    Call<StoreResult> hasStore(@Body Store store);

    @POST("addNewCustomer")
    Call<Results> addNewCustomer(@Body User user);

    @POST("addNewProduct")
    Call<Results> addNewProduct(@Body Product product);

    @POST("confirmOrder")
    Call<Cart> confirmOrder(@Body Cart cart);

    @POST("deleteFromCart")
    Call<Cart> deleteFromCart(@Body Cart cart);

    /* need to review and amend
    @PUT("items")
    Call<Results> updateItem(@Body User user);

    @DELETE("items/delete/{id}")
    Call<Results> deleteItem(@Path("id") String id);

    */


}
