package com.example.publicinfotask.ViewModel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.example.publicinfotask.Repository.DataRespository;
import com.example.publicinfotask.model.Example;

import java.util.List;


public class DataViewModel extends AndroidViewModel {
        private DataRespository dataRespository;
        public DataViewModel(@NonNull Application application) {
            super(application);
            dataRespository = new DataRespository();
        }

        public LiveData<List<Example>> getData() {
            return dataRespository.getData();
        }

}
