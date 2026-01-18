package com.library.libraryprod.dto.response;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.time.Instant;

@Getter
@Setter
@Data
public class LoanResponse {
    Long id;
    Long bookId;
    Long userId;
    Instant borrowedAt;
    Instant returnedAt;
    String status;
}
