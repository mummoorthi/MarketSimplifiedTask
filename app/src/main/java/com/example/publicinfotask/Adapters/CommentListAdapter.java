package com.example.publicinfotask.Adapters;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.publicinfotask.database.DbData;
import com.example.publicinfotask.Interfaces.InterfaceDetailSection;
import com.example.publicinfotask.databinding.ListItemCommentBinding;

import java.util.List;

public class CommentListAdapter extends RecyclerView.Adapter<CommentListAdapter.ViewHolder> {

    List<DbData> listComments;
    InterfaceDetailSection mInterface;
    public CommentListAdapter(List<DbData> listComments){
        this.listComments=listComments;
    }

    @NonNull
    @Override
    public CommentListAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ListItemCommentBinding binding = ListItemCommentBinding.inflate(LayoutInflater
                .from(parent.getContext()), parent, false);
        return new CommentListAdapter.ViewHolder(binding);

    }

    @Override
    public void onBindViewHolder(@NonNull CommentListAdapter.ViewHolder holder, int position) {
        holder.binding.IdListItemTxtComment.setText(listComments.get(position).getRepoComment());
    }

    @Override
    public int getItemCount() {
        return listComments.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder{
        private ListItemCommentBinding binding;
        public ViewHolder(@NonNull ListItemCommentBinding itemView) {
            super(itemView.getRoot());
            binding=itemView;
        }
    }
}
