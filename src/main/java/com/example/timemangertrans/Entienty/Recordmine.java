package com.example.timemangertrans.Entienty;


import java.util.Date;

public class Recordmine {

    public Recordmine(){

    }
    private String rmaster;/*创建者*/
    private String rname;/*名字*/
    private String rtype;/*类型*/
    private Date rdate_start;/*开始时间*/
    private Date rdate_end;/*截止时间*/
    private String rremark;/*备注*/
    private String checked;/*checkbox*/
    private Date rdate_start_plan;/*计划开始时间*/
    private Date rdate_end_plan;/*计划结束时间*/
    private String rstatus;/*状态*/

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    private int id;
    public Date getRdate_start_plan() {
        return rdate_start_plan;
    }

    public void setRdate_start_plan(Date rdate_start_plan) {
        this.rdate_start_plan = rdate_start_plan;
    }

    public Date getRdate_end_plan() {
        return rdate_end_plan;
    }

    public void setRdate_end_plan(Date rdate_end_plan) {
        this.rdate_end_plan = rdate_end_plan;
    }

    public String getRstatus() {
        return rstatus;
    }

    public void setRstatus(String rstatus) {
        this.rstatus = rstatus;
    }



    public String getChecked() {
        return checked;
    }

    public void setChecked(String checked) {
        this.checked = checked;
    }


    public String getRmaster() {
        return rmaster;
    }

    public void setRmaster(String rmaster) {
        this.rmaster = rmaster;
    }

    public String getRname() {
        return rname;
    }

    public void setRname(String rname) {
        this.rname = rname;
    }

    public String getRtype() {
        return rtype;
    }

    public void setRtype(String rtype) {
        this.rtype = rtype;
    }

    public Date getRdate_start() {
        return rdate_start;
    }

    public void setRdate_start(Date rdate_start) {
        this.rdate_start = rdate_start;
    }

    public Date getRdate_end() {
        return rdate_end;
    }

    public void setRdate_end(Date rdate_end) {
        this.rdate_end = rdate_end;
    }

    public String getRremark() {
        return rremark;
    }

    public void setRremark(String rremark) {
        this.rremark = rremark;
    }




}
