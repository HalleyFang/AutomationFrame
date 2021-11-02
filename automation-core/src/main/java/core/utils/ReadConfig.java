package core.utils;

import core.cache.InitCache;

import java.util.ResourceBundle;

public class ReadConfig {

    public synchronized static ResourceBundle readConfig(String properties) {
        ResourceBundle resource = InitCache.cacheMap.get(properties);
        if (resource == null) {
            ReadProperties readProperties = new ReadProperties();
            resource = readProperties.readProperties(properties);
            InitCache.cacheMap.put(properties, resource);
        }
        return resource;
    }
}
