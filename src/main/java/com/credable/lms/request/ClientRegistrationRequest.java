package com.credable.lms.request;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;

@Getter
public class ClientRegistrationRequest {
    @JsonProperty("url")
    private String url;

    @JsonProperty("name")
    private String name;

    @JsonProperty("username")
    private String username;

    @JsonProperty("password")
    private String password;

    public ClientRegistrationRequest() {}

    public ClientRegistrationRequest(String url, String name, String username, String password) {
        this.url = url;
        this.name = name;
        this.username = username;
        this.password = password;
    }

}

