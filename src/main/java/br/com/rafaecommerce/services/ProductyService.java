package br.com.rafaecommerce.services;

import br.com.rafaecommerce.dto.CategoryDTO;
import br.com.rafaecommerce.dto.ProductDTO;
import br.com.rafaecommerce.entities.Category;
import br.com.rafaecommerce.entities.Producty;
import br.com.rafaecommerce.repositories.CategoryRepository;
import br.com.rafaecommerce.repositories.ProductyRepository;
import br.com.rafaecommerce.services.exceptions.DataBaseException;
import br.com.rafaecommerce.services.exceptions.ResourceNotFoundException;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
public class ProductyService {

    @Autowired
    private ProductyRepository productyRepository;

    @Autowired
    private CategoryRepository categoryRepository;

    @Transactional(readOnly = true)
    public Page<ProductDTO> findAllPaged(Pageable pageable) {
        Page<Producty> list = productyRepository.findAll(pageable);
        return list.map(x -> new ProductDTO(x));
    }

    @Transactional(readOnly = true)
    public ProductDTO findById(Long id) {
        Optional<Producty> obj = productyRepository.findById(id);
        Producty entity = obj.orElseThrow(() -> new ResourceNotFoundException("Entity Not Found"));
        ProductDTO dto = new ProductDTO(entity, entity.getCategories());
        return dto;
    }

    @Transactional
    public ProductDTO insert(ProductDTO dto) {
        Producty entity = new Producty();
        copyDTOToEntity(dto, entity);
        entity.setName(dto.getName());
        entity = productyRepository.save(entity);
        return new ProductDTO(entity);
    }


    @Transactional
    public ProductDTO update(ProductDTO dto, Long id) {
        try {
            Producty entity = productyRepository.getReferenceById(id);
            copyDTOToEntity(dto, entity);
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
    private void copyDTOToEntity(ProductDTO dto, Producty entity) {
        entity.setName(dto.getName());
        entity.setDescription(dto.getDescription());
        entity.setPrice(dto.getPrice());
        entity.setImgUrl(dto.getImgUrl());
        entity.setDate(dto.getDate());

        entity.getCategories().clear();
        for (CategoryDTO catDTO : dto.getCategories()){
            Category category = categoryRepository.getReferenceById(catDTO.getId());
            entity.getCategories().add(category);
        }
    }
}
