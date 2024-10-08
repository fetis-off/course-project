package org.project.springweb.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.project.springweb.dto.book.BookDto;
import org.project.springweb.dto.book.BookSearchParametersDto;
import org.project.springweb.dto.book.CreateBookRequestDto;
import org.project.springweb.service.book.BookService;
import org.springframework.http.HttpStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/books")
public class BookController {
    private final BookService bookService;

    @GetMapping
    public List<BookDto> getAll(@PageableDefault(size = 20) Pageable pageable) {
        return bookService.getAll(pageable);
    }

    @GetMapping("/{id}")
    public BookDto getBookById(@PathVariable Long id) {
        return bookService.getBookById(id);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public BookDto createBook(@RequestBody @Valid CreateBookRequestDto requestDto) {
        return bookService.create(requestDto);
    }

    @DeleteMapping("/{id}")
    public void deleteBookById(@PathVariable Long id) {
        bookService.delete(id);
    }

    @GetMapping("/search")
    public Page<BookDto> searchBooks(BookSearchParametersDto searchParameters,
                                     @PageableDefault(size = 20) Pageable pageable) {
        return bookService.search(searchParameters, pageable);
    }

    @PutMapping("/{id}")
    public BookDto updateBook(@PathVariable Long id, @RequestBody @Valid CreateBookRequestDto requestDto) {
        return bookService.update(id, requestDto);
    }
}
