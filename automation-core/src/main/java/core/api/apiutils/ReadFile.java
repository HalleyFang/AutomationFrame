package core.api.apiutils;

import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;

public class ReadFile {

/*    private String path = "";

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }*/


    public String readFile(String filepath) {
        Resource resource = new ClassPathResource(filepath);
        InputStream inputStream = null;
        String result = null;
        try {
//            inputStream = new FileInputStream(file);
            inputStream = resource.getInputStream();
            int size = inputStream.available();
            int readSize = 0;
            byte[] buf = new byte[size];
            while (readSize < size) {
                readSize += inputStream.read(buf, readSize, size - readSize);
            }
            inputStream.read(buf);
            result = new String(buf);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                inputStream.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return result;
    }

    /*public Map<String, File> ergodicFile(){
        Map<String,File> map = new HashMap<>();
        File file = new File(path);
        File[] fs = file.listFiles();
        for(File f:fs){
            if(!f.isDirectory()){
                if(f.getName().endsWith(".json")){
                    map.put(f.getAbsolutePath()+"/"+f.getName(),f);
                }
            }else {
                ergodicFile();
            }
        }
        return map;
    }*/


}
