package com.example.timemangertrans.Action;

public class Kv {
    public Kv(int l, String n){
        this.LastTime = l;
        this.Name = n;
    }
    public int getLastTime() {
        return LastTime;
    }

    public void setLastTime(int lastTime) {
        LastTime = lastTime;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    private int LastTime;
    private String Name;
}
