package com.example.publicinfotask.View;

import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.bumptech.glide.Glide;
import com.example.publicinfotask.Adapters.CommentListAdapter;
import com.example.publicinfotask.database.DatabaseClient;
import com.example.publicinfotask.database.DbData;
import com.example.publicinfotask.database.DbDatapag;
import com.example.publicinfotask.R;
import com.example.publicinfotask.databinding.LayoutModelFragmentDetailsBinding;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.util.ArrayList;
import java.util.List;

public class ModelFragmentDetail  extends Fragment implements View.OnClickListener {
    private LayoutModelFragmentDetailsBinding binding;
    private List<DbData> listComments;
    private CommentListAdapter adapter;
    private DbDatapag data;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        binding = LayoutModelFragmentDetailsBinding.inflate(inflater, container, false);
        declareViews();
        return binding.getRoot();
    }

    private void declareViews() {
        listComments=new ArrayList<>();
        adapter=new CommentListAdapter(listComments);
        binding.IdRecylerComment.setLayoutManager(new LinearLayoutManager(getActivity()));
        binding.IdRecylerComment.setAdapter(adapter);
        binding.IdPostComment.setOnClickListener(this);
        binding.IdDetailBackBtn.setOnClickListener(this);
        getContent();
        getCommentsFromDb();
    }

    private void getContent() {
        if (getArguments()!=null){
            if (getArguments().getString("data")!=null){
                data=new Gson().fromJson(getArguments().getString("data"),new TypeToken<DbDatapag>(){}.getType());
                setDetails();
            }
        }
    }

    private void setDetails() {
        if (data!=null){
            Glide.with(getActivity()).load(data.getAvatarurl()).into(binding.IdDetailImgContent);
            String content="Name : "+data.getName();
            binding.IdDetailTxtName.setText(content);
            content="FullName : "+data.getFullname();
            binding.IdDetailTxtFullName.setText(content);
            content="Description : "+data.getDescription();
            binding.IdDetailTxtFullDescription.setText(content);
        }
    }

    private class AsyncGetComments extends AsyncTask<Void,List<DbData>,List<DbData>>{


        @Override
        protected List<DbData> doInBackground(Void... voids) {
            return DatabaseClient.getInstance(getActivity()).getAppDatabase()
                    .taskDao()
                    .getAll(data.getId());
        }

        @Override
        protected void onPostExecute(List<DbData> dbData) {
            super.onPostExecute(dbData);
            if (dbData != null) {
                ModelFragmentDetail.this.listComments.clear();
                ModelFragmentDetail.this.listComments.addAll(dbData);
                adapter.notifyDataSetChanged();
            }
            binding.IdScrollContent.post(() -> binding.IdScrollContent.fullScroll(View.FOCUS_DOWN));

        }
    }

    private void getCommentsFromDb() {
        if (data!=null) {
            new AsyncGetComments().execute();
        }
    }


    @Override
    public void onClick(View v) {
        if (v.getId()== R.id.IdPostComment){
            validateContent();
        }else if (v.getId()==R.id.IdDetailBackBtn){
            if (getActivity().getSupportFragmentManager().getBackStackEntryCount()>0) {
                getActivity().getSupportFragmentManager().popBackStack();
            }
        }
    }

    private class AsyncDb extends AsyncTask<Void,Void,Void>{
        DbDatapag data;
        String content;

        AsyncDb(DbDatapag data,String content){
            this.data=data;
            this.content=content;
        }
        @Override
        protected Void doInBackground(Void... voids) {
            DbData task = new DbData();
            task.setRepoId(data.getId());
            task.setRepoComment(content);
            DatabaseClient.getInstance(getActivity()).getAppDatabase()
                    .taskDao()
                    .insert(task);
            binding.IdDetailEdtComment.getText().clear();
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            getCommentsFromDb();

        }
    }

    private void validateContent() {
        if (binding.IdDetailEdtComment.getText()!=null){
            String content=binding.IdDetailEdtComment.getText().toString();
            if (content.length()>0){
                //creating a task
                if (data!=null) {
                    new AsyncDb(data,content).execute();
                }
            }else {
                Toast.makeText(getActivity(),"Please enter any comment!",Toast.LENGTH_LONG).show();
            }
        }
    }
}
