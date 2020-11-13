package com.abhiram.abhibot.api;

import com.abhiram.abhibot.Main;
import com.github.steveice10.mc.protocol.MinecraftProtocol;
import com.github.steveice10.packetlib.Client;
import com.github.steveice10.packetlib.tcp.TcpSessionFactory;

public class BotBuilder {
    private Client client;

    public BotBuilder(String module_name,String server_ip,int port)
    {
        try {
            login(module_name,server_ip,port);
        }
        catch (Exception e) {
            Main.getLogger().info("Unable to login to Server Module " + module_name + " Has failed.");
        }

        BotManager.getBotManager().addClient(client);
    }

    private void login(String module_name,String server_ip,int port) throws Exception
    {
        Main.getLogger().info("[" + module_name + "]" + " Logging in to Mojang server!");
        MinecraftProtocol protocol = new MinecraftProtocol("abhithedev2019@gmail.com","abhiram444@A");

        Client client = new Client(server_ip,port,protocol,new TcpSessionFactory());

        this.client = client;

        client.getSession().connect();
    }

    public Client getClient()
    {
        return client;
    }
}
