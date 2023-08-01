package com.shop.entity;

import com.shop.constant.ItemSellStatus;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name="item")
//entity 설정 및 @Table 어노테이션을 통해 어떤 테이블과 매핑될지를 지정한다.
//item 테이블과 매핑되도록 name을 item으로 지정한다.
@Getter
@Setter
@ToString
public class Item {

    @Id
    @Column(name="item_id")
    @GeneratedValue(strategy = GenerationType.AUTO)
    //entity로 선언한 클래스는 반드시 기본키를 가져야 한다.
    //기본키가 되는 멤버변수에 @Id 어노테이션을 붙여준다. 테이블에 매핑될 컬럼의 이름을 @Colum 어노테이션을 통해 설정한다.
    //item 클래스의 id 변수와 item테이블의 item_id 컬럼이 매핑되도록 한다.
    //마지막으로 @GeneratedValue 어노테이션을 통해 기본키 생성 전략을 AUTO로 지정한다.
    private Long id;

    @Column(nullable = false, length = 50)
    //nullable을 통해 항상 값이 있어야 하는 필드는 not null 설정을 한다.
    //String 필드는 default 값으로 255가 설정되어 있다.
    private String itemNm;

    @Column(name="price", nullable = false)
    private int price;

    @Column(nullable = false)
    private int stockNumber;

    @Lob
    @Column(nullable = false)
    private String itemDetail;

    @Enumerated
    private ItemSellStatus itemSellStatus;

    private LocalDateTime regTime;

    private LocalDateTime updateTime;
}
