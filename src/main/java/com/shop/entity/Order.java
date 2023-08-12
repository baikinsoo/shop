package com.shop.entity;

import com.shop.constant.OrderStatus;
import lombok.Getter;
import lombok.Setter;
import org.aspectj.weaver.ast.Or;

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

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    private LocalDateTime orderDate;

    @Enumerated(EnumType.STRING)
    private OrderStatus orderStatus;

    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL,
            orphanRemoval = true, fetch = FetchType.LAZY)
//    // 연관관계의 주인은 OrderItem이다. Order는 주인이 아니므로 읽기만 가능하기 때문에 mappedBy를 사용한다.
//    // order를 작성한 이유는 OrderItem에 있는 Order에 의해 관리된다는 의미다.
//    //CascadeType.ALL -> 부모 엔티티의 영속성 상태 변화를 자식 엔티티에 모두 전이하는 CascadeTypeAll 옵션을 설정한다.
    private List<OrderItem> orderItems = new ArrayList<>();

//    private LocalDateTime regTime;
//    private LocalDateTime updateTime;

    public void addOrderItem(OrderItem orderItem) {
        //1. orderItems에는 주문 상품 정보들을 담아준다.
        // orderItem 객체를 order 객체의 orderItems에 추가한다.
        orderItems.add(orderItem);
        orderItem.setOrder(this);
        //2. Order 엔티티와 OrderItem 엔티티가 양방향 참조 관계 이므로,
        //orderItem 객체에도 order 객체를 세팅한다.
    }

    public static Order createOrder(Member member, List<OrderItem> orderItemList) {
        Order order = new Order();
        order.setMember(member);
        //3. 상품을 주문한 회원의 정보를 세팅한다.
        for (OrderItem orderItem : orderItemList) {
            order.addOrderItem(orderItem);
            //4. 상품 페이지에서는 1개의 상품을 주문하지만, 장바구니 페이지에서는
            //한 번에 여러 개의 상품을 주문 할 수 있다.
            //따라서 여러 개의 주문 상품을 담을 수 있도록 리스트 형태로
            //파라미터 값을 받으며 주문 객체에 orderItem 객체를 추가한다.
        }
        order.setOrderStatus(OrderStatus.ORDER);
        //5. 주문 상태를 "ORDER"로 세팅한다.
        order.setOrderDate(LocalDateTime.now());
        //6. 현재 시간을 주문 시간으로 세팅한다.
        return order;
    }

    public int getTotalPrice() {
        //7. 총 주문 금액을 구하는 메소드이다.
        int totalPrice = 0;
        for (OrderItem orderItem : orderItems) {
            totalPrice += orderItem.getTotalPrice();
        }
        return totalPrice;
    }
}
