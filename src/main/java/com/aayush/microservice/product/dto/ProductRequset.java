package com.aayush.microservice.product.dto;

import java.math.BigDecimal;

public record ProductRequset(String id, String name, String discription, BigDecimal price) {
}
