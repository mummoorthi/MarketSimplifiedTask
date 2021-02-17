package com.example.publicinfotask.View;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import android.os.AsyncTask;
import android.os.Bundle;
import android.view.MenuItem;

import com.example.publicinfotask.R;
import com.example.publicinfotask.ViewModel.DataViewModel;
import com.example.publicinfotask.database.DatabaseClient;
import com.example.publicinfotask.database.DbDatapag;
import com.example.publicinfotask.databinding.ActivityMainBinding;
import com.example.publicinfotask.model.Example;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.List;

public class MainActivity extends AppCompatActivity implements BottomNavigationView.OnNavigationItemSelectedListener
{

    private ActivityMainBinding binding;
    DataViewModel dataViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        declareViews();
        setContentView(binding.getRoot());
    }

    private void declareViews() {
        dataViewModel = ViewModelProviders.of(MainActivity.this).get(DataViewModel.class);
        fetchData();
    }


    @Override
    public void onBackPressed() {
        if (getSupportFragmentManager().getBackStackEntryCount()>0){
            getSupportFragmentManager().popBackStack();
            return;
        }
        super.onBackPressed();
    }


    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        int itemId = item.getItemId();
        FragmentManager manager = getSupportFragmentManager();
        if (itemId == R.id.page_1) {
            boolean fragmentPopped = manager.popBackStackImmediate ("one", 0);
            if (!fragmentPopped && manager.findFragmentByTag("one") == null){ //fragment not in back stack, create it.
                getSupportFragmentManager().beginTransaction().add(R.id.IdBaseFrame,new ModelFragmentOne()).addToBackStack("").commit();
            }
        } else if (itemId == R.id.page_2) {
            boolean fragmentPopped = manager.popBackStackImmediate ("two", 0);
            if (!fragmentPopped && manager.findFragmentByTag("two") == null){ //fragment not in back stack, create it.
                getSupportFragmentManager().beginTransaction().add(R.id.IdBaseFrame,new ModelFragmentTwo()).addToBackStack("").commit();
            }
        } else if (itemId == R.id.page_3) {
            boolean fragmentPopped = manager.popBackStackImmediate ("three", 0);
            if (!fragmentPopped && manager.findFragmentByTag("three") == null){ //fragment not in back stack, create it.
                getSupportFragmentManager().beginTransaction().add(R.id.IdBaseFrame,new ModelFragmentThree()).addToBackStack("").commit();
            }
        }
        return true;
    }

    private class AsyncInsertContent extends AsyncTask<Void,Void,Void>{
        List<Example> listRepo;
        AsyncInsertContent(List<Example> listRepo){
            this.listRepo=listRepo;
        }

        @Override
        protected Void doInBackground(Void... voids) {
            if (listRepo!=null){
                for (int i=0;i<listRepo.size();i++){
                    Example example=listRepo.get(i);
                    DbDatapag task = new DbDatapag();
                    task.setRoomId(example.getId());
                    task.setName(example.getName());
                    task.setFullname(example.getFullName());
                    task.setLogin(example.getOwner().getLogin());
                    task.setType(example.getOwner().getType());
                    task.setDescription(example.getDescription());
                    task.setAvatarurl(example.getOwner().getAvatarUrl());
                    DatabaseClient.getInstance(MainActivity.this).getAppDatabase()
                            .taskDao()
                            .insetRepo(task);
                }
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            getSupportFragmentManager().beginTransaction().replace(R.id.IdBaseFrame,new ModelFragmentOne()).commit();
            binding.navigation.setOnNavigationItemSelectedListener(MainActivity.this);
        }
    }

    private void fetchData() {
        dataViewModel.getData().observe
                (this, new Observer<List<Example>>() {
                    @Override
                    public void onChanged(final List<Example> dataResponseModel) {
                        List<Example> listRepo= dataResponseModel;
                        if (listRepo!=null){
                            if (listRepo.size()>0){
                                new AsyncInsertContent(listRepo).execute();
                            }
                        }
                    }
                });
       /* ConnectionSync.onConnect().listRepos().enqueue(new Callback<List<Example>>() {
            @Override
            public void onResponse(@NonNull Call<List<Example>> call, @NonNull Response<List<Example>> response) {
                if (response.isSuccessful()){
                    List<Example> listRepo=response.body();
                    if (listRepo!=null){
                        if (listRepo.size()>0){
                            new AsyncInsertContent(listRepo).execute();
                        }
                    }
                }
            }

            @Override
            public void onFailure(@NonNull Call<List<Example>> call,@NonNull Throwable t) {

            }
        });*/
    }
}