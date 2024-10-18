package org.project.springweb.service.book;

import java.util.List;
import lombok.RequiredArgsConstructor;
import org.project.springweb.dto.book.BookDto;
import org.project.springweb.dto.book.BookDtoWithoutCategoryIds;
import org.project.springweb.dto.book.BookSearchParametersDto;
import org.project.springweb.dto.book.CreateBookRequestDto;
import org.project.springweb.exception.EntityNotFoundException;
import org.project.springweb.mapper.BookMapper;
import org.project.springweb.model.Book;
import org.project.springweb.repository.book.BookRepository;
import org.project.springweb.repository.book.BookSpecificationBuilder;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class BookServiceImpl implements BookService {
    private final BookRepository bookRepository;
    private final BookMapper bookMapper;
    private final BookSpecificationBuilder bookSpecificationBuilder;

    @Override
    public List<BookDto> findAll(Pageable pageable) {
        return bookRepository.findAll(pageable).stream()
                .map(bookMapper::toDto)
                .toList();
    }

    @Override
    public BookDto getBookById(Long id) {
        Book book = bookRepository.findById(id).orElseThrow(
                () -> new EntityNotFoundException("Cannot find book with id: " + id)
        );
        return bookMapper.toDto(book);
    }

    @Override
    public BookDto create(CreateBookRequestDto requestDto) {
        Book book = bookMapper.toModel(requestDto);
        return bookMapper.toDto(bookRepository.save(book));
    }

    @Override
    public void delete(Long id) {
        Book book = bookRepository.findById(id).orElseThrow(
                () -> new EntityNotFoundException("Cannot find book with id: " + id));
        bookRepository.delete(book);
    }

    @Override
    public List<BookDto> search(BookSearchParametersDto params, Pageable pageable) {
        Specification<BookDto> bookSpecification = bookSpecificationBuilder
                .build(params);
        return bookRepository.findAll(bookSpecification, pageable).stream()
                .toList();
    }

    @Override
    public List<BookDtoWithoutCategoryIds> findAllByCategoryId(Long categoryId,
                                                               Pageable pageable) {
        return bookRepository.findAllByCategoryId(categoryId, pageable)
                .stream()
                .map(bookMapper::toDtoWithoutCategoryIds)
                .toList();
    }

    @Override
    public BookDto update(Long id, CreateBookRequestDto requestDto) {
        Book existedBook = bookRepository.findById(id).orElseThrow(
                () -> new EntityNotFoundException("Cannot find book with id: "
                        + id));
        bookMapper.updateBook(requestDto, existedBook);
        return bookMapper.toDto(bookRepository.save(existedBook));
    }
}
