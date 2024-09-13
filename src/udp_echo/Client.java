package udp_echo;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.nio.ByteBuffer;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Arrays;

public class Client {
    public static void main(String[] args) throws IOException {
        if (args.length !=2){
            System.out.println("Please specify <ServerIP> and <serverPort>");
            return;
        }

        InetAddress serverIP = InetAddress.getByName(args[0]);
        int serverPort = Integer.parseInt(args[1]);

        String message = " ";

        DatagramSocket socket = new DatagramSocket();
        DatagramPacket request = new DatagramPacket(
                message.getBytes(),
                message.getBytes().length,
                serverIP,
                serverPort
        );
        socket.send(request);

        DatagramPacket reply = new DatagramPacket(
                new byte[1024],
                1024
        );
        socket.receive(reply);
        System.out.println(reply);
        socket.close();

        byte[] serverMessage = Arrays.copyOf(
                reply.getData(),
                reply.getLength()
        );

        System.out.println("Original message: " + Arrays.toString(serverMessage));

        int value = ByteBuffer.wrap(serverMessage).getInt();
        long unsignedValue = Integer.toUnsignedLong(value);

        System.out.println("Unsigned integer: " + unsignedValue);

        Instant date = Instant.ofEpochSecond(unsignedValue);
        LocalDateTime dateTime = LocalDateTime.ofInstant(date, ZoneId.systemDefault());

        System.out.println("Date and time: " + dateTime);

    }
}
