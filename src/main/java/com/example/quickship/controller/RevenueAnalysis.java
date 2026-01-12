package com.example.quickship.controller;

import com.example.quickship.model.Package;
import com.example.quickship.repository.PackageRepository;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("analytics/revenue")
public class RevenueAnalysis {

    private final PackageRepository packageRepository;


    public RevenueAnalysis(PackageRepository packageRepository) {
        this.packageRepository = packageRepository;
    }


    @GetMapping
    public Double getTotalRevenue() {
        // Logic to calculate and return total revenue
        List<Package> packageList = packageRepository.getAllPackages();

        return  packageList.stream()
                .filter(
                        p -> p.getStatus().name().equals("SORTED"))
                .mapToDouble(p -> p.getWeight()*2.50).sum();

    }
}
