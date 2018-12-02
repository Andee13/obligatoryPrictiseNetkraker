package com.vacik.andee;

import java.io.*;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Iterator;

public class TaskIO {
    public static void write(TaskList tasks, OutputStream out) throws IOException {
        DataOutputStream outputStream = new DataOutputStream(out);
        try {
            outputStream.writeInt(tasks.count);
            for (int i = 0; i < tasks.size(); i++) {
                Task task = tasks.getTask(i);
                outputStream.writeInt(task.getTitle().length());
                //outputStream.writeChars(tasks.getTask(i).getTitle());
                outputStream.writeUTF(tasks.getTask(i).getTitle());
                /*
                int s = task.getTitle().length();
                char chars[] = task.getTitle().toCharArray();
                for(int j = 0; j < s; j++) {
                    outputStream.writeChar(chars[j]);
                }*/
                outputStream.writeBoolean(task.isActive());
                outputStream.writeInt(task.getRepeatInterval());
                if (task.getRepeatInterval() != 0) {
                    outputStream.writeLong(task.getStartTime().getTime());
                    outputStream.writeLong(task.getEndTime().getTime());
                } else {
                    outputStream.writeLong(task.getStartTime().getTime());
                }
            }
        } finally {
            outputStream.close();
        }
    }
    public  static void read(TaskList tasks, InputStream in) throws IOException {
        DataInputStream inputStream =  new DataInputStream(in);
        try {
            int size = inputStream.readInt();
//            if(size == 0) {
//                inputStream.close();
//                return;
//            }
            for(int i = 0; i < size; i++) {
                int length = inputStream.readInt();
                //char name[] = new char[length];
                //String string = inputStream.readUTF();
                String name = inputStream.readUTF();
                /*for(int j = 0; j < length; j++){
                    name[j] = inputStream.readChar();
                }*/
                boolean active = inputStream.readBoolean();
                int interval = inputStream.readInt();
                Long start;
                Long end;
                Task task;
                if(interval != 0) {
                    start = inputStream.readLong();
                    end = inputStream.readLong();
                    task = new Task(name/*.toString()*/, new Date(start), new Date(end), interval);
                    task.setActive(active);
                } else {
                    start = inputStream.readLong();
                    task = new Task(name/*.toString()*/, new Date(start));
                    task.setActive(active);
                }
                tasks.add(task);
            }
        } finally {
            inputStream.close();
        }
    }
    public static void writeBinary(TaskList tasks, File file) throws IOException {
        RandomAccessFile randomAccessFile = new RandomAccessFile(file, "rw");
        try {
            randomAccessFile.write(tasks.size());
            for (int i = 0; i < tasks.size(); i++) {
                randomAccessFile.writeInt(tasks.getTask(i).getTitle().length());
                randomAccessFile.writeUTF(tasks.getTask(i).getTitle());
                randomAccessFile.writeBoolean(tasks.getTask(i).isActive());
                randomAccessFile.writeInt(tasks.getTask(i).getRepeatInterval());
                if (tasks.getTask(i).getRepeatInterval() != 0) {
                    randomAccessFile.writeLong(tasks.getTask(i).getStartTime().getTime());
                    randomAccessFile.writeLong(tasks.getTask(i).getEndTime().getTime());
                } else {
                    randomAccessFile.writeLong(tasks.getTask(i).getStartTime().getTime());
                }
            }
        } finally {
            randomAccessFile.close();
        }

    }
    public  static void readBinary(TaskList tasks, File file) throws IOException {
        RandomAccessFile randomAccessFile = new RandomAccessFile(file, "rw");
        try {
            int size = randomAccessFile.readInt();
            if(size == 0) return;
            for(int i = 0; i < size; i++) {
                int length = randomAccessFile.readInt();
                //char name[] = new char[length];
                String name = randomAccessFile.readUTF();
                //for(int j = 0; j < length; j++){
                  //  name[j] = randomAccessFile.readChar();
                //}
                boolean active = randomAccessFile.readBoolean();
                int interval = randomAccessFile.readInt();
                Long start;
                Long end;
                Task task;
                if(interval != 0) {
                    start = randomAccessFile.readLong();
                    end = randomAccessFile.readLong();
                    task = new Task(name/*.toString()*/, new Date(start), new Date(end), interval);
                    task.setActive(active);
                } else {
                    start = randomAccessFile.readLong();
                    task = new Task(name/*.toString()*/, new Date(start));
                    task.setActive(active);
                }
                tasks.add(task);
            }
        }
        finally {
            randomAccessFile.close();
        }
    }



    public  static  void write(TaskList tasks, Writer out) throws  IOException {
        BufferedWriter writer  = new BufferedWriter(out);
        try {
            for (Iterator<Task> l = tasks.iterator(); l.hasNext(); ) {
                Task temp = l.next();
                String title = temp.getTitle();
                Date start = temp.getStartTime();
                Date end = temp.getEndTime();
                int interval = temp.getRepeatInterval();
                boolean active = temp.isActive();

                StringBuilder s = new StringBuilder("\"");
                char chars[];
                chars = title.toCharArray();
                //Doubling "
                for (int i = 0; i < title.length(); i++) {
                    if (chars[i] == '"') {
                        s.append("\"\"");
                    } else {
                        s.append(chars[i]);
                    }
                }
                SimpleDateFormat simpleDateFormat = new SimpleDateFormat("[yyyy-MM-dd HH:mm:ss.SS]");
                if (interval == 0) {
                    s.append(" at ");
                    String t = simpleDateFormat.format(start);
                    s.append(t);
                }
                if (interval != 0) {
                    s.append(" from ");
                    String t = simpleDateFormat.format(start);
                    s.append(t);
                    s.append(" to ");
                    s.append(simpleDateFormat.format(end));
                    s.append(" every ");
                }
                StringBuilder intrvalbuilder = new StringBuilder();

                int days = interval / 86400;
                int hours = (interval / 3600) % 24;
                int minutes = (interval / 60) % 60;
                int seconds = interval % 60;
                if (seconds >= 1 || minutes >= 1 || hours >= 1 || days >= 1) {
                    intrvalbuilder.append("[");
                }
                if (days > 1) {
                    intrvalbuilder.append(days);
                    intrvalbuilder.append(" days ");
                }
                if (days == 1) {
                    intrvalbuilder.append(days);
                    intrvalbuilder.append(" day ");
                }
                if (hours > 1) {
                    intrvalbuilder.append(hours);
                    intrvalbuilder.append(" hours ");
                }
                if (hours == 1) {
                    intrvalbuilder.append(hours);
                    intrvalbuilder.append(" hour ");
                }
                if (minutes > 1) {
                    intrvalbuilder.append(minutes);
                    intrvalbuilder.append(" minutes ");
                }
                if (minutes == 1) {
                    intrvalbuilder.append(minutes);
                    intrvalbuilder.append(" minute ");
                }
                if (seconds > 1) {
                    intrvalbuilder.append(seconds);
                    intrvalbuilder.append(" seconds");
                }
                if (seconds == 1) {
                    intrvalbuilder.append(seconds);
                    intrvalbuilder.append(" second");
                }
                if (seconds >= 1 || minutes >= 1 || hours >= 1 || days >= 1) {
                    intrvalbuilder.append("]");
                }
                s.append(intrvalbuilder);
                if (!active) {
                    s.append(" inactive");
                }

                s.append("\"");
                if (l.hasNext()) {
                    s.append(";");
                } else {
                    s.append(".");
                }
                writer.write(s.toString());
                if (l.hasNext()) {
                    writer.newLine();
                }
            }
        }
        finally{
            writer.close();
        }
    }
    public static void read(TaskList tasks, Reader in) throws  IOException {
        BufferedReader bufferedReader = new BufferedReader(in);
        String readString =  bufferedReader.readLine();


    }

}
