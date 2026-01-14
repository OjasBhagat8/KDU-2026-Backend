package com.example.hospital.dto;

import java.util.UUID;

public class CreateUserRequest {
    private String username;
    private String timezone;
    private UUID tenantId;

    public String getUsername() { return username; }
    public void setUsername(String username) { this.username = username; }

    public String getTimezone() { return timezone; }
    public void setTimezone(String timezone) { this.timezone = timezone; }

    public UUID getTenantId() { return tenantId; }
    public void setTenantId(UUID tenantId) { this.tenantId = tenantId; }
}

