package com.abhiram.abhibot.command;

import com.github.steveice10.packetlib.event.session.PacketReceivedEvent;

public interface Command {
    public void onCommand(String message, PacketReceivedEvent event);
}
