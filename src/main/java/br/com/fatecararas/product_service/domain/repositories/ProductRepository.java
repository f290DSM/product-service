package br.com.fatecararas.product_service.domain.repositories;

import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.fatecararas.product_service.domain.entities.ProductEntity;

@Repository
public interface ProductRepository extends JpaRepository<ProductEntity, Integer> {
    List<ProductEntity> findByDescriptionContains(String term);
    Optional<ProductEntity> findByBarcode(String barcode);
}
