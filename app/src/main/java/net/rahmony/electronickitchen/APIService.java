package net.rahmony.electronickitchen;

import net.rahmony.electronickitchen.Data.LogInResult;
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

    @POST("createStore")
    Call<StoreResult>  createStore(@Body Store store);

    @GET("getAllStores")
    Call<List<Store>> getAllStores();

    @POST("hasStore")
    Call<StoreResult> hasStore(@Body Store store);


    // need to review and amend
    @PUT("items")
    Call<Results> updateItem(@Body User user);

    // need to review and amend
    @DELETE("items/delete/{id}")
    Call<Results> deleteItem(@Path("id") String id);


}
