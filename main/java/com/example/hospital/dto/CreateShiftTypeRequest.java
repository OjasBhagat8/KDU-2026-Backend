package com.example.hospital.dto;


import java.util.UUID;

public class CreateShiftTypeRequest {
    private String name;
    private String description;
    private UUID tenantId;

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }

    public UUID getTenantId() { return tenantId; }
    public void setTenantId(UUID tenantId) { this.tenantId = tenantId; }
}

