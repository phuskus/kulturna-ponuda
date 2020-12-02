package ftn.kts.service;

import ftn.kts.repository.SubcategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SubcategoryService {

    private SubcategoryRepository subcategoryRepository;

    @Autowired
    public SubcategoryService(SubcategoryRepository subcategoryRepository) {
        this.subcategoryRepository = subcategoryRepository;
    }
}
