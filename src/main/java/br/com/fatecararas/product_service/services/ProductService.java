package br.com.fatecararas.product_service.services;

import java.util.Optional;

import java.util.List;
import org.springframework.stereotype.Service;

import br.com.fatecararas.product_service.domain.entities.ProductEntity;
import br.com.fatecararas.product_service.domain.repositories.ProductRepository;

@Service
public class ProductService {
    private final ProductRepository repository;

    public ProductService(ProductRepository repository) {
        this.repository = repository;
    }

    public ProductEntity findById(Integer id) {
        Optional<ProductEntity> optional = repository.findById(id);
        if (optional.isPresent()) return optional.get();
        return null;
    }

    public List<ProductEntity> getAll() {
        return repository.findAll();
    }
}
