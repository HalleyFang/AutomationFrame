package core.ui.utils;

import core.ui.captcha.CaptchaData;
import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.StringUtils;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;

public class ScreenShot {

    private Logger logger = LoggerFactory.getLogger(ScreenShot.class);

    public static final String reportDir = "output";
    public static final String failedImageDir = "failedimage";
    public static final String failedImage = reportDir + File.separator + failedImageDir;
    public static final String captchaImage = "captchaimage";

    public static void clearFailedImage() {
        deleteFile(new File(failedImage));
    }

    public static void clearCaptcha() {
        deleteFile(new File(captchaImage));
    }

    private static void deleteFile(File file) {
        if (file.exists()) {
            for (File f : file.listFiles()) {
                f.delete();
            }
        }
    }

    private static void mkDir(String dir) {
        Path path = Paths.get(dir);
        if (!path.toFile().exists()) {
            path.toFile().mkdir();
        }
    }

    public void failedImageScreenShot(WebDriver driver, String pName) throws IOException {
        if (StringUtils.isBlank(pName)) {
            StackTraceElement a = Thread.currentThread().getStackTrace()[2];
            pName = a.getClassName() + "_" + a.getMethodName();
        }
        String fileName = pName + ".png";
        logger.debug("failedimage:" + fileName);
        mkDir(failedImage);
        File srcFile = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
        FileUtils.moveFile(srcFile, new File(failedImage + File.separator + fileName));
    }


    public void captchaScreenShot(WebDriver driver, String pName, WebElement webElement, Coordinate coordinate) throws IOException {
        captchaScreenShot(driver, pName, webElement, 0, 0, coordinate);
    }

    /**
     * @param driver
     * @param pName
     * @param webElement
     * @param width      宽度偏移量
     * @param height     高度偏移量
     * @param coordinate
     * @throws IOException
     */
    public void captchaScreenShot(WebDriver driver, String pName, WebElement webElement, int width, int height, Coordinate coordinate) throws IOException {
        byte[] srcImg = ((TakesScreenshot) driver).getScreenshotAs(OutputType.BYTES);
        BufferedImage originalImage = ImageIO.read(new ByteArrayInputStream(srcImg));
        Dimension size = webElement.getSize();
        BufferedImage croppedImage = originalImage.getSubimage(coordinate.getX(), coordinate.getY(), size.getWidth() + width, size.getHeight() + height);
        if (StringUtils.isBlank(pName)) {
            pName = "captcha";
        }
        String fileName = pName + ".png";
        mkDir(captchaImage);
        cleanLinesInImage(croppedImage, captchaImage + File.separator + fileName);
    }

    /**
     * 根据窗口大小获取验证码坐标
     *
     * @param dimension
     * @return
     */
    public Coordinate windowsSize(Dimension dimension) {
        Coordinate coordinate = new Coordinate();
        int width = dimension.getWidth();
        logger.debug("windows size is " + width + "," + dimension.getHeight());
        if (width >= 1300 && width <= 1400) {
            coordinate.setX(CaptchaData.LoginCaptchaA.getX());
            coordinate.setY(CaptchaData.LoginCaptchaA.getY());
        } else if (width >= 1900 && width <= 2000) {
            coordinate.setX(CaptchaData.LoginCaptchaB.getX());
            coordinate.setY(CaptchaData.LoginCaptchaB.getY());
        }
        return coordinate;
    }

    /**
     * @param bufferedImage 需要去噪的图像
     * @param saveFile      去噪后的图像保存地址
     * @throws IOException
     */
    private void cleanLinesInImage(BufferedImage bufferedImage, String saveFile) throws IOException {

        int h = bufferedImage.getHeight();
        int w = bufferedImage.getWidth();

        // 灰度化
        int[][] gray = new int[w][h];
        for (int x = 0; x < w; x++) {
            for (int y = 0; y < h; y++) {
                int argb = bufferedImage.getRGB(x, y);
                // 图像加亮（调整亮度识别率非常高）
                int r = (int) (((argb >> 16) & 0xFF) * 1.1 + 30);
                int g = (int) (((argb >> 8) & 0xFF) * 1.1 + 30);
                int b = (int) (((argb >> 0) & 0xFF) * 1.1 + 30);
                if (r >= 255) {
                    r = 255;
                }
                if (g >= 255) {
                    g = 255;
                }
                if (b >= 255) {
                    b = 255;
                }
                gray[x][y] = (int) Math
                        .pow((Math.pow(r, 2.2) * 0.2973 + Math.pow(g, 2.2)
                                * 0.6274 + Math.pow(b, 2.2) * 0.0753), 1 / 2.2);
            }
        }

        // 二值化
        int threshold = ostu(gray, w, h);
        BufferedImage binaryBufferedImage = new BufferedImage(w, h, BufferedImage.TYPE_BYTE_BINARY);
        for (int x = 0; x < w; x++) {
            for (int y = 0; y < h; y++) {
                if (gray[x][y] > threshold) {
                    gray[x][y] |= 0x00FFFF;
                } else {
                    gray[x][y] &= 0xFF0000;
                }
                binaryBufferedImage.setRGB(x, y, gray[x][y]);
            }
        }

        //去除干扰线条
        for (int y = 1; y < h - 1; y++) {
            for (int x = 1; x < w - 1; x++) {
                boolean flag = false;
                if (isBlack(binaryBufferedImage.getRGB(x, y))) {
                    //左右均为空时，去掉此点
                    if (isWhite(binaryBufferedImage.getRGB(x - 1, y)) && isWhite(binaryBufferedImage.getRGB(x + 1, y))) {
                        flag = true;
                    }
                    //上下均为空时，去掉此点
                    if (isWhite(binaryBufferedImage.getRGB(x, y + 1)) && isWhite(binaryBufferedImage.getRGB(x, y - 1))) {
                        flag = true;
                    }
                    //斜上下为空时，去掉此点
                    if (isWhite(binaryBufferedImage.getRGB(x - 1, y + 1)) && isWhite(binaryBufferedImage.getRGB(x + 1, y - 1))) {
                        flag = true;
                    }
                    if (isWhite(binaryBufferedImage.getRGB(x + 1, y + 1)) && isWhite(binaryBufferedImage.getRGB(x - 1, y - 1))) {
                        flag = true;
                    }
                    if (flag) {
                        binaryBufferedImage.setRGB(x, y, -1);
                    }
                }
            }
        }


        // 矩阵打印
        /*for (int y = 0; y < h; y++)
        {
            for (int x = 0; x < w; x++)
            {
                if (isBlack(binaryBufferedImage.getRGB(x, y)))
                {
                    System.out.print("*");
                } else
                {
                    System.out.print(" ");
                }
            }
            System.out.println();
        }
*/
        ImageIO.write(binaryBufferedImage, "png", new File(saveFile));
    }

    private boolean isBlack(int colorInt) {
        Color color = new Color(colorInt);
        if (color.getRed() + color.getGreen() + color.getBlue() <= 300) {
            return true;
        }
        return false;
    }

    private boolean isWhite(int colorInt) {
        Color color = new Color(colorInt);
        if (color.getRed() + color.getGreen() + color.getBlue() > 300) {
            return true;
        }
        return false;
    }

    private int isBlackOrWhite(int colorInt) {
        if (getColorBright(colorInt) < 30 || getColorBright(colorInt) > 730) {
            return 1;
        }
        return 0;
    }

    private int getColorBright(int colorInt) {
        Color color = new Color(colorInt);
        return color.getRed() + color.getGreen() + color.getBlue();
    }

    private int ostu(int[][] gray, int w, int h) {
        int[] histData = new int[w * h];
        // Calculate histogram
        for (int x = 0; x < w; x++) {
            for (int y = 0; y < h; y++) {
                int red = 0xFF & gray[x][y];
                histData[red]++;
            }
        }

        // Total number of pixels
        int total = w * h;

        float sum = 0;
        for (int t = 0; t < 256; t++)
            sum += t * histData[t];

        float sumB = 0;
        int wB = 0;
        int wF = 0;

        float varMax = 0;
        int threshold = 0;

        for (int t = 0; t < 256; t++) {
            wB += histData[t]; // Weight Background
            if (wB == 0)
                continue;

            wF = total - wB; // Weight Foreground
            if (wF == 0)
                break;

            sumB += (float) (t * histData[t]);

            float mB = sumB / wB; // Mean Background
            float mF = (sum - sumB) / wF; // Mean Foreground

            // Calculate Between Class Variance
            float varBetween = (float) wB * (float) wF * (mB - mF) * (mB - mF);

            // Check if new maximum found
            if (varBetween > varMax) {
                varMax = varBetween;
                threshold = t;
            }
        }

        return threshold;
    }

    /*public static void main(String[] args) throws IOException, InterruptedException {
        ResourceBundle config = ReadConfig.readConfig("ui/ui.properties");
        String driver_path = config.getString("ui.fireFoxDriverPath");
        String driver_name = "webdriver.gecko.driver";
        System.setProperty(driver_name, driver_path);
        System.setProperty(FirefoxDriver.SystemProperty.BROWSER_LOGFILE,"/dev/null");
        WebDriver driver = new FirefoxDriver();
        driver.get("http://127.0.0.1:8081");
        Thread.sleep(3000);
        System.out.println("========"+driver.manage().window().getSize());
        WebElement element = driver.findElement(By.xpath("//div[@class='lw-login-form-item captcha']/img"));
        ScreenShot screenShot = new ScreenShot();
        Coordinate coordinate = new Coordinate();
        coordinate.setX(1057);
        coordinate.setY(330);
        screenShot.captchaScreenShot(driver,"logintestimg",element,coordinate);
        GetCaptcha getCaptcha = new GetCaptcha();
        String c = getCaptcha.getCaptcha("logintestimg");
        System.out.println(c);
    }*/
}
