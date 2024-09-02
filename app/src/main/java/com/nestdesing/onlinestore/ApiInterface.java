package com.nestdesign.onlinestore;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;

public interface ApiInterface {

    @POST("/login")
    Call<ApiResponse> loginUser(@Body String email, @Body String password);

    @POST("/register")
    Call<ApiResponse> registerUser(@Body String dni, @Body String email, @Body String nombre,
                                   @Body String apellido, @Body String direccion,
                                   @Body String telefono, @Body String ciudad,
                                   @Body String pais, @Body String password);

    @GET("/profile")
    Call<User> getUserProfile(@Header("Authorization") String token, @Body String email);

    @PUT("/profile/update")
    Call<ApiResponse> updateUserProfile(@Header("Authorization") String token,
                                        @Body String email, @Body String dni,
                                        @Body String nombre, @Body String apellido,
                                        @Body String direccion, @Body String telefono,
                                        @Body String ciudad, @Body String pais);
}
