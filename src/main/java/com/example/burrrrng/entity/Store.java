package com.example.burrrrng.entity;

import com.example.burrrrng.enums.StoreStatus;
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
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.Filter;
import org.hibernate.annotations.FilterDef;
import org.hibernate.annotations.ParamDef;
import org.hibernate.annotations.SQLDelete;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "store")
@Getter
@Setter
@SQLDelete(sql = "UPDATE store SET deleted_at = CURRENT_TIMESTAMP WHERE id = ?")
@FilterDef(name = "softDeleteFilterStore", parameters = @ParamDef(name = "deletedAt", type = LocalDateTime.class))
@Filter(name = "softDeleteFilterStore", condition = "deleted_at IS NULL")
public class Store extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private LocalTime openedAt;

    @Column(nullable = false)
    private LocalTime closedAt;

    @Column(nullable = false)
    private int minPrice;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private StoreStatus status;

    @Column
    private LocalDateTime deletedAt;

    @OneToMany(mappedBy = "store", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    private List<Menu> menus = new ArrayList<>();

    @OneToMany(mappedBy = "store", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    private List<Order> orders = new ArrayList<>();

    public Store() {
    }

    public Store(User user, String name, LocalTime openedAt, LocalTime closedAt, int minPrice, StoreStatus status) {
        this.user = user;
        this.name = name;
        this.openedAt = openedAt;
        this.closedAt = closedAt;
        this.minPrice = minPrice;
        this.status = status;
    }

}
