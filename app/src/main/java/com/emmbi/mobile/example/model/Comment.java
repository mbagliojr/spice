package com.emmbi.mobile.example.model;

import com.google.gson.annotations.SerializedName;
import com.orm.SugarRecord;
import com.orm.dsl.Ignore;

/**
 * Created by mbagliojr on 8/21/15.
 */
public class Comment extends SugarRecord {

    //Showing as an example
    @SerializedName(value = "postId")
    private Long postId;

    private String name;
    private String email;
    private String body;

    @Ignore
    private String someTransientField;

    public Long getPostId() {
        return postId;
    }

    public void setPostId(Long postId) {
        this.postId = postId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public String getSomeTransientField() {
        return someTransientField;
    }

    public void setSomeTransientField(String someTransientField) {
        this.someTransientField = someTransientField;
    }
}
