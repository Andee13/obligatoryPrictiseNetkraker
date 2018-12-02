package com.vacik.andee;

import java.io.Serializable;
import java.util.Date;
import java.util.Objects;

public class Task implements Cloneable, Serializable {
    private String title;
    private Date start;
    private Date end;
    private int interval;
    private boolean active;

    public Task(String title, Date time) {
        setTitle(title);
        setTime(time);
    }

    public Task(String title, Date start, Date end, int interval) {
        setTitle(title);
        setTime(start, end, interval);
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public Date getTime() {
        return start;
    }

    public void setTime(Date time) {
//            if(time < 0){
//                throw new IllegalArgumentException("Unpropriate time");
//            }
        this.start = this.end = time;
        this.interval = 0;
    }
    public Date getStartTime() {
        return start;
    }
    public Date getEndTime() {
        return end;
    }
    public int getRepeatInterval() {
        return interval;
    }

    public static void main(String[] args) {
        Date a = new Date();
        System.out.println(a);
    }
    public void setTime(Date start, Date end, int interval) {
//            if(start < 0){
//                throw new IllegalArgumentException("Unpropriate start");
//            }
//            if(interval <= 0){
//                throw new IllegalArgumentException("Unpropriate interval");
//            }
//            if(end <= start){
//                throw new IllegalArgumentException("end < start");
//            }
        this.start = start;
        this.end = end;
        this.interval = interval;
    }

    public boolean isRepeated() {
        return interval == 0 ? false : true;
    }




    public Date nextTimeAfter(Date current) {
//        if(current == null){
//            throw new IllegalArgumentException("current < 0");
//        }
        if (isActive()) {
            if ( isRepeated() ){
                Date temp = new Date(start.getTime());
                while(/*start1 <= end1*/temp.before(end) || temp.equals(end)){
                    if(current.before(temp)) {
                        return temp;
                    }
                    temp.setTime(temp.getTime() + (long)interval * 1000);
                }
            } else {
                if(current.before(start)) {
                    return start;
                }
            }
        }
        return null;
    }


//    @Override
//    public boolean equals(Object o) {
//        if (this == o) return true;
//        if (o == null || getClass() != o.getClass()) return false;
//        Task task = (Task) o;
//        return interval == task.interval &&
//                active == task.active &&
//                Objects.equals(title, task.title) &&
//                Objects.equals(start, task.start) &&
//                Objects.equals(end, task.end);
//    }
//
//    @Override
//    public int hashCode() {
//        int temp = 37;
//        temp += (start.hashCode() + end.hashCode() + interval + title.hashCode());
//        return temp;
//    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Task task = (Task) o;
        return interval == task.interval &&
                active == task.active &&
                Objects.equals(title, task.title) &&
                Objects.equals(start, task.start) &&
                Objects.equals(end, task.end);
    }

    @Override
    public int hashCode() {
        return Objects.hash(title, start, end, interval, active);
    }

    public Task clone() {
        try{
            Task obj = (Task)super.clone();
            obj.start = (Date)start.clone();
            obj.end = (Date)end.clone();
            return obj;
        } catch (CloneNotSupportedException ex) {
            throw new InternalError();
        }
    }

}
