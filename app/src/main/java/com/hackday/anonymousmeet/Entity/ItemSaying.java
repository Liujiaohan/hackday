package com.hackday.anonymousmeet.Entity;

/**
 * Created by Liu jiaohan on 2017-06-04.
 */

public class ItemSaying {
    private String content;
    private String likeamount;

    public ItemSaying(String content, String likeamount) {
        this.content = content;
        this.likeamount = likeamount;
    }

    public String getContent() {
        return content;
    }

    public String getLikeamount() {
        return likeamount;
    }
}
