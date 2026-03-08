package com.anwar.ecommerce_api.model;

public enum PaymentStatus {
    PENDING,
    SUCCESS,
    FAILED
}
/*

        ---

        ## 💡 Relationship Summary
```
User ──────── Cart (One user has one cart)
Cart ──────── CartItem (One cart has many items)
CartItem ──── Product (Each item is a product)

User ──────── Order (One user has many orders)
Order ──────── OrderItem (One order has many items)
OrderItem ──── Product (Each item is a product)

Order ──────── Payment (One order has one payment)
```

        ---

        ## 📁 Your project should look like this now:
        ```
model/
        ├── Role.java            ✅
        ├── User.java            ✅
        ├── Category.java        ✅
        ├── Product.java         ✅
        ├── Cart.java            ✅
        ├── CartItem.java        ✅
        ├── Order.java           ✅
        ├── OrderStatus.java     ✅
        ├── OrderItem.java       ✅
        ├── Payment.java         ✅
        └── PaymentStatus.java   ✅
*/