package com.retrofit.study;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.robot.et.common.lib.http.cookie.CookieManger;
import com.retrofit.study.entity.PostQueryInfo;
import com.retrofit.study.entity.Repo;
import com.retrofit.study.service.GitHubService;

import java.util.List;

import butterknife.ButterKnife;
import butterknife.OnClick;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {

    private Context context;
    private OkHttpClient okHttpClient;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        init();
        ButterKnife.bind(this);
    }

    private void init() {
        context = this;
        okHttpClient = new OkHttpClient.Builder()
                .addInterceptor(new HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY))
                .cookieJar(new CookieManger(context))
                .build();
    }

    @OnClick(R.id.button)
    public void submit(View view) {
        Retrofit retrofit = new Retrofit.Builder()
                .client(okHttpClient)
                .baseUrl("https://api.github.com/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        GitHubService service = retrofit.create(GitHubService.class);
        Call<List<Repo>> call = service.listRepos("tony1213");
        call.enqueue(new Callback<List<Repo>>() {
            @Override
            public void onResponse(Call<List<Repo>> call, Response<List<Repo>> response) {
                Log.e("APP", "isSuccessful:" + response.isSuccessful() + ",Size:" + response.body().size());

            }

            @Override
            public void onFailure(Call<List<Repo>> call, Throwable t) {
                Log.e("App", "onFailure");
                t.printStackTrace();
            }
        });
    }

    @OnClick(R.id.button2)
    public void submit2(View view) {
        Retrofit retrofit = new Retrofit.Builder()
                .client(okHttpClient)
                .baseUrl("http://www.kuaidi100.com/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        GitHubService apiService = retrofit.create(GitHubService.class);
        Call<PostQueryInfo> call = apiService.search("yuantong", "500379523313");
        call.enqueue(new Callback<PostQueryInfo>() {
            @Override
            public void onResponse(Call<PostQueryInfo> call, Response<PostQueryInfo> response) {
                Log.e("APP", "message:" + response.body().getMessage() + ",Nu:" + response.body().getNu() + ",status:" + response.body().getStatus());
            }

            @Override
            public void onFailure(Call<PostQueryInfo> call, Throwable t) {
                t.printStackTrace();
            }
        });
    }
}
