package com.vacik.andee;

import java.io.Serializable;
import java.util.Iterator;

public abstract class TaskList implements Iterable<Task>, Serializable {
    public abstract void add(Task task);
    public abstract boolean remove(Task task);
    public abstract Task getTask(int index);
    public abstract int size();
//    public TaskList incoming(int from, int to) {
//        if(from > to){
//            throw new IllegalArgumentException("Unpropriate time");
//        }
//        TaskList tasks_in = new LinkedTaskList();
//        for( int i = 0; i < size(); i++ ){
//            if ( getTask(i).isActive() ) {
//                if( getTask(i).nextTimeAfter(from) > from && getTask(i).nextTimeAfter(from) <= to ){
//                    tasks_in.add(getTask(i));
//                }
//            }
//        }
//        return tasks_in;
//    }
    
    
    @Override
    public boolean equals(Object obj) {
        if( this == obj){
            return true;
        }
        if(obj == null){
            return false;
        }
        if(getClass() != obj.getClass()) {
            return false;
        }

        
        TaskList temp = (TaskList)obj;

        if( size() != temp.size() ){
            return false;
        }

        Iterator<Task> iter = this.iterator();
        for(Iterator<Task> itertmp = temp.iterator(); iter.hasNext() && itertmp.hasNext();){
            if ( !iter.next().equals(itertmp.next()) ){
                return false;
            }
        }
        return true;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        for(Task task : this){
            hash +=task.hashCode();
        }
        return hash;
    }
    
    
    protected TaskList clone() {
        try {
            return (TaskList)super.clone();
        } catch(CloneNotSupportedException ex) {
            ex.printStackTrace();
            throw new InternalError();
        }
    }
}
