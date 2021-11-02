package core.cache;


import core.utils.ReadProperties;

import java.io.File;
import java.util.*;

public class InitCache {

    public static final Map<String, ResourceBundle> cacheMap = new HashMap<>();

    public static String basePath = System.getProperty("user.dir") + File.separator + "autodata";

    private static Set<String> fileSet = new HashSet<>();

    public static void initCache() {
        ReadProperties readProperties = new ReadProperties();
        Set<String> fileSet = ergodicFile(basePath);
        Iterator<String> it = fileSet.iterator();
        while (it.hasNext()) {
            String filePath = it.next();
            String name = filePath;
            if (filePath.startsWith(basePath)) {
                name = filePath.replace(basePath + File.separator, "").replace(File.separator, "/");
            }
            if (filePath.endsWith(".properties")) {
                cacheMap.put(name, readProperties.readProperties(new File(filePath)));
            }
        }
    }


    public synchronized static Set<String> ergodicFile(String path) {
        File file = new File(path);
        if (file.exists()) {
            File[] fs = file.listFiles();
            for (File f : fs) {
                if (!f.isDirectory()) {
                    fileSet.add(f.getPath());
                } else {
                    ergodicFile(f.getPath());
                }
            }
        }
        return fileSet;
    }

}
