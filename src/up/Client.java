package up;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.Socket;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.Scanner;

public class Client {

    private Socket socket;
    private String host;
    private int port;

    public Client(String h, int p){
        host = h;
        port = p;
    }

    public  void connection(){
        try{
            InetAddress address = InetAddress.getByName(host);
            socket = new Socket(address, port);
            if(socket.isConnected()){
                System.out.println("Nawiązano połączenie z serverem");
            }
        } catch (IOException e){
            System.out.println(e.getMessage());
        }
    }

    public void sendMessage(){
        try {
            PrintWriter writer = new PrintWriter(socket.getOutputStream(),
                    true);
            BufferedReader reader = new BufferedReader(new InputStreamReader(
                    socket.getInputStream(), StandardCharsets.UTF_8
            ));
            String text, text2;
            Scanner scan = new Scanner(System.in);
            while(true){
                text = reader.readLine();
                System.out.println(text);
                text2 = scan.nextLine();
                if (text2.equals("e")){
                    writer.println(text2);
                    writer.close();
                    reader.close();
                    break;
                }
                writer.println(text2);
            }
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    public void sendMessageWithOption(){
        try {
            PrintWriter writer = new PrintWriter(socket.getOutputStream(),
                    true);
            BufferedReader reader = new BufferedReader(new InputStreamReader(
                    socket.getInputStream(), StandardCharsets.UTF_8
            ));
            String text, option;


        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }


    }

    public void disconnect(){
        if (!socket.isClosed()){
            try {
                socket.close();
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
    }
}
