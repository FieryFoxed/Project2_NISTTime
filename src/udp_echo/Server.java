package udp_echo;

import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.util.Arrays;

public class Server {
    public static void main(String[] args) throws Exception {
        DatagramSocket socket = new DatagramSocket(3000);
        DatagramPacket clientRequest = new DatagramPacket(new byte[1024], 1024);

        while (true){
            socket.receive(clientRequest);
            InetAddress clientIP = clientRequest.getAddress();
            int clientPort = clientRequest.getPort();
            byte[] payload = Arrays.copyOf(
                    clientRequest.getData(),
                    clientRequest.getLength()
            );
            String clientMessage = new String(payload);
            String replyMessage = clientMessage.toUpperCase();
            DatagramPacket reply = new DatagramPacket(
                    replyMessage.getBytes(),
                    replyMessage.getBytes().length,
                    clientIP,
                    clientPort
            );
            socket.send(reply);
        }
    }
}
