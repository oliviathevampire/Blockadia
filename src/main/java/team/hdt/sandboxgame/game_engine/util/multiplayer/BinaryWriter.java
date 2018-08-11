package team.hdt.sandboxgame.game_engine.util.multiplayer;

import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.List;

public class BinaryWriter {
    private List<Byte> buffer;

    public BinaryWriter() {
        buffer = new ArrayList<Byte>();
    }

    public BinaryWriter(int size) {
        buffer = new ArrayList<Byte>(size);
    }

    public byte[] getBuffer() {
        Byte[] array = new Byte[buffer.size()];
        buffer.toArray(array);
        byte[] result = new byte[buffer.size()];
        for (int i = 0; i < result.length; i++)
            result[i] = array[i];
        return result;
    }

    public void Write(byte data) {
        buffer.add(data);
    }

    public void Write(byte[] data) {
        for (int i = 0; i < data.length; i++) {
            buffer.add(data[i]);
        }
    }

    public void Write(int data) {
        byte[] b = ByteBuffer.allocate(4).putInt(data).array();
        Write(b);
    }
}



