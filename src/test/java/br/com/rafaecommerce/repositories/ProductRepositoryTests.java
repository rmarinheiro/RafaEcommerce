package br.com.rafaecommerce.repositories;

import br.com.rafaecommerce.entities.Producty;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.Optional;

@DataJpaTest
public class ProductRepositoryTests {
    @Autowired
    private  ProductyRepository repository;
    @Test
    public void deleteShouldDeleteObjectWhenIdExists() {
        long existById = 1L;
        repository.deleteById(existById);
        Optional<Producty> result =  repository.findById(existById);
        Assertions.assertFalse(result.isPresent());
    }
}
