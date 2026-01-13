package com.example.quickship.model;


import com.example.quickship.constants.DeliveryType;
import com.example.quickship.constants.PackageStatus;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;

import java.util.UUID;

@AllArgsConstructor
@Getter
public class Package {

    private UUID id;
    private String destinationAddress;
    private double weight;
    @Setter
    private PackageStatus status;
    private DeliveryType deliveryType;

}
