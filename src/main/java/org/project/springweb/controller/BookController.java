package org.project.springweb.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.project.springweb.dto.book.BookDto;
import org.project.springweb.dto.book.BookSearchParametersDto;
import org.project.springweb.dto.book.CreateBookRequestDto;
import org.project.springweb.model.User;
import org.project.springweb.service.book.BookService;
import org.springframework.http.HttpStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/books")
public class BookController {
    private final BookService bookService;

    @PreAuthorize("hasRole('USER')")
    @GetMapping
    public List<BookDto> getAll(@PageableDefault(size = 20) Pageable pageable) {
        return bookService.getAll(pageable);
    }

    @PreAuthorize("hasRole('USER')")
    @GetMapping("/{id}")
    public BookDto getBookById(@PathVariable Long id) {
        return bookService.getBookById(id);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public BookDto createBook(@RequestBody @Valid CreateBookRequestDto requestDto) {
        return bookService.create(requestDto);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/{id}")
    public void deleteBookById(@PathVariable Long id) {
        bookService.delete(id);
    }

    @PreAuthorize("hasRole('USER')")
    @GetMapping("/search")
    public Page<BookDto> searchBooks(BookSearchParametersDto searchParameters,
                                     @PageableDefault(size = 20) Pageable pageable) {
        return bookService.search(searchParameters, pageable);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("/{id}")
    public BookDto updateBook(@PathVariable Long id, @RequestBody @Valid CreateBookRequestDto requestDto) {
        return bookService.update(id, requestDto);
    }
}
