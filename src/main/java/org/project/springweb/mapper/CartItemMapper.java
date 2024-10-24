package org.project.springweb.mapper;

import java.util.Set;
import java.util.stream.Collectors;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.project.springweb.config.MapperConfig;
import org.project.springweb.dto.cartitem.CartItemResponseDto;
import org.project.springweb.dto.cartitem.CreateCartItemRequestDto;
import org.project.springweb.dto.cartitem.UpdateCartItemRequestDto;
import org.project.springweb.model.CartItem;

@Mapper(config = MapperConfig.class, uses = {BookMapper.class})
public interface CartItemMapper {
    @Mapping(source = "bookId", target = "book", qualifiedByName = "bookFromId")
    CartItem toEntity(CreateCartItemRequestDto requestDto);

    @Mapping(source = "book.id", target = "bookId")
    @Mapping(source = "book.title", target = "bookTitle")
    CartItemResponseDto toDto(CartItem cartItem);

    @Named(value = "toCartItemDtoSet")
    default Set<CartItemResponseDto> toCartItemDtoSet(Set<CartItem> cartItems) {
        return cartItems.stream()
                .map(this::toDto)
                .collect(Collectors.toSet());
    }

    UpdateCartItemRequestDto toUpdateCartItemRequestDto(CreateCartItemRequestDto requestDto);
}
