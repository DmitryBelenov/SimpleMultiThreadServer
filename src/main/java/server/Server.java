package server;

import logger.Logger;
import java.io.IOException;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

/**
 * Сервер
 *
 * Author: Belenov.D
 * */

public class Server {

    public static Logger logger = new Logger();
    private int PORT = 9999;
    private List<Thread> threadList = new ArrayList<>();

    public void communication(){
        try {
            ServerSocket ss = new ServerSocket(PORT, 0,
                    InetAddress.getByName("0.0.0.0"));
            while (true) {
                Socket socket = ss.accept();
                Runnable connectionHandler = new ConnectionHandler(socket);
                Thread th = new Thread(connectionHandler);
                th.start();
                threadList.add(th);
                logger.log("Кол-во потоков: "+threadList.size());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        Server s = new Server();
        s.communication();
    }
}
