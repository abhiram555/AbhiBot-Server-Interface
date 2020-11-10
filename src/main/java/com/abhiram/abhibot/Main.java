package com.abhiram.abhibot;

import com.abhiram.abhibot.module.ModuleManager;

public class Main {

    public static void main(String[] args)
    {
        System.out.println("Running modules...");

        while (true)
        {
            new ModuleManager();
        }
    }
}
