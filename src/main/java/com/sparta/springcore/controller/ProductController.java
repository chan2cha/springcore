package com.sparta.springcore.controller;

import com.sparta.springcore.model.Product;
import com.sparta.springcore.dto.ProductMypriceRequestDto;
import com.sparta.springcore.dto.ProductRequestDto;
import com.sparta.springcore.model.UserRoleEnum;
import com.sparta.springcore.security.UserDetailsImpl;
import com.sparta.springcore.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;


//@RequiredArgsConstructor // final로 선언된 멤버 변수를 자동으로 생성합니다.
@RestController
public class ProductController {
    private final ProductService service;

    @Autowired
    public ProductController(ProductService service){
        this.service = service;
    }


    ///신규 상품 등록
    @PostMapping("/api/products")
    public Product createProduct(@RequestBody ProductRequestDto requestDto, @AuthenticationPrincipal UserDetailsImpl userDetails) {
        // 로그인 되어 있는 회원 테이블으 ID
        Long userId = userDetails.getUser().getId();
        Product product  = service.createProduct(requestDto, userId);
        //응답 보내기
        return product;
    }
    // 설정 가격 변경
    @PutMapping("/api/products/{id}")
    public Long updateProduct(@PathVariable Long id, @RequestBody ProductMypriceRequestDto requestDto) {

        Product product = service.updateProduct(id,requestDto);
        // 응답 보내기
        return product.getId();
    }

    //로그인한 회원이 등록한 관심 상품 조회
    @GetMapping("/api/products")
    public List<Product> getProducts(@AuthenticationPrincipal UserDetailsImpl userDetails) {
        // 로그인 되어 있는 회원 테이블의 Id
        Long userId = userDetails.getUser().getId();

        return service.getProducts(userId);
    }

    //관리자용 상품 목록 조회
    @Secured(UserRoleEnum.Authority.ADMIN)
    @GetMapping("/api/admin/products")
    public List<Product> getAllProducts(@AuthenticationPrincipal UserDetailsImpl userDetails) {


        return service.getAllProducts();
    }

}
