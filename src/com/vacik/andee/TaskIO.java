package com.vacik.andee;

import java.io.*;
import java.util.Date;

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
           outputStream.close();
       } catch (IOException ex) {
           System.out.println(ex);
       }
    }

    static void read(TaskList tasks, InputStream in) {

        try {
            DataInputStream inputStream =  new DataInputStream(in);

            int size = inputStream.readInt();
            if(size == 0) return;
            for(int i = 0; i < size; i++) {
                int length = inputStream.readInt();
                char name[] = new char[length];
                for(int j = 0; j < length; j++){
                    name[j] = inputStream.readChar();
                }
                boolean active = inputStream.readBoolean();
                int interval = inputStream.readInt();
                Long start;
                Long end;
                Task task;
                if(interval != 0) {
                     start = inputStream.readLong();
                     end = inputStream.readLong();
                     task = new Task(name.toString(), new Date(start), new Date(end), interval);
                     task.setActive(active);
                } else {
                     start = inputStream.readLong();
                     task = new Task(name.toString(), new Date(start));
                     task.setActive(active);
                }
                tasks.add(task);
                inputStream.close();


//                inputStream.writeInt(tasks.getTask(i).getTitle().length());
//                inputStream.writeChars(tasks.getTask(i).getTitle());
//                inputStream.writeBoolean(tasks.getTask(i).isActive());
//                inputStream.writeInt(tasks.getTask(i).getRepeatInterval());
//                if(tasks.getTask(i).getRepeatInterval() != 0) {
//                    inputStream.writeLong(tasks.getTask(i).getStartTime().getTime());
//                    inputStream.writeLong(tasks.getTask(i).getEndTime().getTime());
//                } else {
//                    inputStream.writeLong(tasks.getTask(i).getStartTime().getTime());
//                }

            }


        } catch (IOException ex) {
            System.out.println(ex);
        }
    }
}
