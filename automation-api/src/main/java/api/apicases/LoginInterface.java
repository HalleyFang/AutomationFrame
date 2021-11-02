package api.apicases;

import api.apidata.DataFilePathData;
import api.apidata.PathData;
import api.apidata.jsonanalysis.DefaultJsonAnalysis;
import api.config.InterfaceConfig;
import com.google.gson.JsonObject;
import core.api.apiutils.GeneraUri;
import core.api.apiutils.GsonChange;
import core.api.interfaceservice.ResponseDetail;
import core.api.interfaceservice.SendRequest;
import core.data.TestCaseType;
import core.utils.*;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.HashMap;
import java.util.Map;

/**
 * 登陆接口测试
 */
public class LoginInterface {

    private SendRequest sendRequest = (SendRequest) GetBeanFactoryUtil.getBean(SendRequest.class);
    private GsonChange gsonChange = (GsonChange) GetBeanFactoryUtil.getBean(GsonChange.class);
    private InterfaceConfig interfaceConfig = (InterfaceConfig) GetBeanFactoryUtil.getBean(InterfaceConfig.class);

    @Test(description = "登录接口成功登录", priority = 0)
    public void loginInterface() throws URISyntaxException {
        URI uri = GeneraUri.getUri(interfaceConfig.getIp(), interfaceConfig.getPort(), interfaceConfig.getContextPath() + PathData.LOGIN_PATH.getPath(), null, false);
        ResponseDetail responseDetail = sendRequest.postRequest(uri, null, DataFilePathData.LOGIN_FILE_PATH.getDataFilePathData(), null, new DefaultJsonAnalysis());
        int status = responseDetail.getStatus();
        AssertionCtl.verifyEquals(status, 200);
    }

    @Test(description = "登录接口错误的用户名和密码登录失败", priority = 1, dataProvider = "loginFailed")
    public void loginInterfaceFailed(String name, String pass) throws URISyntaxException, IOException {
        URI uri = GeneraUri.getUri(interfaceConfig.getIp(), interfaceConfig.getPort(), interfaceConfig.getContextPath() + PathData.LOGIN_PATH.getPath(), null, false);
        Map<String, Object> bodyMap = new HashMap<>();
        bodyMap.put("username", name);
        bodyMap.put("password", pass);
        ResponseDetail responseDetail = sendRequest.postRequest(uri, null, DataFilePathData.LOGIN_FILE_PATH.getDataFilePathData(), bodyMap, new DefaultJsonAnalysis());
        int status = responseDetail.getStatus();
        AssertionRuntime.verifyEquals(status, 500);
        JsonObject jsonResult = gsonChange.jsonStrToJsonObject(responseDetail.getResponse_msg());
        if (jsonResult == null) {
            AssertionRuntime.verifyEquals(1, -1);
        } else {
            AssertionRuntime.verifyEquals(jsonResult.get("message").getAsString(), "用户名 or 密码不正确");
        }
    }

    @DataProvider(name = "loginFailed")
    public Object[][] createData1() {
        Object[][] objects = null;
        objects = ReadExcel.readExcelObject("login", TestCaseType.INTERFACE_CASE);
        return objects;
    }
}
