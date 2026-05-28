package com.phonestore.dto.auth;

public class LoginResponse {
    private String accessToken;
    private String tokenType = "Bearer";
    private String role;
    private UserInfoResponse user;

    public LoginResponse() {}

    public LoginResponse(String accessToken, String role, UserInfoResponse user) {
        this.accessToken = accessToken;
        this.role = role;
        this.user = user;
    }

    public String getAccessToken() { return accessToken; }
    public void setAccessToken(String accessToken) { this.accessToken = accessToken; }
    public String getTokenType() { return tokenType; }
    public void setTokenType(String tokenType) { this.tokenType = tokenType; }
    public String getRole() { return role; }
    public void setRole(String role) { this.role = role; }
    public UserInfoResponse getUser() { return user; }
    public void setUser(UserInfoResponse user) { this.user = user; }
}
