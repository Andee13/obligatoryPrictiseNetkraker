package com.vacik.andee;

import java.util.Iterator;
import java.util.NoSuchElementException;

public class LinkedTaskList extends TaskList implements Cloneable {
        private Node first;
        private Node last;
        private int count = 0;
        private class Node implements  Cloneable {
            private Task task;
            private Node next;

            public Node( Task task ) {
                this.task = task;
            }
            
            @Override
            public Node clone(){
            try{
                Node obj = (Node)super.clone(); 
                obj.task = task.clone();
                return obj;
            } catch(CloneNotSupportedException ex) {
            throw new InternalError();
            }
           }
        }

        public void add(Task task) {
                if(task == null){
                        throw new IllegalArgumentException("Task = null");
                    }
                if(first == null){
                        first = last = new Node(task);
                        count++;
                        return;
                }
                if(first != null) {
                        Node temp = new Node(task);
                        last.next = temp;
                        last = temp;
                        count++;
                }

        }

        public boolean remove(Task task){
                                Node tempFather = first;
                Node tempSon = first.next;
                if(tempSon == null && first.task == task){
                        first = last = null;
                        count--;
                        return true;
                }

                if(tempFather.task == task) {
                        first = tempSon;
                        count--;
                        return true;
                }
                while(tempSon != null) {
                        if(tempSon.task == task){
                            if(tempSon == last){
                                last = tempFather;
                                count--;
                                return true;
                            }
                                tempFather.next = tempSon.next;
                                count--;
                                return true;
                        }
                        tempFather = tempSon;
                        tempSon = tempFather.next;
                }
                return false;
        }

        public int size() {
                return count;
        }

        public Task getTask(int index) {
                Node temp = first;
                int counter = 0;
                while(temp != null) {
                        if(counter == index) {
                                return temp.task;
                        }
                        counter++;
                        temp = temp.next;
                }
                return null;
        }
  
    private class Iter implements Iterator<Task> {
        private Node node = new Node(first.task);
        private Node prev;
        boolean isNext = false;

        {
            node.next = first;
        }

        @Override
        public boolean hasNext() {
            return node.next != null;

        }

        @Override
        public Task next() {
            if(node == null){
                throw new NoSuchElementException("NoSuchElementException");
            }
            prev = node;
            node = node.next;
            isNext = true;
            return node.task;
        }

        @Override
        public void remove() {
            if(!isNext){
                throw new IllegalStateException("NoSuchElementException");
            }
            if(node == first){
              first = first.next;
              node.next = first;
              prev = null;
            }else{
                prev.next = node.next;
            }
            isNext = false;


        }
    }

    @Override
    public Iterator<Task> iterator() {
        return new Iter();
    }
    
    @Override
    public LinkedTaskList clone() {
        LinkedTaskList obj = (LinkedTaskList)super.clone();           
            if(obj.first == null){
                return obj;
            }
            
            Node temp = obj.first.clone();
           /* obj.first = obj.last = temp;
            */
            
            while(last.next != null) {
                last.next = last.next.clone();
                /*last = temp.next;*/
                last = last.next ;

            }
            /*if(first != null) {
                Node temp = new Node(task);
                last.next = temp;
                last = temp;
                
            }*/
    
        return obj;
    }

}


