package team.hdt.blockadia.game_engine.client.network;

import java.io.*;

public class PacketHandler implements Serializable {

    protected static final long serialVersionUID = 1112122200L;

    // The different types of message sent by the client
    public static final int LISTUSERS = 0;  // send a request for the list of connected users
    public static final int MESSAGE = 1;    // send a command or message
    public static final int LOGOUT = 2;     // disconnect from the server

    public int type;
    public String message;

    // constructor
    public PacketHandler(int type, String message) {
        this.type = type;
        this.message = message;
    }

    // getters
    public int getType() {
        return type;
    }
    public String getMessage() {
        return message;
    }
}
