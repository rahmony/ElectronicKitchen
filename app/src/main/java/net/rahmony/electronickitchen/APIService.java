package net.rahmony.electronickitchen;

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
    Call<Results>  createStore(@Body Store store);

    @GET("getAllStores")
    Call<Results> getAllStores();






    // need to review and amend
    @PUT("items")
    Call<Results> updateItem(@Body User user);

    // need to review and amend
    @DELETE("items/delete/{id}")
    Call<Results> deleteItem(@Path("id") String id);


}
