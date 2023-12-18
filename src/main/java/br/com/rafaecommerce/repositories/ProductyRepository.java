package br.com.rafaecommerce.repositories;

import br.com.rafaecommerce.entities.Category;
import br.com.rafaecommerce.entities.Producty;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductyRepository extends JpaRepository<Producty,Long> {
}
