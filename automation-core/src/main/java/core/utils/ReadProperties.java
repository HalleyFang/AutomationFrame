package core.utils;

import org.apache.log4j.Logger;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;

import java.io.*;
import java.util.PropertyResourceBundle;
import java.util.ResourceBundle;

public class ReadProperties {
    private ResourceBundle resource;
    private BufferedInputStream inputStream;
    private static Logger logger = Logger.getLogger(ReadProperties.class);

    public ResourceBundle readProperties(File file) {
        if (file.exists()) {
            try {
                inputStream = new BufferedInputStream(new FileInputStream(file.getPath()));
                resource = new PropertyResourceBundle(inputStream);
                inputStream.close();
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException ee) {
                ee.printStackTrace();
            } catch (Exception eee) {
                eee.printStackTrace();
            }
        } else {
            logger.error("can't find " + file.getPath());
        }
        return resource;
    }


    public ResourceBundle readProperties(String properties) {
        Resource resourceTmp = new ClassPathResource(properties);
        InputStream is = null;
        InputStreamReader isr = null;
        BufferedReader br = null;
        try {
            is = resourceTmp.getInputStream();
            isr = new InputStreamReader(is);
            resource = new PropertyResourceBundle(isr);
           /* br = new BufferedReader(isr);
            String data = null;
            while((data = br.readLine()) != null) {
                System.out.println(data);
            }*/
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
/*            try {
                br.close();
            } catch (IOException e) {
                e.printStackTrace();
            }*/
            try {
                isr.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
            try {
                is.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return resource;
    }

}
