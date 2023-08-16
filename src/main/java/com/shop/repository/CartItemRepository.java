package com.shop.repository;

import com.shop.dto.CartDetailDto;
import com.shop.entity.CartItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

//장바구니에 들어갈 상품을 저장하거나 조회하기 위해서 CartItemRepository 인터페이스를 생성한다.
public interface CartItemRepository extends JpaRepository<CartItem, Long> {

    CartItem findByCartIdAndItemId(Long cartId, Long itemId);
//    카트 아이디와 상품 아이디를 이용해서 상품이 장바구니에 들어있는지 조회한다.

    @Query("select new com.shop.dto.CartDetailDto(ci.id, i.itemNm, i.price, ci.count, im.imgUrl) " +
//            1. CartDetailDto의 생성자를 이용하여 DTO를 반환할 때는 "new com.shop.dto.CartDetailDto(ci.id,
//            i.itemNm, i.price, ci.count, im.imgUrl)"처럼 new 키워드와 해당 DTO 패키지, 클래스명을 적어준다.
//            또한, 생성자의 파라미터 순서는 DTO 클래스에 명시한 순으로 넣어주어야 한다.
            "from CartItem ci, ItemImg im " +
            "join ci.item i " +
            "where ci.cart.id = :cartId " +
            "and im.item.id = ci.item.id " +
            "and im.repImgYn = 'Y' " +
//            2, 3. 장바구니에 담겨있는 상품의 대표 이미지만 가지고 오도록 조건문을 작성한다.
            "order by ci.regTime desc"
    )
    List<CartDetailDto> findCartDetailDtoList(Long cartId);
}