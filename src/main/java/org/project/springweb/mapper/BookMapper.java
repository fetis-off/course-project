package org.project.springweb.mapper;

import java.util.Set;
import java.util.stream.Collectors;
import org.mapstruct.AfterMapping;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.Named;
import org.project.springweb.config.MapperConfig;
import org.project.springweb.dto.book.BookDto;
import org.project.springweb.dto.book.BookDtoWithoutCategoryIds;
import org.project.springweb.dto.book.CreateBookRequestDto;
import org.project.springweb.model.Book;
import org.project.springweb.model.Category;

@Mapper(config = MapperConfig.class)
public interface BookMapper {
    BookDto toDto(Book book);

    Book toModel(CreateBookRequestDto requestDto);

    BookDtoWithoutCategoryIds toDtoWithoutCategoryIds(Book book);

    void updateBook(CreateBookRequestDto bookDto, @MappingTarget Book book);

    @AfterMapping
    default void setCategories(@MappingTarget Book book, CreateBookRequestDto requestDto) {
        Set<Category> categories = requestDto.getCategoryIds()
                .stream()
                .map(Category::new)
                .collect(Collectors.toSet());
        book.setCategories(categories);
    }

    @AfterMapping
    default void setCategoryIds(@MappingTarget BookDto bookDto, Book book) {
        Set<Long> categoryIds = book.getCategories().stream()
                .map(Category::getId)
                .collect(Collectors.toSet());

        bookDto.setCategoryIds(categoryIds);
    }

    @Named(value = "bookFromId")
    default Book bookFromId(Long id) {
        Book book = new Book();
        book.setId(id);
        return book;
    }
}
