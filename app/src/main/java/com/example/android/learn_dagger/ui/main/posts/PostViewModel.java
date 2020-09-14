package com.example.android.learn_dagger.ui.main.posts;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.LiveDataReactiveStreams;
import androidx.lifecycle.MediatorLiveData;
import androidx.lifecycle.ViewModel;

import com.example.android.learn_dagger.BaseResource;
import com.example.android.learn_dagger.SessionManager;
import com.example.android.learn_dagger.models.Post;
import com.example.android.learn_dagger.network.main.MainApi;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import javax.inject.Inject;

import io.reactivex.rxjava3.core.Flowable;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class PostViewModel extends ViewModel {
    private static final String TAG = "PostViewModel";

    private final SessionManager sessionManager;

    private final MainApi mainApi;

    private MediatorLiveData<BaseResource<List<Post>>> posts;

    @Inject
    public PostViewModel(SessionManager sessionManager, MainApi mainApi) {
        this.sessionManager = sessionManager;
        this.mainApi = mainApi;
        Log.d(TAG, "PostViewModel: viewmodel is working");
    }

    public LiveData<BaseResource<List<Post>>> observePosts() {
        if (posts == null) {
            posts = new MediatorLiveData<>();
            posts.setValue(BaseResource.loading(null));

            // Retrieve posts
            final Flowable<List<Post>> request = mainApi.getPostsFromUser(
                    Objects.requireNonNull(Objects.requireNonNull(sessionManager.getAuthUser()
                                                                          .getValue()).data)
                            .getId());

            final LiveData<BaseResource<List<Post>>> source = LiveDataReactiveStreams.fromPublisher(
                    request.onErrorReturn(throwable -> {
                        Log.e(TAG, "observePosts: ", throwable);
                        Post post = new Post();
                        post.setId(-1);
                        ArrayList<Post> posts = new ArrayList<>();
                        posts.add(post);
                        return posts;
                    })
                            .map(posts -> {
                                     if (posts.size() > 0) {
                                         if (posts.get(0)
                                                 .getId() == -1) {
                                             return BaseResource.error("Something went wrong",
                                                                       (List<Post>) null);
                                         }
                                     }
                                     return BaseResource.success(posts);
                                 }
                            )
                            .subscribeOn(Schedulers.io())
            );

            posts.addSource(source, listBaseResource -> {
                posts.setValue(listBaseResource);
                posts.removeSource(source);
            });
        }
        return posts;
    }

}
