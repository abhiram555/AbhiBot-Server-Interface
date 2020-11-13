package com.abhiram.abhibot;

import com.abhiram.abhibot.api.BotManager;
import com.abhiram.abhibot.defaultlistener.PacketListener;
import com.abhiram.abhibot.module.Module;
import com.abhiram.abhibot.module.ModuleManager;
import com.abhiram.abhibot.task.ReconnectTask;
import com.github.steveice10.packetlib.Client;
import org.apache.log4j.Logger;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.Timer;

public class Main {
    private static Logger logger = org.apache.log4j.Logger.getLogger(Main.class.getName());

    public static void main(String[] args) throws Exception
    {
        logger.info("Starting AbhiBot Server....");
        Boolean running = true;
        ModuleManager moduleManager = new ModuleManager();
        Timer timer = new Timer();
        ReconnectTask reconnectTask = new ReconnectTask();
        timer.schedule(reconnectTask,2,2);
        registerdefaultListener();

        while (running)
        {
            BufferedReader read = new BufferedReader(new InputStreamReader(System.in));
            if(read.readLine().equals("stop"))
            {
                for(Module modules : moduleManager.getRegistryMap().values())
                {
                    modules.onDisable();
                    reconnectTask.cancel();
                    System.exit(-1);
                    running = false;
                }
            }
        }
    }

    private static void registerdefaultListener()
    {
        for(Client client : BotManager.getBotManager().getClients())
        {
            client.getSession().addListener(new PacketListener());
        }
    }
    public static Logger getLogger()
    {
        return logger;
    }
}
