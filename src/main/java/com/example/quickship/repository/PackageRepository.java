package com.example.quickship.repository;

import com.example.quickship.model.Package;
import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Repository;

import java.util.*;

@Repository
public class PackageRepository {

    private final Map<UUID, Package> packageRepository = new HashMap<>();

    public Package addPackage(Package newPackage){
        UUID id = newPackage.getId();
        packageRepository.put(id , newPackage);
        return newPackage;
    }

    public List<Package> getAllPackages(){
        return  packageRepository.values().stream().toList();
    }

    public Long getPending(){
        return packageRepository.values().
                stream().
                filter(p -> p.getStatus().name().equals("PENDING"))
                .count();
    }

    public Long getSorted(){
        return packageRepository.values()
                .stream().
                filter(p -> p.getStatus().name().equals("SORTED"))
                .count();
    }


}
