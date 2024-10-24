package org.project.springweb.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.project.springweb.config.MapperConfig;
import org.project.springweb.dto.shoppingcart.ShoppingCartResponseDto;
import org.project.springweb.model.ShoppingCart;

@Mapper(config = MapperConfig.class, uses = {CartItemMapper.class})
public interface ShoppingCartMapper {
    @Mapping(source = "user.id", target = "userId")
    @Mapping(source = "cartItems", target = "cartItemsDtos", qualifiedByName = "toCartItemDtoSet")
    ShoppingCartResponseDto toDto(ShoppingCart shoppingCart);
}
