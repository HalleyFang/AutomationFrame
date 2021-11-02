package core.ui.utils;

import net.sourceforge.tess4j.ITesseract;
import net.sourceforge.tess4j.Tesseract;
import net.sourceforge.tess4j.TesseractException;

import java.io.File;

public class GetCaptcha {

    public String getCaptcha(String pName) {
        File imageFile = new File("captchaimage" + File.separator + pName + ".png");
        if (!imageFile.exists()) {
            return null;
        }
        ITesseract instance = new Tesseract();
        instance.setDatapath("src/main/resources/tessdata");
        instance.setLanguage("eng");
        instance.setTessVariable("tessedit_char_whitelist", "0123456789");
        String result = null;
        try {
            result = instance.doOCR(imageFile);
        } catch (TesseractException e1) {
            e1.printStackTrace();
        }
        result = result.replaceAll("[^a-z^A-Z^0-9]", "");
        return result;
    }
}
