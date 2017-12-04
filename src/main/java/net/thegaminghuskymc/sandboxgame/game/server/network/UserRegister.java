/**
 * *	This file is part of the project https://github.com/toss-dev/VoxelEngine
 * *
 * *	License is available here: https://raw.githubusercontent.com/toss-dev/VoxelEngine/master/LICENSE.md
 * *
 * *	PEREIRA Romain
 * *                                       4-----7
 * *                                      /|    /|
 * *                                     0-----3 |
 * *                                     | 5___|_6
 * *                                     |/    | /
 * *                                     1-----2
 */

package net.thegaminghuskymc.sandboxgame.game.server.network;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import net.thegaminghuskymc.sandboxgame.engine.managers.PacketManager;
import net.thegaminghuskymc.sandboxgame.engine.packets.NoSuchPacketException;
import net.thegaminghuskymc.sandboxgame.engine.packets.Packet;
import net.thegaminghuskymc.sandboxgame.engine.packets.WrongPacketFormatException;

import java.net.SocketAddress;
import java.util.HashMap;

public class UserRegister {
    private HashMap<SocketAddress, ClientData> connectedClients;
    private Thread[] workerThreads;
    private RunnableMessageHandler[] handlers;

    public UserRegister() {
        this.workerThreads = new Thread[ServerNetwork.MAX_NB_THREADS];
        this.handlers = new RunnableMessageHandler[ServerNetwork.MAX_NB_THREADS];
        for (int t = 0; t < ServerNetwork.MAX_NB_THREADS; t++) {
            this.handlers[t] = new RunnableMessageHandler();
            this.workerThreads[t] = new Thread(handlers[t]);
        }
        this.connectedClients = new HashMap<SocketAddress, ClientData>();
    }

    public void clean() {
        for (RunnableMessageHandler handler : this.handlers) {
            handler.stop();
        }

        for (Thread worker_thrd : this.workerThreads) {
            try {
                worker_thrd.wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public void onUserConnect(ChannelHandlerContext ctx) {
        this.connectedClients.putIfAbsent(ctx.channel().remoteAddress(), new ClientData(ctx));
    }

    public void onMessageReceived(PacketManager manager, ChannelHandlerContext ctx, ByteBuf byteBuffer) {
        try {
            Packet packet = manager.fromByteBuffer(byteBuffer);
            ClientData clt = this.connectedClients.get(ctx.channel().remoteAddress());

            /** Do whatever you want with Packet and Client :D **/
            RunnableMessageHandler lch = getLeastChargedHandler();
            lch.add(lch.new PacketClientDataWrapper(packet, clt));
            manager.onPacketReceived(packet);
        } catch (NoSuchPacketException e) {
            e.printStackTrace();
        } catch (WrongPacketFormatException e) {
            e.printStackTrace();
        }
    }

    private RunnableMessageHandler getLeastChargedHandler() {
        RunnableMessageHandler lch = this.handlers[0];
        for (int ite = 1; ite < ServerNetwork.MAX_NB_THREADS; ++ite) {
            if (this.handlers[ite].queueLength() < lch.queueLength()) {
                lch = this.handlers[ite];
            }
        }
        return lch;
    }

    public void onUserDisconnect(ChannelHandlerContext ctx) {
        SocketAddress key = ctx.channel().remoteAddress();

        if (this.connectedClients.containsKey(key)) {
            this.connectedClients.remove(key);
        }
    }
}
