package com.abhiram.abhibot.module;

import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class ModuleClassLoader extends URLClassLoader {
    private ModuleManager loader;
    private final Map<String, Class> classes = new HashMap<>();

    public ModuleClassLoader(ModuleManager module,ClassLoader parant, File file) throws MalformedURLException
    {
        super(new URL[]{file.toURI().toURL()},parant);
        this.loader = module;
    }

    protected Class<?> findClass(String name, boolean checkGlobal) throws ClassNotFoundException {
        Class<?> result = classes.get(name);

        if (result == null) {
            if (checkGlobal) {
                result = loader.getClassByName(name);
            }

            if (result == null) {
                result = super.findClass(name);

                if (result != null) {
                    loader.setClass(name, result);
                }
            }

            classes.put(name, result);
        }

        return result;
    }


    Set<String> getClasses() {
        return classes.keySet();
    }
}
