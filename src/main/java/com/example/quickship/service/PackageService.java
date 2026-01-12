package com.example.quickship.service;

import com.example.quickship.DTO.PackageRequestDTO;
import com.example.quickship.constants.PackageStatus;
import com.example.quickship.model.Package;
import com.example.quickship.repository.PackageRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Service
public class PackageService {

    private final PackageRepository repo;

    // Thread pool for async scanning
    private final ExecutorService scannerPool = Executors.newFixedThreadPool(5);

    public PackageService(PackageRepository repo) {
        this.repo = repo;
    }

    //  Add package asynchronously
    public Package addPackage(PackageRequestDTO newPackage) {
        Package updatedPackage = new Package(UUID.randomUUID(),
                newPackage.getDestination(),
                newPackage.getWeight(),
                PackageStatus.PENDING,
                newPackage.getDeliveryType());
        repo.addPackage(updatedPackage);
        startScanAsync(updatedPackage);
        return updatedPackage;
    }

    // Background scan (3 seconds)
    private void startScanAsync(Package newPackage) {
        scannerPool.submit(() -> {
            try {
                Thread.sleep(3000);
                newPackage.setStatus(PackageStatus.SORTED);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        });
    }

    // For analytics
    public List<Package> getAll() {
        return repo.getAllPackages();
    }

    public long getPendingCount() {
        return repo.getPending();
    }

    public long getSortedCount() {
        return repo.getSorted();
    }
}
