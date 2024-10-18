package org.project.springweb.dto.book;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Set;
import lombok.Data;
import org.project.springweb.validation.book.Author;
import org.project.springweb.validation.book.Isbn;

@Data
public class CreateBookRequestDto {
    @NotBlank
    private String title;
    @Author
    private String author;
    @Isbn
    private String isbn;
    @NotNull
    @Min(value = 0)
    private BigDecimal price;

    @NotEmpty
    private Set<Long> categoryIds = new HashSet<>();
    private String description;
    private String coverImage;
}
