package org.project.springweb.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.project.springweb.dto.cartitem.CreateCartItemRequestDto;
import org.project.springweb.dto.cartitem.UpdateCartItemRequestDto;
import org.project.springweb.dto.shoppingcart.ShoppingCartResponseDto;
import org.project.springweb.model.User;
import org.project.springweb.service.shoppingcart.ShoppingCartService;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "Books store", description = "Endpoints for managing shopping carts")
@RestController
@RequiredArgsConstructor
@RequestMapping("/cart")
public class ShoppingCartController {
    private final ShoppingCartService shoppingCartService;

    @Operation(summary = "Add new cart item",
            description = "Add a new cart item to current user's shopping cart")
    @PreAuthorize("hasRole('USER')")
    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping
    public ShoppingCartResponseDto addCartItem(
            @RequestBody @Valid CreateCartItemRequestDto requestDto,
            @AuthenticationPrincipal User user) {
        return shoppingCartService.addCartItem(requestDto, user.getId());
    }

    @Operation(summary = "Get shopping cart",
            description = "Get all cart items from current user's shopping cart")
    @PreAuthorize("hasRole('USER')")
    @GetMapping
    public ShoppingCartResponseDto getShoppingCart(@AuthenticationPrincipal User user) {
        return shoppingCartService.getShoppingCartForUser(user.getId());
    }

    @Operation(summary = "Update cart item",
            description = "Update cart item by id from current user's shopping cart")
    @PreAuthorize("hasRole('USER')")
    @PutMapping("/items/{id}")
    public ShoppingCartResponseDto updateCartItem(
            @PathVariable Long id,
            @RequestBody @Valid UpdateCartItemRequestDto requestDto,
            @AuthenticationPrincipal User user) {
        return shoppingCartService.updateCartItem(id, requestDto, user.getId());
    }

    @Operation(summary = "Delete cart item",
            description = "Delete cart item by id from current user's shopping cart")
    @PreAuthorize("hasRole('USER')")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @DeleteMapping("/items/{id}")
    public void deleteCartItem(
            @PathVariable Long id,
            @AuthenticationPrincipal User user) {
        shoppingCartService.deleteCartItem(id, user.getId());
    }
}
