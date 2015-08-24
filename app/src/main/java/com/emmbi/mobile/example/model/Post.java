package com.emmbi.mobile.example.model;

import com.orm.SugarRecord;

/**
 * Created by mbagliojr on 8/21/15.
 */
public class Post extends SugarRecord {

    private Long userId;
    private String title;
    private String body;

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }
}
