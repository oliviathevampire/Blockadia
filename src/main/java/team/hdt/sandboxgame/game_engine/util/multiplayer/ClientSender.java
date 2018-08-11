package team.hdt.sandboxgame.game_engine.util.multiplayer;

public class ClientSender {
}
/*
import java.io.IOException;
import java.net.*;
import java.util.Random;


public class Client {

    private final static byte[] PACKET_HEADER = new byte[]{0x040, 0x040};

    private final static byte PACKET_TYPE_CONNECT = 0x01;
    private final static byte PACKET_TYPE_PLAYER = 0x02;

    private final static byte PACKET_TYPE_DISCONNECT = 0x04;
    private final static byte PACKET_TYPE_PLAYERREMOVE = 0x05;
    private Random r = new Random();
    private String ipAddress;
    private int port;
    private Error errorCode = Error.NONE;
    private InetAddress server;
    private DatagramSocket soc;

    public Client(String host) {
        String[] parts = host.split(":");
        if (parts.length != 2) {
            errorCode = Error.INVALED_HOST;
            return;
        }
        ipAddress = parts[0];
        try {
            port = Integer.parseInt(parts[1]);
        } catch (NumberFormatException e) {
            errorCode = Error.INVALED_HOST;
            e.printStackTrace();
            return;
        }
    }

    public Client(String host, int port) {
        this.ipAddress = host;
        this.port = port;
    }

    public boolean connect() {
        System.out.println("client start connet");
        try {
            server = InetAddress.getByName(ipAddress);
        } catch (UnknownHostException e) {
            e.printStackTrace();
            errorCode = Error.INVALED_HOST;
            return false;
        }
        try {
            soc = new DatagramSocket(r.nextInt(7776));
        } catch (SocketException e) {
            e.printStackTrace();
            errorCode = Error.SOCKET_EXCEPTION;
            return false;
        }
        sendConnectionPacket();
        return true;

    }

    public boolean Disconnect() {
        System.out.println("client start disconnet");
        try {
            server = InetAddress.getByName(ipAddress);
        } catch (UnknownHostException e) {
            e.printStackTrace();
            errorCode = Error.INVALED_HOST;
            return false;
        }
        try {
            soc = new DatagramSocket(r.nextInt(7776));
        } catch (SocketException e) {
            e.printStackTrace();
            errorCode = Error.SOCKET_EXCEPTION;
            return false;
        }
        sendDisconectPacket();
        return true;

    }

    private void sendDisconectPacket() {
        int i = 0;
        if (i == 0) {
            BinaryWriter writer = new BinaryWriter();
            writer.Write(PACKET_HEADER);
            writer.Write(PACKET_TYPE_DISCONNECT);
            send(writer.getBuffer());
            i += 1;
        }
        if (i == 1) {
            BinaryWriter writer = new BinaryWriter();
            writer.Write(PACKET_HEADER);
            writer.Write(PACKET_TYPE_PLAYERREMOVE);
            send(writer.getBuffer());
        }

    }

    private void sendConnectionPacket() {
        int i = 0;
        if (i == 0) {
            BinaryWriter writer = new BinaryWriter();
            writer.Write(PACKET_HEADER);
            writer.Write(PACKET_TYPE_CONNECT);
            send(writer.getBuffer());
            i += 1;
        }
        if (i == 1) {
            BinaryWriter writer = new BinaryWriter();
            writer.Write(PACKET_HEADER);
            writer.Write(PACKET_TYPE_PLAYER);
            send(writer.getBuffer());
        }

    }

    public void send(byte[] data) {
        assert (soc.isConnected());
        DatagramPacket p = new DatagramPacket(data, data.length, server, port);
        try {
            soc.send(p);
        } catch (IOException e) {

            e.printStackTrace();
        }


    }

    public Error getErrorCode() {
        return errorCode;
    }

    public enum Error {
        NONE, INVALED_HOST, SOCKET_EXCEPTION,
    }
}
 */