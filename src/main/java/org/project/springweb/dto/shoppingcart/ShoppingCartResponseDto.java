package org.project.springweb.dto.shoppingcart;

import java.util.Set;
import lombok.Data;
import org.project.springweb.dto.cartitem.CartItemResponseDto;

@Data
public class ShoppingCartResponseDto {
    private Long id;
    private Long userId;
    private Set<CartItemResponseDto> cartItemsDtos;
}
