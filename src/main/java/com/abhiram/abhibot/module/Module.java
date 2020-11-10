package com.abhiram.abhibot.module;

import com.github.steveice10.packetlib.Client;

public class Module {
    protected Client client;

    public void onEnable()
    {

    }

    public void onDisable()
    {

    }

    public void SetClient(Client client)
    {
        this.client = client;
    }
}
