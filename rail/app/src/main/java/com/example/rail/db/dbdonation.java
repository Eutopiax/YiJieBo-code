package com.example.rail.db;
import java.io.Serializable;


public class dbdonation implements Serializable{
    private int id;
    private String username;
    private String password;
    private int age;
    private String sex;
    private int userid;
    private int maoliang;
    private int maobohe;
    private int maosha;
    private int maoguantou;

    public dbdonation() {
        super();
        // TODO Auto-generated constructor stub
    }
    public void dbdonate(int userid, int maoliang, int maobohe, int maosha, int maoguantou) {

        this.userid = userid;
        this.maoliang = maoliang;
        this.maobohe = maobohe;
        this.maosha = maosha;
        this.maoguantou = maoguantou;


    }
    public int getUserid() {
        return userid;
    }
    public void setUserid(int userid) {
        this.userid = userid;
    }
    public int getMaoliang() {
        return maoliang;
    }
    public void setMaoliang(int maoliang) {
        this.maoliang = maoliang;
    }
    public int getMaobohe() {
        return maobohe;
    }
    public void setMaobohe(int maobohe) {
        this.maobohe = maobohe;
    }
    public int getMaosha() {
        return maosha;
    }
    public void setMaosha(int maosha) {
        this.maosha = maosha;
    }
    public int getMaoguantou() {
        return maoguantou;
    }
    public void setMaoguantou(int maoguantou) {
        this.maoguantou = maoguantou;
    }

    public String toString() {
        return "dbdonation [userid=" + userid + ", maoliang=" + maoliang + ", maobohe="
                + maobohe + ", maosha=" + maosha + ", maoguantou=" + maoguantou + "]";
    }
}
