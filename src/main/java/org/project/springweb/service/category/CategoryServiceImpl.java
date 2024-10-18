package org.project.springweb.service.category;

import java.util.List;
import lombok.RequiredArgsConstructor;
import org.project.springweb.dto.book.BookDtoWithoutCategoryIds;
import org.project.springweb.dto.category.CategoryDto;
import org.project.springweb.dto.category.CreateCategoryRequestDto;
import org.project.springweb.exception.EntityNotFoundException;
import org.project.springweb.mapper.BookMapper;
import org.project.springweb.mapper.CategoryMapper;
import org.project.springweb.model.Category;
import org.project.springweb.repository.book.BookRepository;
import org.project.springweb.repository.category.CategoryRepository;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CategoryServiceImpl implements CategoryService {
    private final CategoryRepository categoryRepository;
    private final BookRepository bookRepository;
    private final CategoryMapper categoryMapper;
    private final BookMapper bookMapper;

    @Override
    public List<CategoryDto> findAll(Pageable pageable) {
        return categoryRepository.findAll(pageable)
                .stream()
                .map(categoryMapper::toDto)
                .toList();
    }

    @Override
    public CategoryDto getById(Long id) {
        Category category = categoryRepository.findById(id).orElseThrow(
                () -> new EntityNotFoundException("Cannot find category with id: "
                + id));
        return categoryMapper.toDto(category);
    }

    @Override
    public CategoryDto save(CreateCategoryRequestDto requestDto) {
        Category category = categoryMapper.toEntity(requestDto);
        return categoryMapper.toDto(categoryRepository.save(category));
    }

    @Override
    public CategoryDto update(Long id, CreateCategoryRequestDto requestDto) {
        Category existedCategory = categoryRepository.findById(id)
                .orElseThrow(
                    () -> new EntityNotFoundException("Cannot find category with id: "
                        + id));
        categoryMapper.updateCategory(requestDto, existedCategory);
        return categoryMapper.toDto(categoryRepository.save(existedCategory));
    }

    @Override
    public void delete(Long id) {
        Category existedCategory = categoryRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Cannot find category with id: "
                                + id));
        categoryRepository.delete(existedCategory);
    }

    @Override
    public List<BookDtoWithoutCategoryIds> getBooksByCategoryId(Pageable pageable, Long id) {
        return bookRepository.findAllByCategoryId(id, pageable)
                .stream()
                .map(bookMapper::toDtoWithoutCategoryIds)
                .toList();
    }
}
