package com.example.rail.ui.activity;

public class donation {
    private String sentence;
    private String count;
    private String name;
    private int imageId;

    public donation(String sentence,String count,String name,  int imageId) {
        this.sentence=sentence;
        this.count=count;
        this.name = name;
        this.imageId = imageId;
    }
    public String getSentence(){return sentence;}
    public String getCount(){return count;}
    public String getName() {
        return name;
    }

    public int getImageId() {
        return imageId;

    }


}
