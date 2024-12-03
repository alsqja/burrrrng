package com.example.burrrrng.entity;

import jakarta.persistence.*;
import lombok.Getter;

@Getter
@Entity
@Table(name = "review")
public class Review extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "order_id")
    private Order order;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @Column(nullable = false)
    private int star;

    @Column(nullable = false)
    private String comment;

    public Review() {

    }

    public Review(Order order, User user, int star, String comment) {
        this.order = order;
        this.user = user;
        this.star = star;
        this.comment = comment;
    }
}
