package com.anwar.ecommerce_api.repository;

import com.anwar.ecommerce_api.model.CartItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CartItemRepository extends JpaRepository<CartItem, Long> {

    // Find cart item by cart id and product id
    Optional<CartItem> findByCartIdAndProductId(Long cartId, Long productId);
}
