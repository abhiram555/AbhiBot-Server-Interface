package com.abhiram.abhibot.module;


import com.abhiram.abhibot.Main;
import com.abhiram.abhibot.util.ModuleException;
import com.abhiram.abhibot.util.Util;

import java.io.*;
import java.util.HashMap;
import java.util.Map;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;

public class ModuleManager {
    private final Map<String, Class> classes = new HashMap<>();
    private final Map<String, ModuleClassLoader> classLoaders = new HashMap<>();
    private final HashMap<String,Module> registry_map = new HashMap<>();


    public ModuleManager()
    {
        try {
            loadModules();
        }catch (Exception e)
        {
            e.printStackTrace();
        }
    }

    private void loadModules() throws Exception {
        Main.getLogger().info("Loading Modules");
        File module_folder = new File("modules");

        if (!module_folder.exists()) {
            module_folder.mkdir();
        }

        for (File file : module_folder.listFiles()) {
            ModuleDescription description = getDescription(file);
            String className = description.getMain();
            ModuleClassLoader classLoader = new ModuleClassLoader(this, this.getClass().getClassLoader(), file);
            this.classLoaders.put(description.getName(), classLoader);

            Module module;

            try {
                Class javaClass = classLoader.loadClass(className);

                try {
                    Class<? extends Module> moduleclass = javaClass.asSubclass(Module.class);

                    module = moduleclass.newInstance();
                    module.onEnable();

                    registry_map.put(description.getName(),module);
                } catch (ClassCastException e) {
                    e.printStackTrace();
                    throw new ModuleException("main class `" + description.getMain() + "' does not extend Module");
                } catch (InstantiationException | IllegalAccessException e) {
                    e.printStackTrace();
                }

            } catch (ClassNotFoundException e) {
                throw new ModuleException("Couldn't load Module " + description.getName() + ": Main class not found");
            }
        }
    }

    private ModuleDescription getDescription(File file)
    {
        try (JarFile jar = new JarFile(file)) {
            JarEntry entry = jar.getJarEntry("module.yml");
            if (entry == null) {
                throw new ModuleException("module.yml not found in " + file.getName() + " Unable to load that.");
            }
            try (InputStream stream = jar.getInputStream(entry))
            {
                ModuleDescription description = new ModuleDescription(Util.readFile(stream));
                return description;
            }
        } catch (IOException e) {
            return null;
        }
    }

    public HashMap<String,Module> getRegistryMap()
    {
        return registry_map;
    }

    Class<?> getClassByName(final String name) {
        Class<?> cachedClass = classes.get(name);

        if (cachedClass != null) {
            return cachedClass;
        } else {
            for (ModuleClassLoader loader : this.classLoaders.values()) {

                try {
                    cachedClass = loader.findClass(name, false);
                } catch (ClassNotFoundException e) {
                    //ignore
                }
                if (cachedClass != null) {
                    return cachedClass;
                }
            }
        }
        return null;
    }

    void setClass(final String name, final Class<?> clazz) {
        if (!classes.containsKey(name)) {
            classes.put(name, clazz);
        }
    }
}
