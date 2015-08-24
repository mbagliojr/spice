package com.emmbi.mobile.example.activities;

import android.app.Activity;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ListView;
import android.widget.Toast;

import com.android.volley.VolleyError;
import com.emmbi.mobile.example.R;
import com.emmbi.mobile.example.adapters.CommentsArrayAdapter;
import com.emmbi.mobile.example.adapters.PostsArrayAdapter;
import com.emmbi.mobile.example.api.Api;
import com.emmbi.mobile.example.model.Comment;
import com.emmbi.mobile.example.model.Post;
import com.emmbi.mobile.spice.cache.CacheFetcher;
import com.emmbi.mobile.spice.cache.CacheRequestCallback;
import com.emmbi.mobile.spice.cache.DefaultCacheFetcher;
import com.orm.SugarRecord;

import java.util.ArrayList;
import java.util.List;


public class CommentsActivity extends AppCompatActivity {

    private ListView mainList;
    private CommentsArrayAdapter commentsArrayAdapter;
    private List<Comment> comments;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final Long postId = getIntent().getLongExtra("postId", -1);
        if(postId.equals(-1)) {
            Toast.makeText(this, "Uh oh!", Toast.LENGTH_LONG).show();
            return;
        }

        comments = new ArrayList<Comment>();
        mainList = (ListView) findViewById(R.id.main_listView);

        commentsArrayAdapter = new CommentsArrayAdapter(this, R.layout.comment_list_view_item, comments);
        mainList.setAdapter(commentsArrayAdapter);

        CacheFetcher<List<Comment>> cacheFetcher = new CacheFetcher<List<Comment>>() {
            @Override
            public void fetchFromCache(CacheRequestCallback<List<Comment>> callback, Activity activity) {
                List<Comment> comments = SugarRecord.find(Comment.class, "POST_ID = ?", postId.toString());

                callback.updateUI(comments, true);

            }
        };

        Api.getInstance().getComments(postId,new CacheRequestCallback<List<Comment>>(cacheFetcher, this) {

            @Override
            public void updateUI(List<Comment> response, boolean fromCache) {
                super.updateUI(response, fromCache);

                comments.clear();
                comments.addAll(response);
                commentsArrayAdapter.notifyDataSetChanged();
            }

            @Override
            public void onServerFailure(VolleyError error) {
                super.onServerFailure(error);

                Toast.makeText(CommentsActivity.this, "Uh oh!", Toast.LENGTH_LONG).show();
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
