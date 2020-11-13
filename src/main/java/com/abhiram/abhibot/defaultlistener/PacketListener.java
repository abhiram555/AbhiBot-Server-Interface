package com.abhiram.abhibot.defaultlistener;

import com.abhiram.abhibot.command.CommandHandler;
import com.github.steveice10.mc.protocol.data.game.ClientRequest;
import com.github.steveice10.mc.protocol.packet.ingame.client.ClientRequestPacket;
import com.github.steveice10.mc.protocol.packet.ingame.client.player.ClientPlayerPositionRotationPacket;
import com.github.steveice10.mc.protocol.packet.ingame.client.window.ClientConfirmTransactionPacket;
import com.github.steveice10.mc.protocol.packet.ingame.client.world.ClientTeleportConfirmPacket;
import com.github.steveice10.mc.protocol.packet.ingame.server.ServerChatPacket;
import com.github.steveice10.mc.protocol.packet.ingame.server.ServerRespawnPacket;
import com.github.steveice10.mc.protocol.packet.ingame.server.entity.player.ServerPlayerPositionRotationPacket;
import com.github.steveice10.mc.protocol.packet.ingame.server.window.ServerConfirmTransactionPacket;
import com.github.steveice10.packetlib.event.session.DisconnectedEvent;
import com.github.steveice10.packetlib.event.session.PacketReceivedEvent;
import com.github.steveice10.packetlib.event.session.SessionAdapter;


public class PacketListener extends SessionAdapter {

    @Override
    public void packetReceived(PacketReceivedEvent event) {
        if(event.getPacket() instanceof ServerRespawnPacket)
        {
            event.getSession().send(new ClientRequestPacket(ClientRequest.RESPAWN));
        }

        if(event.getPacket() instanceof ServerChatPacket)
        {
            ServerChatPacket chat = event.getPacket();

            String[] split = chat.getMessage().getFullText().split(" ");
            if(split.length > 1) {
                CommandHandler.getCommandHandler().call(split[1],event);
            }
        }

        if(event.getPacket() instanceof ServerConfirmTransactionPacket)
        {
            ServerConfirmTransactionPacket p = event.getPacket();
            event.getSession().send(new ClientConfirmTransactionPacket(p.getWindowId(),
                    p.getActionId(), p.getAccepted()));
        }

        if(event.getPacket() instanceof ServerPlayerPositionRotationPacket)
        {
            ServerPlayerPositionRotationPacket packet = event.getPacket();
            event.getSession().send(new ClientTeleportConfirmPacket(packet.getTeleportId()));
            event.getSession().send(new ClientPlayerPositionRotationPacket( true, packet.getX(), packet.getY(), packet.getZ(), packet.getYaw(), packet.getPitch()));
        }

    }

    @Override
    public void disconnected(DisconnectedEvent event) {
        System.out.println("Disconnected: " + event.getReason());
        if(event.getCause() != null) {
            event.getCause().printStackTrace();
        }
    }
}
