package com.shop.entity;

import com.shop.constant.OrderStatus;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "orders")
@Getter
@Setter
public class Order extends BaseEntity{

    @Id
    @GeneratedValue
    @Column(name = "order_id")
    private Long id;

    @ManyToOne
//    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    private LocalDateTime orderDate;

    @Enumerated(EnumType.STRING)
    private OrderStatus orderStatus;

    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL,
    orphanRemoval = true)
//    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL,
//            orphanRemoval = true, fetch = FetchType.LAZY)
//    // 연관관계의 주인은 OrderItem이다. Order는 주인이 아니므로 읽기만 가능하기 때문에 mappedBy를 사용한다.
//    // order를 작성한 이유는 OrderItem에 있는 Order에 의해 관리된다는 의미다.
//    //CascadeType.ALL -> 부모 엔티티의 영속성 상태 변화를 자식 엔티티에 모두 전이하는 CascadeTypeAll 옵션을 설정한다.
    private List<OrderItem> orderItems = new ArrayList<>();

    private LocalDateTime regTime;
    private LocalDateTime updateTime;
}
