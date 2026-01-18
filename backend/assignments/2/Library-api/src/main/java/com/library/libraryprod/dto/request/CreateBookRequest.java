package com.library.libraryprod.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class CreateBookRequest  {
    @NotBlank @Size(max = 200) String title;
    @NotBlank @Size(max = 200) String author;
    @NotBlank @Size(max = 200) String isbn;
}
