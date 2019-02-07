package com.app.stack.project.ui;

import android.util.Log;

import com.app.stack.project.BuildConfig;
import com.app.stack.project.model.Users;
import com.app.stack.project.network.StackoverflowApi;
import com.app.stack.project.util.Constants;
import com.facebook.stetho.okhttp3.StethoInterceptor;
import okhttp3.OkHttpClient;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class HomePresenterImpl implements HomePresenter {

    private HomeView homeView;
    private StackoverflowApi api;
    private Call<Users> call;

    public HomePresenterImpl(HomeView homeView) {
        this.homeView = homeView;

        OkHttpClient.Builder builder = new OkHttpClient.Builder();
        if(BuildConfig.DEBUG) {
            builder.addNetworkInterceptor(new StethoInterceptor());
        }
        OkHttpClient client = builder.build();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Constants.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .client(client)
                .build();

        api = retrofit.create(StackoverflowApi.class);
    }

    @Override
    public void getUsers() {
        call = api.getUsers();
        call.enqueue(new Callback<Users>() {

            @Override
            public void onResponse(Call<Users> call, Response<Users> response) {
                if(response.code() == 200 && response.body() != null) {
                    Users users = response.body();
                    if(users != null && users.getItems().size() > 0) {
                        homeView.updateUsers(users.getItems());
                    }else {
                        homeView.showError();
                    }
                }else {
                    homeView.showError();
                }
            }

            @Override
            public void onFailure(Call<Users> call, Throwable t) {
                Log.e("SearchPresenterImpl","Exception",t);
                homeView.showError();
            }
        });
    }

    @Override
    public void onDestroy(){
        if(call != null && !call.isCanceled()){
            call.cancel();
        }
    }
}
