package com.example.burrrrng.entity;

import jakarta.persistence.*;
import lombok.Getter;

@Entity
@Table(name = "cart_menu")
@Getter
public class CartMenu extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "cart_id")
    private Cart cart;

    @ManyToOne
    @JoinColumn(name = "menu_id")
    private Menu menu;

    @Column(nullable = false)
    private int amount;

    public CartMenu() {
    }

    public CartMenu(Cart cart, Menu menu, int amount) {
        this.cart = cart;
        this.menu = menu;
        this.amount = amount;
    }

}
