package com.vacik.andee;

import java.io.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Duration;
import java.util.Date;
import java.util.Iterator;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class TaskIO {
    public static void write(TaskList tasks, OutputStream out) throws IOException {
        DataOutputStream outputStream = new DataOutputStream(out);
        try {
            outputStream.writeInt(tasks.count);
            for (int i = 0; i < tasks.size(); i++) {
                Task task = tasks.getTask(i);
                outputStream.writeInt(task.getTitle().length());
                outputStream.writeUTF(tasks.getTask(i).getTitle());
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
            for(int i = 0; i < size; i++) {
                int length = inputStream.readInt();
                String name = inputStream.readUTF();

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
        BufferedOutputStream bOS = new BufferedOutputStream(new FileOutputStream(file));
        try {
            write(tasks,bOS);
        } finally {
            bOS.close();
        }

    }
    public  static void readBinary(TaskList tasks, File file) throws IOException {
        BufferedInputStream bIS = new BufferedInputStream( new FileInputStream(file));
        try {
            read(tasks, bIS);
        }
        finally {
            bIS.close();
        }
    }
/**
 * Bad  variant
 *
 * */


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
                s.append("\"");
                SimpleDateFormat simpleDateFormat = new SimpleDateFormat("[yyyy-MM-dd HH:mm:ss.SSS]");
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
                    String inervalPeriods[] = {" day", " hour", " minute", " second"};
                    long intervalsParse[] = new long[4];


                    Duration interval1 = Duration.ofSeconds(interval);
                    intervalsParse[0] = interval1.toDays();
                    interval1 = interval1.minusDays(intervalsParse[0]);
                    intervalsParse[1] = interval1.toHours();
                    interval1 = interval1.minusHours(intervalsParse[1]);
                    intervalsParse[2] = interval1.toMinutes();
                    interval1 = interval1.minusMinutes(intervalsParse[2]);
                    intervalsParse[3] = interval1.getSeconds();
                    StringBuilder intervalBuilder = new StringBuilder();
                    for (int i = 0; i < intervalsParse.length; i++) {
                        if (intervalsParse[i] > 0) {
                            intervalBuilder
                                    .append( intervalBuilder.length() > 0 ? " " : "" )
                                    .append(intervalsParse[i])
                                    .append(inervalPeriods[i]);
                            if (intervalsParse[i] > 1) intervalBuilder.append("s");
                        }
                    }
                    if (intervalBuilder.length() > 0 ) {
                        intervalBuilder.insert(0,"[").append("]");
                    }
//                    StringBuilder intrvalbuilder = new StringBuilder();
//
//                    int days = interval / 86400;
//                    int hours = (interval / 3600) % 24;
//                    int minutes = (interval / 60) % 60;
//                    int seconds = interval % 60;
//                    if (seconds >= 1 || minutes >= 1 || hours >= 1 || days >= 1) {
//                        intrvalbuilder.append("[");
//                    }
//                    if (days > 1) {
//                        intrvalbuilder.append(days);
//                        intrvalbuilder.append(" days ");
//                    }
//                    if (days == 1) {
//                        intrvalbuilder.append(days);
//                        intrvalbuilder.append(" day ");
//                    }
//                    if (hours > 1) {
//                        intrvalbuilder.append(hours);
//                        intrvalbuilder.append(" hours ");
//                    }
//                    if (hours == 1) {
//                        intrvalbuilder.append(hours);
//                        intrvalbuilder.append(" hour ");
//                    }
//                    if (minutes > 1) {
//                        intrvalbuilder.append(minutes);
//                        intrvalbuilder.append(" minutes ");
//                    }
//                    if (minutes == 1) {
//                        intrvalbuilder.append(minutes);
//                        intrvalbuilder.append(" minute ");
//                    }
//                    if (seconds > 1) {
//                        intrvalbuilder.append(seconds);
//                        intrvalbuilder.append(" seconds");
//                    }
//                    if (seconds == 1) {
//                        intrvalbuilder.append(seconds);
//                        intrvalbuilder.append(" second");
//                    }
//                    if (seconds >= 1 || minutes >= 1 || hours >= 1 || days >= 1) {
//                        intrvalbuilder.append("]");
//                    }
//                    s.append(intrvalbuilder);
                }

                    if (!active) {
                        s.append(" inactive");
                    }


                //s.append("\"");
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
    public static void read(TaskList tasks, Reader in) throws  IOException, ParseException {
        BufferedReader bufferedReader = new BufferedReader(in);
        String readString = bufferedReader.readLine();
        while (readString != null) {

            //Find title
            String pattern = "(\".*\")";
            Pattern p = Pattern.compile(pattern);
            Matcher m = p.matcher(readString);
            m.find();
            String title = m.group();
            String stringTOParse = readString.substring(title.length() + 1, readString.length() - 1);
            title = title.substring(1, title.length() - 1);
            pattern = "\"\"";
            p = Pattern.compile(pattern);
            m = p.matcher(title);
            title = m.replaceAll("\"");
            //Set active
            pattern = "inactive";
            p = Pattern.compile(pattern);
            m = p.matcher(stringTOParse);
            boolean active = false;
            active = m.find();
            //find date for unrepeated
            pattern = "at";
            p = Pattern.compile(pattern);
            m = p.matcher(stringTOParse);
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");
            if (m.find()) {
                Date d;
                pattern = "(\\d{4}-\\d{2}-\\d{2} \\d{2}:\\d{2}:\\d{2}.\\d{3})";
                p = Pattern.compile(pattern);
                m = p.matcher(stringTOParse);
                m.find();
                String a = m.group();
                d = simpleDateFormat.parse(a);


                Task task = new Task(title, d);
                task.setActive(active);
                tasks.add(task);
            } else {

                pattern = "(\\d{4}-\\d{2}-\\d{2} \\d{2}:\\d{2}:\\d{2}.\\d{3})";
                p = Pattern.compile(pattern);
                m = p.matcher(stringTOParse);
                m.find();
                String Start = m.group();
                Date dateStart = simpleDateFormat.parse(Start);
                //System.out.println(dateStart);
                m.find();
                String End = m.group();
                //System.out.println(End);
                Date dateEnd = simpleDateFormat.parse(End);
                int interval = 0;
                pattern = "(\\d*) day|days";
                p = Pattern.compile(pattern);
                m = p.matcher(stringTOParse);
                if (m.find()) {
                    interval = Integer.parseInt(m.group(1)) * 86400;
                    System.out.println(interval);
                }
                pattern = "(\\d*) hour|hours";
                p = Pattern.compile(pattern);
                m = p.matcher(stringTOParse);
                if (m.find()) {
                    interval = interval + Integer.parseInt(m.group(1)) * 3600;
                    System.out.println(interval);
                }
                pattern = "(\\d*) minute|minutes";
                p = Pattern.compile(pattern);
                m = p.matcher(stringTOParse);
                if (m.find()) {
                    interval = interval + Integer.parseInt(m.group(1)) * 60;
                    System.out.println(interval);
                }
                System.out.println(interval);
                pattern = "(\\d*) second|seconds";
                p = Pattern.compile(pattern);
                m = p.matcher(stringTOParse);
                if (m.find()) {
                    interval = interval + Integer.parseInt(m.group(1));
                    System.out.println(interval);
                }
                Task task = new Task(title, dateStart, dateEnd, interval);
                task.setActive(active);
                tasks.add(task);
            }
        }
        readString = bufferedReader.readLine();
    }

    public static  void writeText(TaskList tasks, File file) throws  IOException {
        BufferedWriter out = null;
        try {
            out = new BufferedWriter( new FileWriter(file));
            write(tasks, out);
        } finally {
            if(out != null)
            out.close();
        }
    }

    public static void readText(TaskList tasks, File file) throws  IOException  {
        BufferedReader in = null;
        try {
            in = new BufferedReader(new FileReader(file));
        } finally {
            if(in != null){
                in.close();
            }

        }
    }
}
