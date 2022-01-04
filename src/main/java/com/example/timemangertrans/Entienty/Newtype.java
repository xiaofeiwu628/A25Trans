package com.example.timemangertrans.Entienty;

public class Newtype implements Comparable<Newtype>{
    public Newtype(){}

    String fatype;
    String sotype;
    String totalname;
    String showname;
    int cnt;
    boolean extneded;

    public String getFatype() {
        return fatype;
    }

    public void setFatype(String fatype) {
        this.fatype = fatype;
    }

    public String getSotype() {
        return sotype;
    }

    public void setSotype(String sotype) {
        this.sotype = sotype;
    }

    public String getTotalname() {
        return totalname;
    }

    public void setTotalname(String totalname) {
        this.totalname = totalname;
    }

    public String getShowname() {
        return showname;
    }

    public void setShowname(String showname) {
        this.showname = showname;
    }

    public int getCnt() {
        return cnt;
    }

    public void setCnt(int cnt) {
        this.cnt = cnt;
    }

    @Override
    public int compareTo(Newtype o) {
        return this.totalname.compareTo(o.getTotalname());
    }

    public boolean isExtneded() {
        return extneded;
    }

    public void setExtneded(boolean extneded) {
        this.extneded = extneded;
    }
}
