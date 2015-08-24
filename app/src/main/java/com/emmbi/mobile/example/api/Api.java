package com.emmbi.mobile.example.api;

import com.android.volley.Request;
import com.emmbi.mobile.example.model.Comment;
import com.emmbi.mobile.example.model.Post;
import com.emmbi.mobile.spice.BaseApi;
import com.emmbi.mobile.spice.cache.CacheRequestCallback;
import com.emmbi.mobile.spice.json.CustomJsonRequest;
import com.emmbi.mobile.spice.json.JsonParsingStrategy;
import com.emmbi.mobile.spice.json.JsonRequestBuilder;
import com.emmbi.mobile.spice.json.RequestCallback;
import com.google.gson.reflect.TypeToken;

import java.util.List;

/**
 * Created by mbagliojr on 8/21/15.
 */
public class Api extends BaseApi {

    /***************
     // singleton
     ****************/
    private static Api instance = null;
    private Api() { }
    public static synchronized Api getInstance() {
        if (instance == null) {
            instance = new Api();
        }
        return instance;
    }

    //Cache the Posts
    public void getPosts(CacheRequestCallback<List<Post>> callback) {

        CustomJsonRequest req = new JsonRequestBuilder().setRequestMethod(Request.Method.GET)
                .setUrl("http://jsonplaceholder.typicode.com/posts")
                .setClazz(List.class)
                .setType(new TypeToken<List<Post>>() {}.getType())
                //Example
                //.setHeaders(getDefaultHeaders())
                .setSuccessListener(callback.getSuccessListener())
                .setErrorListener(callback.getErrorListener())
                .build();

        addToRequestQueue(req, callback);

    }

    //To not cache the response, use RequestCallback rather than CacheRequestCallback as seen below.
    //public void getComments(Integer postId, RequestCallback<List<Comment>> callback) {

    public void getComments(Long postId, CacheRequestCallback<List<Comment>> callback) {

        CustomJsonRequest req = new JsonRequestBuilder().setRequestMethod(Request.Method.GET)
                .setUrl(String.format("http://jsonplaceholder.typicode.com/posts/%s/comments", postId.toString()))
                .setJsonParsingStrategy(new JsonParsingStrategy<List<Comment>>() {
                    @Override
                    public List<Comment> parseJson(String json) {

                        List<Comment> comments = getGsonInstance().fromJson(json, new TypeToken<List<Comment>>() {
                        }.getType());

                        return comments;
                    }
                })
                //Example
                //.setHeaders({SOME HEADER MAP})
                .setSuccessListener(callback.getSuccessListener())
                .setErrorListener(callback.getErrorListener())
                .build();

        addToRequestQueue(req, callback);

    }


}
