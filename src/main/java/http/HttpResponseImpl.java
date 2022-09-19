package http;

import java.util.HashMap;
import java.util.Map;

public class HttpResponseImpl implements HttpResponse{

    private final Map<String, String> headers;
    private int statusCode = 200;
    private String code = "OK";
    private String body;

    public HttpResponseImpl() {
        this.headers = new HashMap<>();
    }

    public String message() {
        StringBuilder message = new StringBuilder();
        message.append("HTTP/1.0")
                .append(" ")
                .append(statusCode)
                .append(" ")
                .append(code)
                .append("\n");
        for (Map.Entry<String, String> header : headers.entrySet()) {
            message.append(header.getKey())
                    .append(": ")
                    .append(header.getValue())
                    .append("\n");
        }
        message.append("\n");
        message.append(body);
        return message.toString();
    }

    public void addHeader(String key, String value) {
        headers.put(key, value);
    }

    public void addHeaders(Map<String, String> headers) {
        this.headers.putAll(headers);
    }

    public int getStatusCode() {
        return statusCode;
    }

    public void setStatusCode(int statusCode) {
        this.statusCode = statusCode;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        headers.put("Content-Length", String.valueOf(body.length()));
        this.body = body;
    }
}
