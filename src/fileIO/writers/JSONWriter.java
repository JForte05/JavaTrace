package fileIO.writers;

import java.io.File;
import java.io.FileWriter;

import utilities.json.JSONEntry;

public class JSONWriter {
    public static void write(JSONEntry entry, File writeTo){
        try (FileWriter f = new FileWriter(writeTo)){
            f.write(entry.toString());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}