package http;

import java.util.Map;

public interface HttpRequest {
    HttpMethod getMethod();
    String getUrl();
    String getProtocol();
    Map<String, String> getHeaders();
    Map<String, String> getParams();
    String getBody();
}
