package com.anwar.ecommerce_api.model;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "products")
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    private String description;

    @Column(nullable = false)
    private Double price;

    @Column(nullable = false)
    private Integer stock;

    private String imageUrl;

    @ManyToOne
    @JoinColumn(name = "category_id", nullable = false)
    private Category category;

    private LocalDateTime createdAt;

    @PrePersist
    public void prePersist() {
        this.createdAt = LocalDateTime.now();
    }

    // ✅ Getters and Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }

    public Double getPrice() { return price; }
    public void setPrice(Double price) { this.price = price; }

    public Integer getStock() { return stock; }
    public void setStock(Integer stock) { this.stock = stock; }

    public String getImageUrl() { return imageUrl; }
    public void setImageUrl(String imageUrl) { this.imageUrl = imageUrl; }

    public Category getCategory() { return category; }
    public void setCategory(Category category) { this.category = category; }

    public LocalDateTime getCreatedAt() { return createdAt; }
    public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }
}
/*

        ---

        ## 💡 Key Concepts Explained

| Annotation | What it does |
        |---|---|
        | `@OneToMany` | One category has many products |
        | `@ManyToOne` | Many products belong to one category |
        | `@JoinColumn(name="category_id")` | Creates `category_id` foreign key column in products table |
        | `mappedBy="category"` | Tells JPA the relationship is already mapped in Product side |
        | `cascade = CascadeType.ALL` | If category deleted, its products also delete |

        ---

        ## 🗄️ How Tables Look in MySQL

**categories table:**
        ```
id | name        | description
1  | Electronics | All electronic items
2  | Clothing    | All clothes
```

        **products table:**
        ```
id | name    | price  | stock | category_id
1  | iPhone  | 999.99 | 50    | 1
        2  | Samsung | 799.99 | 30    | 1
        3  | T-Shirt | 19.99  | 100   | 2
        ```

        ---

        ## 📁 Your project should look like this now:
        ```
model/
        ├── Role.java         ✅
        ├── User.java         ✅
        ├── Category.java     ✅
        └── Product.java      ✅  */
