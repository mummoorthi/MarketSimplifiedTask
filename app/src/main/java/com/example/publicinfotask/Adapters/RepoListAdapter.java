package com.example.publicinfotask.Adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.publicinfotask.database.DbDatapag;
import com.example.publicinfotask.Interfaces.InterfaceDetailSection;
import com.example.publicinfotask.databinding.ListItemLoadingBinding;
import com.example.publicinfotask.databinding.ListItemRepoBinding;

import java.util.List;

public class RepoListAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    List<DbDatapag> listRepo;
    InterfaceDetailSection mInterface;

    public RepoListAdapter(List<DbDatapag> listRepo,InterfaceDetailSection mInterface){
        this.listRepo=listRepo;
        this.mInterface=mInterface;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if (viewType==1) {
            ListItemRepoBinding binding = ListItemRepoBinding.inflate(LayoutInflater
                    .from(parent.getContext()), parent, false);
            return new ViewHolder(binding);
        }else {
            ListItemLoadingBinding binding = ListItemLoadingBinding.inflate(LayoutInflater
                    .from(parent.getContext()), parent, false);
            return new ViewHolderLoading(binding);
        }

    }

    @Override
    public int getItemViewType(int position) {
        if (listRepo.get(position)==null){
            return 2;
        }
        return 1;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        if (holder.getItemViewType()==1){
            ((ViewHolder)holder).binding.setModel(listRepo.get(position));
        }
    }

    @Override
    public int getItemCount() {
        return listRepo.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private ListItemRepoBinding binding;
        public ViewHolder(@NonNull ListItemRepoBinding itemView) {
            super(itemView.getRoot());
            binding=itemView;
            binding.getRoot().setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            mInterface.onClick(getAdapterPosition());
        }
    }

    class ViewHolderLoading extends RecyclerView.ViewHolder {
        private ListItemLoadingBinding binding;
        public ViewHolderLoading(@NonNull ListItemLoadingBinding itemView) {
            super(itemView.getRoot());
            binding=itemView;
        }


    }
}
