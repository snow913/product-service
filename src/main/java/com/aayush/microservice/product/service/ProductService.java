package com.aayush.microservice.product.service;

import com.aayush.microservice.product.dto.ProductRequset;
import com.aayush.microservice.product.dto.ProductResponse;
import com.aayush.microservice.product.model.Product;
import com.aayush.microservice.product.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class ProductService {

    private final ProductRepository productRepository;

/*      @REquiredArgsConstrictor annotation from LOMBOk takes care of this boilerplate code
        public ProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }
 */

    public ProductResponse createProduct(ProductRequset productRequset) {
        Product product = Product.builder()
                .name(productRequset.name())
                .description(productRequset.discription())
                .price(productRequset.price())
                .build();
        productRepository.save(product);
        log.info("Product created");
        return new ProductResponse(product.getId(), product.getName(), product.getDescription(), product.getPrice());
    }

    public List<ProductResponse> getAllProducts() {
        return productRepository.findAll()
                .stream()
                .map(product -> new ProductResponse(product.getId(), product.getName(), product.getDescription(), product.getPrice()))
                .toList();
    }
}
