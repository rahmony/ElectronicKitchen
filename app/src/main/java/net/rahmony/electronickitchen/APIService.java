package net.rahmony.electronickitchen;

import retrofit.*;
import retrofit.http.*;

/**
 * Created by RaHmOnY on 2/14/2016.
 */
public interface APIService {


    @GET("items")
    Call<Results> loadItems();

    @POST("items")
    Call<Results> insertItem(@Body User user);

    @PUT("items")
    Call<Results> updateItem(@Body User user);

    @DELETE("items/delete/{id}")
    Call<Results> deleteItem(@Path("id") String id);


}
