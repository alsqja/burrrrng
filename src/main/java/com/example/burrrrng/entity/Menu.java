package com.example.burrrrng.entity;

import com.example.burrrrng.enums.MenuStatus;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import org.hibernate.annotations.SQLDelete;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Table(name = "menu")
@SQLDelete(sql = "UPDATE menu SET deleted_at = ? WHERE id = ?")
public class Menu extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne
    @JoinColumn(name = "store_id", referencedColumnName = "id")
    private Store store;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private int price;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private MenuStatus status;

    @Column
    private LocalDateTime deletedAt;

    @OneToMany(mappedBy = "menu", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    private List<OrderMenu> orderMenus = new ArrayList<>();

    public Menu() {
    }

    public Menu(User user, Store store, String name, int price, MenuStatus status) {
        this.user = user;
        this.store = store;
        this.name = name;
        this.price = price;
        this.status = status;
    }

    

    public void setPrice(@NotNull(message = "가격은 필수입니다.") @Min(value = 500, message = "가격은 최소 500원 이상이어야 합니다.") int price) {
        this.price = price;
    }

    public void setName(@NotNull(message = "이름은 필수입니다.") String name) {
        this.name = name;
    }

    public void setStatus(MenuStatus status) {
        this.status = status;
    }

    public void setDeletedAt(LocalDateTime now) {
        this.deletedAt = now;
    }
}
