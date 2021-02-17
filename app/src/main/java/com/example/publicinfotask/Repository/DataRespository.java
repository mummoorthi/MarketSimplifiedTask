package com.example.publicinfotask.Repository;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.publicinfotask.ViewModel.ConnectionSync;
import com.example.publicinfotask.model.Example;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DataRespository {

    public LiveData<List<Example>> getData() {
        final MutableLiveData<List<Example>> mutableLiveData = new MutableLiveData<>();
        ConnectionSync.onConnect().listRepos().enqueue(new Callback<List<Example>>() {
            @Override
            public void onResponse(@NonNull Call<List<Example>> call, @NonNull Response<List<Example>> response) {
                if (response.isSuccessful()){
                            Gson gson=new Gson();
                            mutableLiveData.setValue(response.body());
                        }

            }

            @Override
            public void onFailure(@NonNull Call<List<Example>> call,@NonNull Throwable t) {
                List<Example> dataResponse= new ArrayList<>();
                mutableLiveData.setValue(dataResponse);
            }
        });
        return mutableLiveData;
    }

}
