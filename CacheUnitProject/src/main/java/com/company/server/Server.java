package main.java.com.company.server;
import main.java.com.company.services.CacheUnitController;

import java.beans.PropertyChangeListener;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public class Server implements PropertyChangeListener, Runnable {

    private boolean isRunning = false;
    private ServerSocket server;

    public void propertyChange(java.beans.PropertyChangeEvent evt) {
        String command = evt.getNewValue().toString();
        switch (command)
        {
            case "start":
                if (!isRunning)
                {
                    isRunning =true;
                    new Thread(this).start();
                }
                else System.out.println("Server is already up\n");
                break;

            case "shutdown":
                if(!isRunning)
                    System.out.println("Server is already down\n");
                else
                {
                    try {
                        isRunning = false;
                        server.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    System.out.println("Shutdown server ");
                }
                break;
            default:
                System.out.println("Not a valid commnand");
                break;
        }
    }

    public void run()
    {
        try
        {
            server=new ServerSocket(12345);
            System.out.println("Starting server...");
            Executor ex = Executors.newFixedThreadPool(5);
            CacheUnitController<String> controller= new CacheUnitController<String>();
            while(isRunning) {
                Socket socket = server.accept();
                HandleRequest<String> RequesterHandler = new HandleRequest<String>(socket,controller);
                ex.execute(RequesterHandler);
            }
        }
        catch(SocketException ignored) { }
        catch (IOException e) {
            e.printStackTrace();
        }
        finally {
            try {
                if(server != null)
                    server.close();
            }catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}