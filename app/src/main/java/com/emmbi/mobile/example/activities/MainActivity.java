package com.emmbi.mobile.example.activities;

import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.android.volley.VolleyError;
import com.emmbi.mobile.example.R;
import com.emmbi.mobile.example.adapters.PostsArrayAdapter;
import com.emmbi.mobile.example.api.Api;
import com.emmbi.mobile.example.model.Comment;
import com.emmbi.mobile.example.model.Post;
import com.emmbi.mobile.spice.cache.CacheRequestCallback;
import com.emmbi.mobile.spice.cache.DefaultCacheFetcher;

import java.util.ArrayList;
import java.util.List;


public class MainActivity extends AppCompatActivity {

    private ListView mainList;
    private PostsArrayAdapter postsArrayAdapter;
    private List<Post> posts;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        posts = new ArrayList<Post>();
        mainList = (ListView) findViewById(R.id.main_listView);

        postsArrayAdapter = new PostsArrayAdapter(this, R.layout.post_list_view_item, posts);
        mainList.setAdapter(postsArrayAdapter);

        mainList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(MainActivity.this, CommentsActivity.class);
                intent.putExtra("postId", posts.get(position).getId());
                startActivity(intent);
            }
        });

        Api.getInstance().getPosts(new CacheRequestCallback<List<Post>>(new DefaultCacheFetcher<List<Post>>(Post.class), this) {

            @Override
            public void updateUI(List<Post> response, boolean fromCache) {
                super.updateUI(response, fromCache);

                posts.clear();
                posts.addAll(response);
                postsArrayAdapter.notifyDataSetChanged();
            }

            @Override
            public void onServerFailure(VolleyError error) {
                super.onServerFailure(error);

                Toast.makeText(MainActivity.this, "Uh oh!", Toast.LENGTH_LONG).show();
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
