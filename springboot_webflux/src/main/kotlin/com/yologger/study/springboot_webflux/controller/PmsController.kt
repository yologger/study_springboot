package com.yologger.study.springboot_webflux.controller

import com.yologger.study.springboot_webflux.dto.ProductDto
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono

// Annotation-based controller
@RestController
@RequestMapping("/api/pms")
class PmsController {

    @GetMapping("/product/{pid}")
    fun getProductById(@PathVariable pid: Long): Mono<ProductDto> {
        val product = ProductDto(uid = 1, pid = pid, title = "title1", "description1")
        return Mono.just(product)
    }


    @GetMapping("/user/{uid}/products")
    fun getProductsByUid(@PathVariable uid: Long): Flux<ProductDto> {
        val products = (1L..10L).map {
            ProductDto(uid = uid, pid = it, title = "title${it}", description = "description${it}")
        }
        return Flux.fromIterable(products)
    }
}