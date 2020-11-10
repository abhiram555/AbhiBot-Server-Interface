package com.abhiram.abhibot.module;

import com.abhiram.abhibot.util.ModuleException;
import org.yaml.snakeyaml.DumperOptions;
import org.yaml.snakeyaml.Yaml;

import java.util.LinkedHashMap;
import java.util.Map;

public class ModuleDescription {
    private String name;
    private String main;
    private String version;

    public ModuleDescription(String yamlString) {
        DumperOptions dumperOptions = new DumperOptions();
        dumperOptions.setDefaultFlowStyle(DumperOptions.FlowStyle.BLOCK);
        Yaml yaml = new Yaml(dumperOptions);
        this.loadMap(yaml.loadAs(yamlString, LinkedHashMap.class));
    }

    private void loadMap(Map<String, Object> plugin){
        this.name = ((String) plugin.get("name")).replaceAll("[^A-Za-z0-9 _.-]", "");

        if(this.name.equals(""))
        {
            throw new ModuleException("Invalid PluginDescription name");
        }
        this.name = this.name.replace(" ", "_");
        this.version = String.valueOf(plugin.get("version"));
        this.main = (String) plugin.get("main");
    }

    public String getName()
    {
        return name;
    }

    public String getMain()
    {
        return main;
    }

    public String getVersion()
    {
        return version;
    }
}
