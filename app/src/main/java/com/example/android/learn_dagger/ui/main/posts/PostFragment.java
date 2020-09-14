package com.example.android.learn_dagger.ui.main.posts;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.example.android.learn_dagger.BaseResource;
import com.example.android.learn_dagger.databinding.FragmentPostsBinding;
import com.example.android.learn_dagger.di.ViewModelProviderFactory;
import com.example.android.learn_dagger.models.Post;
import com.example.android.learn_dagger.utils.VerticalSpacingItemDecoration;

import java.util.List;

import javax.inject.Inject;

import dagger.android.support.DaggerFragment;

public class PostFragment extends DaggerFragment {

    private static final String TAG = "PostFragment";

    @Inject ViewModelProviderFactory factory;

    private FragmentPostsBinding binding;

    private PostViewModel viewModel;

    @Inject
    PostRecyclerAdapter postRecyclerAdapter;

    @Nullable
    @Override
    public View onCreateView(
            @NonNull LayoutInflater inflater, @Nullable ViewGroup container,
            @Nullable Bundle savedInstanceState
    ) {
        binding = FragmentPostsBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(
            @NonNull View view, @Nullable Bundle savedInstanceState
    ) {
        viewModel = new ViewModelProvider(this, factory).get(PostViewModel.class);
        initRecyclerView();
        subscribeObservers();
    }

    private void subscribeObservers() {
        viewModel.observePosts()
                .removeObservers(getViewLifecycleOwner());
        viewModel.observePosts()
                .observe(
                        getViewLifecycleOwner(),
                        new Observer<BaseResource<List<Post>>>() {
                            @Override
                            public void onChanged(
                                    BaseResource<List<Post>> listBaseResource
                            ) {
                                if (listBaseResource != null) {
                                    switch (listBaseResource.status) {
                                        case LOADING: {
                                            Log.d(TAG, "subscribeObservers: LOADING");
                                            break;
                                        }
                                        case SUCCESS: {
                                            postRecyclerAdapter.setPosts(listBaseResource.data);
                                            Log.d(TAG, "subscribeObservers: got posts");
                                            break;
                                        }
                                        case ERROR: {
                                            Log.e(
                                                    TAG, "error loading posts: " +
                                                            listBaseResource.message);
                                            break;
                                        }

                                    }
                                }

                            }
                        }
                );
    }

    private void initRecyclerView() {
        binding.postsList.setLayoutManager(new LinearLayoutManager(getActivity()));
        binding.postsList.addItemDecoration(new VerticalSpacingItemDecoration(8));
        binding.postsList.setAdapter(postRecyclerAdapter);
    }
}
