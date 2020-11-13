package com.abhiram.abhibot.command;

import com.github.steveice10.packetlib.event.session.PacketReceivedEvent;

import java.util.ArrayList;

public class CommandHandler {
    private ArrayList<Command> registry_map = new ArrayList<>();
    private static CommandHandler commandHandler;

    public void Register(Command command)
    {
        registry_map.add(command);
    }

    public void call(String message, PacketReceivedEvent event)
    {
        for(Command commands : registry_map)
        {
            commands.onCommand(message,event);
        }
    }

    public static CommandHandler getCommandHandler()
    {
        if(commandHandler == null)
        {
            commandHandler = new CommandHandler();
            return commandHandler;
        }

        return commandHandler;
    }
}
