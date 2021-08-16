package main.java.com.company.server;

import java.io.*;
import java.net.Socket;
import java.util.Map;
import java.util.Scanner;

import com.google.gson.Gson;
import java.lang.reflect.Type;
import com.google.gson.reflect.TypeToken;
import main.java.com.company.dm.DataModel;
import main.java.com.company.services.CacheUnitController;


public class HandleRequest<T>extends java.lang.Object implements java.lang.Runnable {
    Socket socket;
    CacheUnitController<T> controller;
    Scanner reader;
    PrintWriter writer;
    String req, string;
    boolean resp =false;
    Map <String,String> headers;

    public HandleRequest(java.net.Socket socket, CacheUnitController<T> controller) {
        this.socket = socket;
        this.controller=controller;
    }

    public void run() {
        try {
            reader = new Scanner(new InputStreamReader(socket.getInputStream()));
            writer = new PrintWriter(new OutputStreamWriter(socket.getOutputStream()));
        }
        catch (IOException e) {
            e.printStackTrace();
        }
        req = reader.nextLine();
        System.out.println(req);
        Type ref = new TypeToken<Request<DataModel<T>[]>>(){}.getType();
        Request<DataModel<T>[]> request = new Gson().fromJson(req,ref);
        headers=request.getHeaders();
        switch (headers.get("action"))
        {
            case "UPDATE":
            {
                resp = controller.update(request.getBody());
                break;
            }

            case "DELETE":
            {
                resp = controller.delete(request.getBody());
                break;
            }
            case "GET":
            {
                try {
                    resp = controller.get(request.getBody()) != null;
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                }
                break;
            }
            case "STATS":
            {
                string = controller.getStats();
                break;
            }
            default:break;
        }
        if (!headers.get("action").equals("STATS"))
        {
            if (resp)
                string ="true";
            else string ="false";
        }
        writer.println(string);
        writer.flush();
    }
}