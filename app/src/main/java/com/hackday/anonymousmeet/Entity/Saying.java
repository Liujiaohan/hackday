package com.hackday.anonymousmeet.Entity;

/**
 * Created by Liu jiaohan on 2017-06-03.
 */

public class Saying {

    /*"content": "說說內容",
      "postTime": "發布時間",
      "likeAmount": 123,
      "comment1Content": "評論1內容",
      "comment1PostTime": "評論1發布時間",
      "comment2Content": "評論2內容",
      "comment2PostTime": "評論2發布時間",
      "comment3Content": "評論3內容",
      "comment3PostTime": "評論3發布時間"**/


    private String content;
    private String id;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    private String postTime;
    private String likeAmount;

    private String comment1Content;
    private String comment1PostTime;

    private String comment2Content;
    private String comment2PostTime;

    private String comment3Content;
    private String comment3PostTime;


    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getPostTime() {
        return postTime;
    }

    public void setPostTime(String postTime) {
        this.postTime = postTime;
    }

    public String getLikeAmount() {
        return likeAmount;
    }

    public void setLikeAmount(String likeAmount) {
        this.likeAmount = likeAmount;
    }

    public String getComment1Content() {
        return comment1Content;
    }

    public void setComment1Content(String comment1Content) {
        this.comment1Content = comment1Content;
    }

    public String getComment1PostTime() {
        return comment1PostTime;
    }

    public void setComment1PostTime(String comment1PostTime) {
        this.comment1PostTime = comment1PostTime;
    }

    public String getComment2Content() {
        return comment2Content;
    }

    public void setComment2Content(String comment2Content) {
        this.comment2Content = comment2Content;
    }

    public String getComment2PostTime() {
        return comment2PostTime;
    }

    public void setComment2PostTime(String comment2PostTime) {
        this.comment2PostTime = comment2PostTime;
    }

    public String getComment3Content() {
        return comment3Content;
    }

    public void setComment3Content(String comment3Content) {
        this.comment3Content = comment3Content;
    }

    public String getComment3PostTime() {
        return comment3PostTime;
    }

    public void setComment3PostTime(String comment3PostTime) {
        this.comment3PostTime = comment3PostTime;
    }
}
