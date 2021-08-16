package main.java.com.company.client;

import java.io.*;
import java.net.Socket;
import java.util.Scanner;

public class CacheUnitClient extends java.lang.Object {

    private Socket  s;

    public CacheUnitClient()  {}

    public String send(java.lang.String request) throws ClassNotFoundException
    {
        String res = "Empty";
        try {
            s = new Socket("localhost", 12345);
            Scanner reader = new Scanner(new InputStreamReader(s.getInputStream()));
            PrintWriter writer = new PrintWriter(new OutputStreamWriter(s.getOutputStream()));
            writer.println(request);
            writer.flush();

            res = (String) reader.nextLine();
            writer.close();
            reader.close();
            s.close();
        }catch(IOException e) {
            e.printStackTrace();
        }
        return res;
    }
}