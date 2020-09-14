package com.example.android.learn_dagger.ui.main.posts;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.example.android.learn_dagger.R;
import com.example.android.learn_dagger.databinding.PostListItemBinding;
import com.example.android.learn_dagger.models.Post;

import java.util.ArrayList;
import java.util.List;

public class PostRecyclerAdapter extends RecyclerView.Adapter {
    private static final String TAG = "PostRecyclerAdapter";
    private List<Post> posts = new ArrayList<>();

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(
            @NonNull ViewGroup parent, int viewType
    ) {
        Log.d(TAG, "onCreateViewHolder: view created");
        return new PostViewHolder(DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()),
                                                          R.layout.post_list_item, parent, false
        ));
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        // Binds the entire layout to this viewholder
        // Setting of variables done in layout
        ((PostViewHolder) holder).binding.setPost(posts.get(position));
        Log.d(TAG, "onBindViewHolder: " + posts.get(position).getTitle());
    }

    @Override
    public int getItemCount() {
        return posts.size();
    }

    public void setPosts(List<Post> posts) {
        this.posts = posts;
        notifyDataSetChanged();
    }

    public class PostViewHolder extends RecyclerView.ViewHolder {
        private PostListItemBinding binding;

        public PostViewHolder(@NonNull PostListItemBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }
}
