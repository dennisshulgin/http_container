package http;

import java.util.Map;

public interface HttpResponse {
    String message();
    void addHeader(String key, String value);
    void addHeaders(Map<String, String> headers);
    int getStatusCode();
    void setStatusCode(int statusCode);
    String getCode();
    void setCode(String code);
    String getBody();
    void setBody(String body);
}
