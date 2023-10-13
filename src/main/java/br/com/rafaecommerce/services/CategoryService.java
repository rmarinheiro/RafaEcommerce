package br.com.rafaecommerce.services;

import br.com.rafaecommerce.dto.CategoryDTO;
import br.com.rafaecommerce.entities.Category;
import br.com.rafaecommerce.repositories.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CategoryService {

    @Autowired
    private CategoryRepository categoryRepository;
    @Transactional(readOnly = true)
    public List<CategoryDTO> findAll() {
        List<Category> list = categoryRepository.findAll();
        List<CategoryDTO> lisDTO = list.stream().map(x -> new CategoryDTO(x)).collect(Collectors.toList());
        return lisDTO;
    }
}