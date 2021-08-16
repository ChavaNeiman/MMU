package main.java.com.company.util;
import java.beans.PropertyChangeSupport;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.util.Scanner;

public class CLI implements java.lang.Runnable {

    private final PropertyChangeSupport propertyChangeSupport = new PropertyChangeSupport(this);

    InputStream in;
    OutputStream out;
    Scanner reader;

    public CLI(InputStream in, OutputStream out) {
        reader = new Scanner (new InputStreamReader(in));
        this.in = in;
        this.out = out;
    }

    public void addPropertyChangeListener(java.beans.PropertyChangeListener pcl) {
        propertyChangeSupport.addPropertyChangeListener(pcl);
    }

    public void removePropertyChangeListener(java.beans.PropertyChangeListener pcl) {
        propertyChangeSupport.removePropertyChangeListener(pcl);
    }

    public void write(java.lang.String string) {
        System.out.println(string);
    }

    public void run() {
        try {
            write("Please enter your command");
            String command;
            do {
                command = reader.nextLine().toLowerCase();
                if (command.equals("start") || command.equals("shutdown")) {
                    propertyChangeSupport.firePropertyChange("command", null, command);
                } else {
                    write("Not a valid command");
                }
            } while (true);
        }catch (Exception ignored) { }
    }

}
