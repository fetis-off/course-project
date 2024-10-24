package org.project.springweb.service.shoppingcart;

import org.project.springweb.dto.cartitem.CreateCartItemRequestDto;
import org.project.springweb.dto.cartitem.UpdateCartItemRequestDto;
import org.project.springweb.dto.shoppingcart.ShoppingCartResponseDto;
import org.project.springweb.model.User;

public interface ShoppingCartService {
    void createShoppingCart(User user);

    ShoppingCartResponseDto getShoppingCartForUser(Long userId);

    ShoppingCartResponseDto addCartItem(CreateCartItemRequestDto requestDto, Long userId);

    ShoppingCartResponseDto updateCartItem(
            Long cartId,
            UpdateCartItemRequestDto requestDto,
            Long userId);

    void deleteCartItem(Long cartId, Long userId);
}
