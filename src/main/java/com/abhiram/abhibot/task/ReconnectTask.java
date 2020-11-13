package com.abhiram.abhibot.task;

import com.abhiram.abhibot.Main;
import com.abhiram.abhibot.api.BotManager;
import com.github.steveice10.packetlib.Client;

import java.util.TimerTask;

public class ReconnectTask extends TimerTask {


    @Override
    public void run() {
        for(Client client : BotManager.getBotManager().getClients())
        {
            if(!client.getSession().isConnected())
            {
                Main.getLogger().info("Reconnecting.....");
                client.getSession().connect();
            }
        }
    }
}
