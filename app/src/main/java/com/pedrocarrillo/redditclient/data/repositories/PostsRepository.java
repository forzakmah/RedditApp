package com.pedrocarrillo.redditclient.data.repositories;

import android.support.annotation.NonNull;

import com.pedrocarrillo.redditclient.data.PostsDataSource;
import com.pedrocarrillo.redditclient.domain.RedditData;
import com.pedrocarrillo.redditclient.domain.RedditPostMetadata;

import rx.Observable;

/**
 * Created by pedrocarrillo on 5/3/17.
 */

public class PostsRepository implements PostsDataSource {

    private PostsDataSource mPostsRemoteDataSource;
    private PostsDataSource mPostsLocalDataSource;
    private boolean internetAccess;

    private static PostsRepository ourInstance;

    public static PostsRepository getInstance(PostsDataSource tasksRemoteDataSource, PostsDataSource mPostsLocalDataSource, boolean internetAccess) {
        if (ourInstance == null) {
            ourInstance = new PostsRepository(tasksRemoteDataSource, mPostsLocalDataSource, internetAccess);
        }
        return ourInstance;
    }

    private PostsRepository(@NonNull PostsDataSource postsRemoteDataSource, @NonNull PostsDataSource postsLocalDataSource, boolean internetAccess) {
        mPostsRemoteDataSource = postsRemoteDataSource;
        mPostsLocalDataSource = postsLocalDataSource;
        this.internetAccess = internetAccess;
    }

    @Override
    public Observable<RedditData> getPosts(String after) {
        if (internetAccess) {
            return mPostsRemoteDataSource.getPosts(after);
        } else {
            return mPostsLocalDataSource.getPosts(after);
        }
    }

    @Override
    public void setFavorite(RedditPostMetadata redditPostMetadata, boolean favorite) {
        mPostsLocalDataSource.setFavorite(redditPostMetadata, favorite);
    }

}
