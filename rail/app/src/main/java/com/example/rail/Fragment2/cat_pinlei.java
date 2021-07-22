package com.example.rail.Fragment2;

public class cat_pinlei {
    private String name;
    private String life;
    private String cha;
    private int imageId;

    public cat_pinlei(String name, String life,String cha,int imageId) {
        this.name = name;
        this.life = life;
        this.cha = cha;
        this.imageId = imageId;
    }

    public String getName() {
        return name;
    }
    public String getLife(){
        return life;
    }
    public String getChar(){
        return cha;
    }
    public int getImageId() {
        return imageId;

    }

}