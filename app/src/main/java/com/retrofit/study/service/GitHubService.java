package com.retrofit.study.service;

import com.retrofit.study.entity.PostQueryInfo;
import com.retrofit.study.entity.Repo;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;

/**
 * Created by wudong on 16/10/9.
 */
public interface GitHubService {

    @GET("users/{user}/repos")
    Call<List<Repo>> listRepos(@Path("user") String user);

    @POST("query")
    Call<PostQueryInfo> search(@Query("type") String type, @Query("postid") String postid);
}
