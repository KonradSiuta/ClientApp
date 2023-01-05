package up.server;

import java.io.IOException;
import java.net.*;
import java.nio.charset.StandardCharsets;

public class UDPClient {
    private DatagramSocket socket;

    public UDPClient() {
        try {
            socket = new DatagramSocket();
        } catch (SocketException e) {
            e.printStackTrace();
        }
    }

    public void getMessage(String s) {
        byte[] buff = new byte[512];
        try {
            DatagramPacket packetIn = new DatagramPacket(buff, buff.length);
            DatagramPacket packetOut = new DatagramPacket(s.getBytes(), s.length(), InetAddress.getLocalHost(), 5501);
            socket.send(packetOut);
            socket.receive(packetIn);

            String message = new String(packetIn.getData(), 0, packetIn.getLength(), StandardCharsets.UTF_8);

            System.out.println(message);
        } catch (UnknownHostException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void close() {
        socket.close();
    }
}
