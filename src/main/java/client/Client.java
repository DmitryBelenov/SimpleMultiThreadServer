package client;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.Scanner;

/**
 * Клиент
 *
 * Author: Belenov.D
 * */

public class Client {

    public void clientStart () {
        final String REMOTE_HOST = "localhost";
        final int PORT = 9999;
        try {
            Socket socket = new Socket(REMOTE_HOST, PORT);
            while (true) {
                DataOutputStream oos = new DataOutputStream(socket.getOutputStream());
                DataInputStream dis = new DataInputStream(socket.getInputStream());

                Scanner sc = new Scanner(System.in);
                String my = sc.nextLine();
                oos.writeUTF(my);
                oos.flush();

                System.out.println(dis.readUTF());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        Client cl = new Client();
        cl.clientStart();
    }
}
