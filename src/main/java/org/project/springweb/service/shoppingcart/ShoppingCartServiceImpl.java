package org.project.springweb.service.shoppingcart;

import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.project.springweb.dto.cartitem.CreateCartItemRequestDto;
import org.project.springweb.dto.cartitem.UpdateCartItemRequestDto;
import org.project.springweb.dto.shoppingcart.ShoppingCartResponseDto;
import org.project.springweb.exception.DataProcessingException;
import org.project.springweb.exception.EntityNotFoundException;
import org.project.springweb.mapper.CartItemMapper;
import org.project.springweb.mapper.ShoppingCartMapper;
import org.project.springweb.model.Book;
import org.project.springweb.model.CartItem;
import org.project.springweb.model.ShoppingCart;
import org.project.springweb.model.User;
import org.project.springweb.repository.book.BookRepository;
import org.project.springweb.repository.cartitem.CartItemRepository;
import org.project.springweb.repository.shoppingcart.ShoppingCartRepository;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ShoppingCartServiceImpl implements ShoppingCartService {
    private final ShoppingCartRepository shoppingCartRepository;
    private final ShoppingCartMapper shoppingCartMapper;
    private final CartItemMapper cartItemMapper;
    private final CartItemRepository cartItemRepository;
    private final BookRepository bookRepository;

    @Override
    public void createShoppingCart(User user) {
        ShoppingCart shoppingCart = new ShoppingCart();
        shoppingCart.setUser(user);
        shoppingCartRepository.save(shoppingCart);
    }

    @Override
    public ShoppingCartResponseDto getShoppingCartForUser(Long userId) {
        return shoppingCartMapper.toDto(findShoppingCartByUserId(userId));
    }

    @Override
    public ShoppingCartResponseDto addCartItem(CreateCartItemRequestDto requestDto, Long userId) {
        Book book = bookRepository.findById(requestDto.getBookId()).orElseThrow(
                () -> new EntityNotFoundException("Cannot find book with id "
                        + requestDto.getBookId())
        );
        ShoppingCart shoppingCart = findShoppingCartByUserId(userId);
        Optional<CartItem> existingCartItem = shoppingCart.getCartItems().stream()
                .filter(item -> item.getBook().getId()
                        .equals(book.getId()))
                .findFirst();
        CartItem cartItem;
        if (existingCartItem.isPresent()) {
            cartItem = existingCartItem.get();
            UpdateCartItemRequestDto updateRequestDto = cartItemMapper
                    .toUpdateCartItemRequestDto(requestDto);
            updateCartItem(cartItem.getId(), updateRequestDto, userId);
        } else {
            cartItem = cartItemMapper.toEntity(requestDto);
            cartItem.setShoppingCart(shoppingCart);
            shoppingCart.getCartItems().add(cartItem);
        }
        cartItem.setBook(book);
        cartItemRepository.save(cartItem);
        return shoppingCartMapper.toDto(shoppingCart);
    }

    @Override
    public ShoppingCartResponseDto updateCartItem(Long cartId,
                                                  UpdateCartItemRequestDto requestDto,
                                                  Long userId) {
        CartItem cartItem = findCartItemById(cartId);
        ShoppingCart shoppingCart = findShoppingCartByUserId(userId);
        cartItem.setQuantity(requestDto.getQuantity());
        cartItemRepository.save(cartItem);
        return shoppingCartMapper.toDto(shoppingCart);
    }

    @Override
    public void deleteCartItem(Long cartId, Long userId) {
        ShoppingCart shoppingCart = findShoppingCartByUserId(userId);
        CartItem cartItem = shoppingCart.getCartItems().stream()
                .filter(item -> item.getId().equals(cartId))
                .findFirst().orElseThrow(
                        () -> new DataProcessingException("Cannot find cart item with id "
                                + cartId));
        cartItemRepository.delete(cartItem);
    }

    private ShoppingCart findShoppingCartByUserId(Long userId) {
        return shoppingCartRepository.findByUserId(userId).orElseThrow(
                () -> new EntityNotFoundException("Can't find shopping cart by userId "
                        + userId)
        );
    }

    private CartItem findCartItemById(Long cartId) {
        return cartItemRepository.findById(cartId).orElseThrow(
                () -> new EntityNotFoundException("Can't find cart item by id "
                        + cartId)
        );
    }
}
