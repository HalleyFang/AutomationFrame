package core.api.apiutils;

import org.apache.http.NameValuePair;
import org.apache.http.client.utils.URIBuilder;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;

public class GeneraUri {

    public synchronized static URI getUri(String host, int port, String path, List<NameValuePair> params, Boolean isHttps) throws URISyntaxException {
        URI uri = null;
        String protocol = "http";
        if (isHttps) {
            protocol = "https";
        }
        if (params == null) {
            uri = new URIBuilder().setScheme(protocol).setHost(host).setPort(port)
                    .setPath(path).build();
        } else {
            uri = new URIBuilder().setScheme(protocol).setHost(host).setPort(port)
                    .setPath(path).setParameters(params).build();
        }
        return uri;
    }
}
