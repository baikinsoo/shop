package com.shop.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CartDetailDto {

    private Long cartItemId; //장바구니 상품 아이디

    private String itemNm;

    private int price;

    private int count;

    private String imgUrl;

    public CartDetailDto(Long cartItemId, String itemNm, int price, int count, String imgUrl) {
//        1. 장바구니 페이지에 전달할 데이터를 생성자의 파라미터로 만들어준다.
        this.cartItemId = cartItemId;
        this.itemNm = itemNm;
        this.price = price;
        this.count = count;
        this.imgUrl = imgUrl;
    }
}
