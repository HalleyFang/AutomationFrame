package ui.cases;

import core.data.TestCaseType;
import core.utils.ReadExcel;
import org.apache.log4j.Logger;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import ui.config.BaseData;
import ui.pageobject.HomePage;
import ui.pageobject.LoginPage;
import ui.pageobject.pagedata.PromptMessage;

/**
 * 登陆测试用例
 */
public class LoginCases extends BaseCase {

    private Logger logger = Logger.getLogger(LoginCases.class);
    private LoginPage loginPage;
    private String username;
    private String password;

    @BeforeClass
    public void setUp() {
        loginPage = new LoginPage(driver);
        username = BaseData.getUsername();
        password = BaseData.getPassword();
    }

    @Test
    public void loginSuccess() {
        HomePage homePage = loginPage.loginSuccess(username, password);
        Assert.assertEquals(homePage.getUserinfoMsg(), username);
    }

    @Test(dataProvider = "loginFailed")
    public void loginFailed(String username, String password) {
        loginPage.login(username, password);
        Assert.assertEquals(loginPage.getMessageBox(), PromptMessage.LOGIN_ERROR.getMsg());
    }

    @DataProvider(name = "loginFailed")
    public Object[][] createData1() {
        Object[][] objects = ReadExcel.readExcelObject("login", TestCaseType.UI_CASE);
        return objects;
    }

    @AfterClass
    public void tearDown() {
    }

}
