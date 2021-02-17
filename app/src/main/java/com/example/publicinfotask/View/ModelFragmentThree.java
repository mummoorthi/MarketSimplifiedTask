package com.example.publicinfotask.View;

import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.publicinfotask.Adapters.RepoListAdapter;
import com.example.publicinfotask.database.DatabaseClient;
import com.example.publicinfotask.database.DbDatapag;
import com.example.publicinfotask.Interfaces.InterfaceDetailSection;
import com.example.publicinfotask.R;
import com.example.publicinfotask.databinding.LayoutModelFragmentBinding;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;

public class ModelFragmentThree extends Fragment implements InterfaceDetailSection {
    private LayoutModelFragmentBinding binding;
    private List<DbDatapag> listRepo;
    private RepoListAdapter adapter;
    private boolean loadmore=false;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        binding = LayoutModelFragmentBinding.inflate(inflater, container, false);
        declareViews();
        initScrollListener();
        return binding.getRoot();
    }


    private void initScrollListener() {
        binding.IdRecylerRepo.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
            }

            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);

                LinearLayoutManager linearLayoutManager = (LinearLayoutManager) recyclerView.getLayoutManager();

                if (!loadmore) {
                    if (linearLayoutManager != null && linearLayoutManager.findLastCompletelyVisibleItemPosition() == listRepo.size() - 1) {
                        //bottom of list!
                        loadmore = true;
                        showLoading(true);
                        new Handler().postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                new AsyncGetComments(true).execute();
                            }
                        },1000);
                    }
                }
            }
        });


    }

    private void showLoading(boolean isLoading){
        if (isLoading){
            listRepo.add(null);
            adapter.notifyItemInserted(listRepo.size()-1);
        }else {
            int position=listRepo.size()-1;
            if (listRepo.get(position)==null){
                listRepo.remove(position);
                adapter.notifyItemRemoved(position);
            }
        }
    }

    private void declareViews() {
        listRepo=new ArrayList<>();
        adapter=new RepoListAdapter(listRepo,this);
        binding.IdRecylerRepo.setLayoutManager(new LinearLayoutManager(getActivity()));
        binding.IdRecylerRepo.setAdapter(adapter);
        fetchData();
    }

    private void fetchData() {
        new AsyncGetComments(false).execute();
    }
    private class AsyncGetComments extends AsyncTask<Void,List<DbDatapag>,List<DbDatapag>> {

        boolean isFromLoadMore;

        AsyncGetComments(boolean isFromLoadMore){
            this.isFromLoadMore=isFromLoadMore;
        }

        @Override
        protected List<DbDatapag> doInBackground(Void... voids) {
            return DatabaseClient.getInstance(getActivity()).getAppDatabase()
                    .taskDao()
                    .getAllRepos(listRepo.size());
        }

        @Override
        protected void onPostExecute(List<DbDatapag> dbData) {
            super.onPostExecute(dbData);
            if (isFromLoadMore){
                showLoading(false);
            }
            loadmore=false;
            if (dbData != null) {
                listRepo.addAll(dbData);
                adapter.notifyDataSetChanged();
            }
        }
    }
    @Override
    public void onClick(int position) {
        ModelFragmentDetail detail=new ModelFragmentDetail();
        Bundle bundle = new Bundle();
        bundle.putString("data", new Gson().toJson(listRepo.get(position)));
        detail.setArguments(bundle);
        getActivity().getSupportFragmentManager().beginTransaction().add(R.id.IdBaseFrame,detail,"three").addToBackStack("three").commit();
    }
}
