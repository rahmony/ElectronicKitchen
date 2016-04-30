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

    @POST("getOrder")
    Call<List<Cart>> getOrder(@Body Cart cart);

    @POST("getOrderDetails")
    Call<List<Cart>> getOrderDetails(@Body Cart cart);

    @POST("acceptOrder")
    Call<Cart> acceptOrder(@Body Cart cart);

    @POST("denialOrder")
    Call<Cart> denialOrder(@Body Cart cart);

    @POST("acceptedOrder")
    Call<List<Cart>> acceptedOrder(@Body Cart cart);

    @POST("acceptedOrderDetails")
    Call<List<Cart>> acceptedOrderDetails(@Body Cart cart);

    @POST("readyForDelivery")
    Call<Cart> readyForDelivery(@Body Cart cart);

    @POST("trackingForCustomer")
    Call<List<Cart>> trackingForCustomer(@Body Cart cart);

    @POST("getInvoiceStatus")
    Call<Cart> getInvoiceStatus(@Body Cart cart);

    @POST("trackingForDriver")
    Call<List<Cart>> trackingForDriver(@Body Cart cart);

    @POST("trackingForDriverDetails")
    Call<List<Cart>> trackingForDriverDetails(@Body Cart cart);

    @POST("trackingDeliveringOrder")
    Call<List<Cart>> trackingDeliveringOrder(@Body Cart cart);

    @POST("getSellerData")
    Call<Cart> getSellerData(@Body Cart cart);

    @POST("isDriverDelivering")
    Call<Cart> isDriverDelivering(@Body Cart cart);

    @POST("acceptTrackingForDriverDetails")
    Call<Cart> acceptTrackingForDriverDetails(@Body Cart cart);

    @POST("forgetPassword")
    Call<LogInResult> forgetPassword(@Body User user);

    @POST("changeStoreName")
    Call<StoreResult> changeStoreName(@Body Store store);

    @POST("changeStoreDescription")
    Call<StoreResult> changeStoreDescription(@Body Store store);

    @POST("changeProductName")
    Call<Product> changeProductName(@Body Product product);

    @POST("customerReceivedOrder")
    Call<Cart> customerReceivedOrder(@Body Cart cart);

    /* need to review and amend
    @PUT("items")
    Call<Results> updateItem(@Body User user);

    @DELETE("items/delete/{id}")
    Call<Results> deleteItem(@Path("id") String id);

    */


}
