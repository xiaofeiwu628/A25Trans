package com.example.timemangertrans.Entienty;

public class NewRecordmine {
    private int intime_efficient;
    private int intime_inefficiency;
    private int ovwetime_efficient;
    private int overtime_inefficiency;
    private long lasttime;

    public int getNorecord() {
        return norecord;
    }

    public void setNorecord(int norecord) {
        this.norecord = norecord;
    }

    private int norecord;
    private String name;
    public NewRecordmine(){
        this.intime_efficient = 0;
        this.intime_inefficiency = 0;
        this.overtime_inefficiency = 0;
        this.ovwetime_efficient = 0;
        this.norecord = 0;
    }
    public int getIntime_efficient() {
        return intime_efficient;
    }

    public void setIntime_efficient(int intime_efficient) {
        this.intime_efficient = intime_efficient;
    }

    public int getIntime_inefficiency() {
        return intime_inefficiency;
    }

    public void setIntime_inefficiency(int intime_inefficiency) {
        this.intime_inefficiency = intime_inefficiency;
    }

    public int getOvwetime_efficient() {
        return ovwetime_efficient;
    }

    public void setOvwetime_efficient(int ovwetime_efficient) {
        this.ovwetime_efficient = ovwetime_efficient;
    }

    public int getOvertime_inefficiency() {
        return overtime_inefficiency;
    }

    public void setOvertime_inefficiency(int overtime_inefficiency) {
        this.overtime_inefficiency = overtime_inefficiency;
    }

    public long getLasttime() {
        return lasttime;
    }

    public void setLasttime(long lasttime) {
        this.lasttime = lasttime;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }






}
