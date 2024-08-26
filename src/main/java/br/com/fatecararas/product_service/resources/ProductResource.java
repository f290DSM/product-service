package br.com.fatecararas.product_service.resources;

import java.util.Objects;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.fatecararas.product_service.domain.entities.ProductEntity;
import br.com.fatecararas.product_service.services.ProductService;

@RestController
@RequestMapping("/product")
public class ProductResource {

    @Autowired
    private ProductService service;

    @GetMapping("/{id}")
    public ResponseEntity<ProductEntity> findById(@PathVariable("id") Integer id) {

        ProductEntity entity = service.findById(id);

        if (Objects.isNull(entity)) ResponseEntity.notFound().build();

        return ResponseEntity.ok().body(entity);
    }
}
