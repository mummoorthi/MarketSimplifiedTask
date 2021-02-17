package com.example.publicinfotask.ViewModel;

import com.example.publicinfotask.model.Example;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

public interface RetroIntro {

    @GET("repositories")
    Call<List<Example>> listRepos();
}
