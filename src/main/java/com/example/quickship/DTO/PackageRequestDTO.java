package com.example.quickship.DTO;

import com.example.quickship.constants.DeliveryType;
import com.example.quickship.constants.PackageStatus;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class PackageRequestDTO {
    private final String destination;
    private final Double weight;
    private final DeliveryType deliveryType;
}
