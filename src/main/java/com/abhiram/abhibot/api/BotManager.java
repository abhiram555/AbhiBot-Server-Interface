package com.abhiram.abhibot.api;

import com.github.steveice10.packetlib.Client;
import com.github.steveice10.packetlib.event.session.PacketReceivedEvent;

import java.util.ArrayList;

public class BotManager {
    private ArrayList<Client> clients = new ArrayList<>();
    private static BotManager botManager;

    public void addClient(Client client)
    {
        clients.add(client);
    }

    public ArrayList<Client> getClients()
    {
        return clients;
    }

    public static BotManager getBotManager()
    {
        if(botManager == null)
        {
            botManager = new BotManager();
            return botManager;
        }

        return botManager;
    }
}
