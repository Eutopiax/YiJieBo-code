package com.example.rail.db;

public class order {
    private String date;
    private String startdestination;
    private String enddestinatioin;
    private String useTime;

    public order(String date, String startdestination, String enddestinatioin, String useTime) {
        super();
        this.date = date;
        this.startdestination = startdestination;
        this.enddestinatioin = enddestinatioin;
        this.useTime = useTime;
    }
    public void set_date(String date){
        this.date = date ;
    }
    public String get_date(){
        return this.date ;
    }
    public void set_startdestination(String startdestination){
        this.startdestination = startdestination ;
    }
    public String get_startdestination(){
        return this.startdestination ;
    }
    public void set_enddestinatioin( String enddestinatioin){
        this.enddestinatioin = enddestinatioin ;
    }
    public String get_enddestinatioin(){
        return this.enddestinatioin ;
    }
    public void set_useTime(String useTime){
        this.useTime = useTime ;
    }
    public String get_useTime(){
        return this.useTime ;
    }


}
