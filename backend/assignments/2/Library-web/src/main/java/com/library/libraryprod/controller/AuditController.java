package com.library.libraryprod.controller;

import com.library.libraryprod.enums.BookStatus;
import com.library.libraryprod.libraryservice.AnalyticsService;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/analytics/v1")
public class AuditController {
    @Autowired
    private AnalyticsService analyticsService;

    @Operation(
            summary = "Gets the audit details."
    )
    @GetMapping("/audit")
    public ResponseEntity<Map<BookStatus,Long>> getAuditDetails(){
        return ResponseEntity.ok(analyticsService.auditCountsByStatus());
    }
}
