package api.caseutils;

import api.apidata.DataFilePathData;
import core.api.interfaceservice.ResponseDetail;
import core.api.interfaceservice.SendRequest;
import api.config.ShareVoid;
import core.utils.GetBeanFactoryUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.URISyntaxException;
import java.util.Map;

/**
 * 接口上下文
 */
public class InitContext {

    private static final Logger logger = LoggerFactory.getLogger(InitContext.class);

    public SendRequest sendRequest = (SendRequest) GetBeanFactoryUtil.getBean(SendRequest.class);
    public ShareVoid doHttp = (ShareVoid) GetBeanFactoryUtil.getBean(ShareVoid.class);

    public ResponseDetail response;
    public Map<String, String> cookie;

    public InitContext() {
        logger.debug("============= this is InitContext()");
        try {
            cookie = doHttp.loginGetCookie(DataFilePathData.LOGIN_FILE_PATH.getDataFilePathData());
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }
    }

    public InitContext(String path, String dataFile) {
        logger.debug("============= this is InitContext(String path,String dataFile)");
        try {
            cookie = doHttp.loginGetCookie(path, dataFile);
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }
    }
}
