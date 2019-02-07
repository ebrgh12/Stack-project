package com.app.stack.project.network;

import com.app.stack.project.model.Users;
import retrofit2.Call;
import retrofit2.http.GET;

public interface StackoverflowApi {

    @GET("/2.2/users?site=stackoverflow")
    Call<Users> getUsers();

}
