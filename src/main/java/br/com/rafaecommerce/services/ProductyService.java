package br.com.rafaecommerce.services;

import br.com.rafaecommerce.dto.ProductDTO;
import br.com.rafaecommerce.entities.Producty;
import br.com.rafaecommerce.repositories.ProductyRepository;
import br.com.rafaecommerce.services.exceptions.DataBaseException;
import br.com.rafaecommerce.services.exceptions.ResourceNotFoundException;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
public class ProductyService {

    @Autowired
    private ProductyRepository productyRepository;

    @Transactional(readOnly = true)
    public Page<ProductDTO> findAllPaged(PageRequest pageRequest) {
        Page<Producty> list = productyRepository.findAll(pageRequest);
        return list.map(x -> new ProductDTO(x));
    }

    @Transactional(readOnly = true)
    public ProductDTO findById(Long id) {
        Optional<Producty> obj = productyRepository.findById(id);
        Producty entity = obj.orElseThrow(() -> new ResourceNotFoundException("Entity Not Found"));
        ProductDTO dto = new ProductDTO(entity,entity.getCategories());
        return dto;
    }

    @Transactional
    public ProductDTO insert(ProductDTO dto) {
        Producty entity = new Producty();
        entity.setName(dto.getName());
        entity = productyRepository.save(entity);
        return new ProductDTO(entity);
    }

    @Transactional
    public ProductDTO update(ProductDTO dto, Long id) {
        try {
            Producty entity = productyRepository.getReferenceById(id);
            entity.setName(dto.getName());
            entity = productyRepository.save(entity);
            return new ProductDTO(entity);
        } catch (EntityNotFoundException e) {
            throw new ResourceNotFoundException("Id not found");
        }
    }

    @Transactional(propagation = Propagation.SUPPORTS)
    public void delete(Long id) {
        if (!productyRepository.existsById(id)) {
            throw new ResourceNotFoundException("Recurso não encontrado");
        }
        try {
            productyRepository.deleteById(id);
        } catch (DataIntegrityViolationException e) {
            throw new DataBaseException("Falha de integridade referencial");
        }
    }
}
