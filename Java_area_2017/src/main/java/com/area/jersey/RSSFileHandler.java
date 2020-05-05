package com.area.jersey;

import java.io.*;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;
import java.util.List;

public class RSSFileHandler {

    public static boolean addLine(String line) throws IOException {
        Path p = Paths.get("./.FluxRss.txt");
        String s = System.lineSeparator() + line;

        checkFile();
        if (!Exist(line)) {
            if (!isEmpty()) {
                try {
                    Files.write(p, s.getBytes(), StandardOpenOption.APPEND);
                } catch (IOException e) {
                    System.err.println(e);
                }
            } else {
                if (line != null) {
                    Files.write(p, line.getBytes());
                }
            }
            return true;
        }
        return false;
    }

    public static boolean isEmpty() throws IOException {
        BufferedReader br = new BufferedReader(new FileReader("./.FluxRss.txt"));
        if (br.readLine() == null) {
            return true;
        }
        return false;
    }

    public static boolean Exist(String url) throws IOException {
        String line;
        BufferedReader reader = new BufferedReader(new FileReader("./.FluxRss.txt"));

        while ((line = reader.readLine()) != null) {
            if (line.equals(url)) {
                return true;
            }
        }
    return false;
    }

    public static boolean checkFile() {
        File file = new File("./.FluxRss.txt");

        if(file.exists() && !file.isDirectory()) {
            return true;
        }
        else {
            try {
                file.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return true;
        }
    }

    public static List<String> readFile(String filename) {
        List<String> records = new ArrayList<String>();
        try {
            BufferedReader reader = new BufferedReader(new FileReader(filename));
            String line;
            while ((line = reader.readLine()) != null) {
                records.add(line);
            }
            reader.close();
            return records;
        } catch (Exception e) {
            System.err.format("Exception occurred trying to read '%s'.", filename);
            e.printStackTrace();
            return null;
        }
    }
}
