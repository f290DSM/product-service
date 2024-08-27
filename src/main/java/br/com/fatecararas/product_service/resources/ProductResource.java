package br.com.fatecararas.product_service.resources;

import java.net.URI;
import java.util.Objects;
import java.util.Optional;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import br.com.fatecararas.product_service.domain.entities.ProductEntity;
import br.com.fatecararas.product_service.domain.repositories.ProductRepository;
import br.com.fatecararas.product_service.services.ProductService;

@RestController
@RequestMapping("/products")
public class ProductResource {

    @Autowired
    private ProductService service;
    @Autowired
    private ProductRepository repository;

    @GetMapping("product/{id}")
    public ResponseEntity<ProductEntity> findById(@PathVariable("id") Integer id) {

        ProductEntity entity = service.findById(id);

        if (Objects.isNull(entity))
            ResponseEntity.notFound().build();

        return ResponseEntity.ok().body(entity);
    }

    @GetMapping("/all")
    public ResponseEntity<Page<ProductEntity>> getAll(@RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "100") int size) {

        // Criar objeto para ordenacao de resultados com base me descricao
        PageRequest pageable = PageRequest.of(page, size, Sort.Direction.ASC, "description");

        Page<ProductEntity> products = repository.findAll(pageable);

        if (products.isEmpty())
            return ResponseEntity
                    .status(HttpStatus.NOT_FOUND)
                    .build();

        return ResponseEntity.ok().body(products);
    }

    @PostMapping
    @ResponseStatus(code = HttpStatus.CREATED)
    public ResponseEntity<Void> createNewProduct(@RequestBody ProductEntity product) {
        ProductEntity p = repository.save(product);

        URI uri = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(p.getId())
                .toUri();

        return ResponseEntity.created(uri).build();
    }

    //TODO: Criar busca por termos na descrição.
    @GetMapping("/find/{term}")
    public List<ProductEntity> findByDescription(@PathVariable("term") String term) {
        return repository.findByDescriptionContains(term);
    }   

    //TODO: Remover produto por id.
    @DeleteMapping("/remove/{id}")
    public void deleteProductById(@PathVariable("id") Integer id) {
        repository.deleteById(id);
    }

    //TODO: Atualizar produto.
    @PutMapping
    public ResponseEntity<?> update(@RequestBody ProductEntity productEntity) {
        Optional<ProductEntity> optional = repository.findById(productEntity.getId());

        if (optional.isEmpty()) return ResponseEntity.notFound().build();

        ProductEntity product = optional.get();
        product.setBarcode(productEntity.getBarcode());
        product.setDescription(productEntity.getDescription());
        product.setPrice(productEntity.getPrice());

        repository.save(product);

        return ResponseEntity.ok().body(product);
    }

    //TODO: Cotação de produto em moeda estrangeira.
}
