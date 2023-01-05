package up;

import java.io.*;
import java.net.InetAddress;
import java.net.Socket;
import java.nio.charset.StandardCharsets;
import java.nio.file.Path;

public class FileClient {

    private Socket socket;
    private DataInputStream dataInputStream;
    private DataOutputStream dataOutputStream;
    private String defaultLocation;
    private String host;
    private int port;

    public FileClient(String h, int p, String l){
        this.host = h;
        this.port = p;
        this.defaultLocation = l;
    }

    public void connect(){
        try {
            InetAddress address = InetAddress.getByName(host);
            this.socket = new Socket(address, this.port);
            if (!socket.isClosed()){
                this.dataOutputStream =
                        new DataOutputStream(socket.getOutputStream());
                this.dataInputStream = new DataInputStream(socket.getInputStream());
                System.out.println("Udało się nawiązać połączenie ");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void getFileFromServer(){
        try {
            int fileNameLength = dataInputStream.readInt();
            if (fileNameLength > 0){
                byte[] fileNameBytes = new byte[fileNameLength];
                dataInputStream.readFully(fileNameBytes);
                String fileName = new String(fileNameBytes);
                long fileContentLength = dataInputStream.readLong();
                if(fileContentLength > 0){
                    byte[] fileContentBytes = new byte[(int) fileContentLength];
                    dataInputStream.readFully(fileContentBytes);
                    FileOutputStream fileOut =
                            new FileOutputStream(defaultLocation + fileName);
                    fileOut.write(fileContentBytes);
                    fileOut.flush();
                    fileOut.close();
                    System.out.println("Zapisano przekazany plik " + fileName);
                }else{
                    System.out.println("Plik pusty nie tworzę go");
                }
            }else{
                System.out.println("nie przekazano plku");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void sendFileToServer(){
        File file = new File(defaultLocation + "Turbine_Blower_for_540-sized_Motors.zip");
        // pobranie nazwy i konwersja na tablice najtów
        System.out.println("przygotowywanie pliku do wysłania");
        int fileNameLength = file.getName().length();
        byte[] fileNameBytes = file.getName().getBytes(StandardCharsets.UTF_8);
        byte[] fileContentBytes = new byte[(int) file.length()];
        try {
            FileInputStream fileIn = new FileInputStream(file);
            fileIn.read(fileContentBytes);
            fileIn.close();
            // wysłanie nazwy pliku
            dataOutputStream.writeInt(fileNameLength);
            dataOutputStream.write(fileNameBytes);
            // wysyłanie zawartości pliku
            dataOutputStream.writeLong(file.length());
            dataOutputStream.write(fileContentBytes);
            dataOutputStream.flush();
            System.out.println("Wysłano plik");
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void disconnect(){
        if (!socket.isClosed()){
            try {
                dataOutputStream.close();
                dataInputStream.close();
                socket.close();
                System.out.println("Połączenie zakończone ");
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
