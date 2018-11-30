package com.vacik.andee;

import java.util.Iterator;
import java.util.NoSuchElementException;


public class ArrayTaskList extends TaskList implements Cloneable {
        private  int Size = 20;
        private Task[] tasks = new Task[Size];
        private int count = 0;

        public void add(Task task) {
            if(task == null){
                    throw new IllegalArgumentException("Task = null");
            }
            if(count == Size){
                Task[] temp = new Task[tasks.length + 10];
                for(int i = 0; i < tasks.length; i++){
                    temp[i] = tasks[i];
                }
                tasks = temp;
            }
            tasks[count] = task;
            count++;
        }

        public boolean remove(Task task) {
            for(int i = 0; i < tasks.length; i++ ){
                if(tasks[i].equals(task)){//equals
                    tasks[i] = tasks[count - 1];
                    tasks[count - 1] = null;
                    count--;
                    return true;
                }
            }
            return false;
        }

        public int size() {
            return count;
        }

        public Task getTask(int index) {
            return tasks[index];
        }

//        public ArrayTaskList incoming(int from, int to) {
//            if(from > to){
//                throw new IllegalArgumentException("Unpropriate time");
//            }
//            ArrayTaskList tasks_in = new ArrayTaskList();
//            for( int i = 0; i < count; i++ ){
//                if ( getTask(i).isActive() ) {
//                    if( getTask(i).nextTimeAfter(from) > from && getTask(i).nextTimeAfter(from) <= to ){
//                        tasks_in.add(getTask(i));
//                    }
//                }
//            }
//            return tasks_in;
//        }
        
       private class Iter implements Iterator<Task> {
        int currentIndex = -1;
        boolean isNext = false;
        @Override
        public boolean hasNext() {
            return currentIndex + 1 < count;
        }

        @Override
        public Task next() {
            if(!hasNext()){
                throw new NoSuchElementException("NoSuchElementException43343454");
            }
            isNext = true;
            return tasks[++currentIndex];
        }

        @Override
        public void remove() {
            if(!isNext){
                throw new IllegalStateException("IllegalStateException");
            }
            if(count == 1){
                tasks[currentIndex] = null;
            } else {
               for(int i = currentIndex; i < count - 1 ; i++){
                    tasks[i] = tasks[i + 1];
               }
            }
            currentIndex--;
            isNext = false;
            count--;

        }
    }


    @Override
    public Iterator<Task> iterator() {
        return new Iter();
    }
    
    @Override
    public ArrayTaskList clone() {
        ArrayTaskList obj = (ArrayTaskList)super.clone();
        obj.tasks = tasks.clone();
        return obj;
    }
}
