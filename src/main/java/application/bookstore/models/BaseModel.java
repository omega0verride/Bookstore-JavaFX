package application.bookstore.models;

import application.bookstore.auxiliaries.CustomObjectOutputStream;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;

public abstract class BaseModel {
    public abstract String saveInFile();

    public abstract boolean updateFile();

    public String save(File dataFile) {
        if (!(isValid().matches("1")))
            return isValid();
        try {
            ObjectOutputStream outputStream;
            FileOutputStream fileOutputStream = new FileOutputStream(dataFile, true);
            if (dataFile.length() == 0)
                outputStream = new ObjectOutputStream(fileOutputStream);
            else
                outputStream = new CustomObjectOutputStream(fileOutputStream);
            outputStream.writeObject(this);
        } catch (IOException e) {
            // TODO: log
            e.printStackTrace();
            return "Could Not Save to File. Please check Logs.";
        }
        return "1";
    }

    public abstract String isValid();
    // return "1" if valid, else return a "message" describing the error

    public abstract boolean deleteFromFile();

}
