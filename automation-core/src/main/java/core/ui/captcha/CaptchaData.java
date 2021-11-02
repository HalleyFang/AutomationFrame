package core.ui.captcha;

/**
 * 根据屏幕大小设置验证码坐标
 * 具体数值需要根据具体情况设置，本类中的数据是测试数据
 */
public enum CaptchaData {

    //获取坐标参考 document.getElementById("").getBoundingClientRect()
    //屏幕大小 1358*650
    LoginCaptchaA(1057, 330),
    //屏幕大小 1912*960
    LoginCaptchaB(1369, 450);

    private int x;
    private int y;

    CaptchaData(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

}
