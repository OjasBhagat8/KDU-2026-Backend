package warehouse;
// package com.company.warehouse;

import java.util.List;
import java.util.stream.Collectors;
import java.util.Set;

public class Inventory {
    private String name;
    private List<List<String>> palletItemIds; 

    public Inventory(String name, List<List<String>> palletItemIds) {
        this.name = name;
        this.palletItemIds = palletItemIds;
    }
    
    public   List<List<String>> getPalletItemIds() { return palletItemIds; }
    
    // Simulates an expensive database lookup
    public static Inventory findItem(String id) {
        if (id.equals("A100")) {
            return new Inventory("Main Inventory", List.of(
                List.of("P10", "P20"),
                List.of("P30", "P10", "P40")
            ));
        }
        return null; // Search fails

    }

    public static void main(String[] args) {

        Inventory inventory = new Inventory(
            "DUMMY-INVENTORY",
                List.of(
                    List.of("DUMMY-1", "DUMMY-2"),
                    List.of("DUMMY-3")
                )
            );
        Set<String> flatSet = inventory.getPalletItemIds()
            .stream()
            .flatMap(List::stream)   
            .collect(Collectors.toSet()); 

        System.out.println(flatSet);
    }
}

// File: ItemPlaceholder.java
class ItemPlaceholder {
    // This is a highly resource-intensive object to create.
    public ItemPlaceholder() { 
        System.out.println("ALERT: Creating expensive placeholder object!"); 
    }
    public String getInfo() { return "ID-NOT-FOUND: Placeholder Item"; }
}

