package com.example.hospital.model;

import java.util.UUID;

public class User {

    private UUID id;
    private String username;
    private Boolean loggedIn;
    private String timezone;
    private UUID tenantId;

    public UUID getId() { return id; }
    public void setId(UUID id) { this.id = id; }

    public String getUsername() { return username; }
    public void setUsername(String username) { this.username = username; }

    public Boolean getLoggedIn() { return loggedIn; }
    public void setLoggedIn(Boolean loggedIn) { this.loggedIn = loggedIn; }

    public String getTimezone() { return timezone; }
    public void setTimezone(String timezone) { this.timezone = timezone; }

    public UUID getTenantId() { return tenantId; }
    public void setTenantId(UUID tenantId) { this.tenantId = tenantId; }
}

