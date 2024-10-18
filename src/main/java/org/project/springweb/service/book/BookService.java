package org.project.springweb.service.book;

import java.util.List;
import org.project.springweb.dto.book.BookDto;
import org.project.springweb.dto.book.BookDtoWithoutCategoryIds;
import org.project.springweb.dto.book.BookSearchParametersDto;
import org.project.springweb.dto.book.CreateBookRequestDto;
import org.springframework.data.domain.Pageable;

public interface BookService {
    List<BookDto> findAll(Pageable pageable);

    BookDto getBookById(Long id);

    BookDto create(CreateBookRequestDto requestDto);

    BookDto update(Long id, CreateBookRequestDto requestDto);

    void delete(Long id);

    List<BookDto> search(BookSearchParametersDto params, Pageable pageable);

    List<BookDtoWithoutCategoryIds> findAllByCategoryId(Long categoryId, Pageable pageable);
}
