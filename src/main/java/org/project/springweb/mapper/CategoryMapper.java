package org.project.springweb.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.project.springweb.config.MapperConfig;
import org.project.springweb.dto.category.CategoryDto;
import org.project.springweb.dto.category.CreateCategoryRequestDto;
import org.project.springweb.model.Category;

@Mapper(config = MapperConfig.class)
public interface CategoryMapper {
    CategoryDto toDto(Category category);

    Category toEntity(CreateCategoryRequestDto requestDto);

    void updateCategory(CreateCategoryRequestDto requestDto, @MappingTarget Category category);
}
