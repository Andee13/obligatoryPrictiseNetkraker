package com.vacik.andee;

import java.io.*;

public class TaskIO {
    static void write(TaskList tasks, OutputStream out){
       try {
           DataOutputStream outputStream =  new DataOutputStream(out);
           outputStream.writeInt(tasks.size());
           for(int i = 0; i < tasks.size(); i++) {
                outputStream.writeInt(tasks.getTask(i).getTitle().length());
                outputStream.writeChars(tasks.getTask(i).getTitle());
                outputStream.writeBoolean(tasks.getTask(i).isActive());
                outputStream.writeInt(tasks.getTask(i).getRepeatInterval());
                if(tasks.getTask(i).getRepeatInterval() != 0) {
                    outputStream.writeLong(tasks.getTask(i).getStartTime().getTime());
                    outputStream.writeLong(tasks.getTask(i).getEndTime().getTime());
                } else {
                    outputStream.writeLong(tasks.getTask(i).getStartTime().getTime());
                }
           }

       } catch (IOException ex) {
           System.out.println(ex);
       }
    }
}
