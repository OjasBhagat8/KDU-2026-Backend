package com.library.libraryprod.dto.request;

import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class BorrowBookRequest {
    @Size(max = 500) String note;
}
