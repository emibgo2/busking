package com.example.demo;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Method {
    private String url;
    private String href;
    private String method;
    private String description;
    private String param;

    public Method(String url, String href, String method, String description, String param) {
        this.url = url;
        this.href = href;
        this.method = method;
        this.description = description;
        this.param = param;
    }
}
