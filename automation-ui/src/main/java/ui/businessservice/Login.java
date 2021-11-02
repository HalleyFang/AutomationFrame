package ui.businessservice;

import core.utils.AssertionRuntime;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Component;
import ui.config.BaseData;
import ui.pageobject.HomePage;
import ui.pageobject.LoginPage;

import java.io.IOException;

/**
 * 公共操作：登陆
 */

@Component
public class Login {
    private Logger logger = Logger.getLogger(Login.class);

    public void login(LoginPage loginPage) throws InterruptedException, IOException {
        HomePage homePage = loginPage.loginSuccess(BaseData.getUsername(),
                BaseData.getPassword());
        AssertionRuntime.verifyEquals(homePage.getUserinfoMsg(), BaseData.getUsername());
        if (!AssertionRuntime.flag) {
            logger.debug("login failed");
        }
    }

    public void login(LoginPage loginPage, String username, String password) throws InterruptedException, IOException {
        HomePage homePage = loginPage.loginSuccess(username, password);
        AssertionRuntime.verifyEquals(homePage.getUserinfoMsg(), username);
        if (!AssertionRuntime.flag) {
            logger.debug("login failed");
        }
    }
}
