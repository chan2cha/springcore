package com.sparta.springcore.service;

import com.sparta.springcore.dto.ProductMypriceRequestDto;
import com.sparta.springcore.dto.ProductRequestDto;
import com.sparta.springcore.model.Product;
import com.sparta.springcore.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;


import java.util.List;

@Component
public class ProductService {

//    private final ProductRepository repository ;
//
//    public ProductService(ProductRepository repository){
//        this.repository = repository;
//    }

        private final ProductRepository repository;
        public static final int MIN_MY_PRICE = 100;

        @Autowired
        public ProductService(ApplicationContext context) {
            // 1.'빈' 이름으로 가져오기
            ProductRepository repository = (ProductRepository) context.getBean("productRepository");
            // 2.'빈' 클래스 형식으로 가져오기
            // ProductRepository productRepository = context.getBean(ProductRepository.class);
            this.repository = repository;
        }

        // ...

    public Product createProduct(ProductRequestDto requestDto, Long userId) {
        // 요청받은 DTO로 DB에 저장할 객체 만들기
            Product product = new Product(requestDto, userId);
         repository.save(product);

        return product;
    }

    public Product updateProduct(Long id, ProductMypriceRequestDto requestDto)  {

            int myprice = requestDto.getMyprice();
            if(myprice<MIN_MY_PRICE){
                throw new IllegalArgumentException("유효하지 않은 관심 가격입니다. 최소 "+ MIN_MY_PRICE+" 원 이상으로 설정해 주세요.");
            }
            Product product = repository.findById(id).
                orElseThrow(() ->new NullPointerException("해당 아이디가 존재하지 않습니다.")) ;
        product.setMyprice(myprice);
        repository.save(product);

        return product;
    }
    // 회원 ID로 등록된 상품 조회
    public List<Product> getProducts(Long userId) {

       List<Product> products= repository.findAllByUserId(userId);

        return products;
    }

    public List<Product> getAllProducts() {
            return repository.findAll();
    }
}
