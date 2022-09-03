package http;

import java.util.HashMap;
import java.util.Map;

public class HttpRequestImpl implements HttpRequest{

    private final static String DELIMITER = "\r\n\r\n";
    private final static String NEW_LINE = "\n";

    private final HttpMethod method;
    private final String url;
    private final String protocol;
    private final Map<String, String> headers;
    private final String body;
    private final Map<String, String> params;

    public HttpRequestImpl(String message) {
        String[] parts = message.split(DELIMITER);
        String head = parts[0];
        String[] lines = head.split(NEW_LINE);

        String[] firstLine = lines[0].split(" ");
        String[] urlParts = firstLine[1].split("\\?(?!\\?)");
        this.method = HttpMethod.valueOf(firstLine[0]);
        this.url = urlParts[0];
        this.protocol = firstLine[2];
        this.headers = new HashMap<>();
        this.params = new HashMap<>();

        if(urlParts.length > 1) {
            String[] paramsArray = urlParts[1].split("&");
            for(int i = 0; i < paramsArray.length; i++) {
                String[] param = paramsArray[i].split("=");
                if (param.length == 2) {
                    params.put(param[0], param[1]);
                }
            }
        }

        for(int i = 1; i < lines.length; i++) {
            String[] line = lines[i].split(":");
            this.headers.put(line[0].trim(), line[1].trim());
        }

        String bodyLength = headers.get("Content-Length");
        int len = bodyLength != null ? Integer.parseInt(bodyLength) : 0;
        body = len > 0? parts[1].trim() : "";
    }

    public HttpMethod getMethod() {
        return method;
    }

    public String getUrl() {
        return url;
    }

    public String getProtocol() {
        return protocol;
    }

    public Map<String, String> getHeaders() {
        return headers;
    }

    public Map<String, String> getParams() {
        return params;
    }

    public String getBody() {
        return body;
    }
}
