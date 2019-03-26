package server;

import logger.Logger;
import mail.MailManager;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.*;

/**
 * Обработчик подключений к серверу
 * в отдельных потоках
 *
 * Author: Belenov.D
 * */

public class ConnectionHandler implements Runnable {

    public Logger logger = Server.logger;
    private Socket socket;
    private String reciever = "<reciever_mail_adress>"; // адрес почты получателя

    ConnectionHandler(Socket socket){
        this.socket = socket;
    }

    @Override
    public void run() {
        try {
            logger.log("Открыта новая сессия");
            while (true) {
                DataOutputStream oos = new DataOutputStream(socket.getOutputStream());
                DataInputStream dis = new DataInputStream(socket.getInputStream());
                String in = dis.readUTF();
                if (in.equals("стоп")){
                    oos.writeUTF("благодарю за общение, пока!");
                    oos.flush();
                    socket.close();

                    logger.log("Сессия закрыта");
                    break;
                } else
                    // отправка почты
                if (in.equals("почта")){
                    MailManager.send(reciever, "Hello)", "Hi from your mail program");
                    oos.writeUTF("почта отправлена");
                    oos.flush();
                } else {
                    Scanner sc = new Scanner(System.in);
                    String out = sc.nextLine();
                    oos.writeUTF(out);
                    oos.flush();
                }
            }
        } catch (IOException e) {
            logger.log("Сессия завершена не корректно");
            logger.log(e);
        }
    }
}
