package ftn.kts.controller;

import ftn.kts.dto.SubcategoryDTO;
import ftn.kts.exceptions.UniqueConstraintViolationException;
import ftn.kts.service.SubcategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/subcategories")
public class SubcategoryController {
    private SubcategoryService service;

    @Autowired
    public SubcategoryController(SubcategoryService service)
    {
        this.service = service;
    }

    @GetMapping
    public ResponseEntity<List<SubcategoryDTO>> getAllSubcategories() {
        List<SubcategoryDTO> subcategories = service.getAllDTO();
        return new ResponseEntity<>(subcategories, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<SubcategoryDTO> getSubcategory(@PathVariable("id") long id) {
        return new ResponseEntity<>(service.getOneDTO(id), HttpStatus.OK);
    }
    
    @PostMapping
    public ResponseEntity<String> addSubcategory(@Valid @RequestBody SubcategoryDTO dto) throws UniqueConstraintViolationException {
        service.create(dto);
        return new ResponseEntity<>("Successfully added subcategory!", HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<SubcategoryDTO> updateSubcategory(@Valid @RequestBody SubcategoryDTO dto, @PathVariable long id) throws UniqueConstraintViolationException {
        SubcategoryDTO updated = service.update(dto, id);
        return new ResponseEntity<>(updated, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteSubcategory(@PathVariable("id") long id) {
        service.delete(id);
        return new ResponseEntity<>("Successfully deleted subcategory!", HttpStatus.OK);
    }
}
