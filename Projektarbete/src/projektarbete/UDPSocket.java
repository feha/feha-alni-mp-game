package projektarbete;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class UDPSocket {
    private static DatagramSocket socket = getSocket();
    private static final int serverPort = 25565;
    private static final int clientPort = 25565;

    public static Communication listen() {
        byte[] buffer = new byte[1024];
        DatagramPacket packet = new DatagramPacket(buffer, buffer.length);
        try {
            socket.receive(packet);
            return new Communication(packet.getAddress().getHostAddress(),
                    packet.getData());
        } catch (IOException ex) {
            return null;
        }
    }

    public static void send(Communication communication) {
        byte[] buffer = new byte[1024];
        try {
            DatagramPacket packet = new DatagramPacket(buffer, buffer.length,
                    InetAddress.getByName(communication.getAddress()),
                    getDestinationPort());
            packet.setData(communication.getData());
            socket.send(packet);
        } catch (UnknownHostException ex) {
        } catch (IOException ex) {
        }
    }

    public static void close() {
        socket.close();
    }

    private static DatagramSocket getSocket() {
        DatagramSocket datagramSocket = null;
        try {
             datagramSocket = new DatagramSocket(getPort());
             System.out.println("Socket opened");
        } catch (SocketException ex) {
        } finally {
            return datagramSocket;
        }
    }

    private static int getPort() {
        if (Stage.isServer()) {
            return serverPort;
        } else {
            return clientPort;
        }
    }

    private static int getDestinationPort() {
        if (Stage.isServer()) {
            return clientPort;
        } else {
            return serverPort;
        }
    }

}
