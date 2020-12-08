package ftn.kts.controller;

import ftn.kts.dto.CategoryDTO;
import ftn.kts.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/categories")
public class CategoryController {

    private CategoryService service;

    @Autowired
    public CategoryController(CategoryService service){
        this.service = service;
    }

    @GetMapping
    public ResponseEntity<List<CategoryDTO>> getAllCategories() {
        List<CategoryDTO> categories = service.getAllDTO();
        return new ResponseEntity<>(categories, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<CategoryDTO> getCategory(@PathVariable("id") long id) {
        return new ResponseEntity<>(service.getOneDTO(id), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<String> addCategory(@Valid @RequestBody CategoryDTO dto) {
        service.create(dto);
        return new ResponseEntity<>("Successfully added category!", HttpStatus.OK);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Object> updateCategory(@Valid @RequestBody CategoryDTO dto, @PathVariable long id) {
        CategoryDTO updated = service.update(dto, id);
        return new ResponseEntity<>(updated, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteCategory(@PathVariable("id") long id) {
        service.delete(id);
        return new ResponseEntity<>("Successfully deleted category!", HttpStatus.OK);
    }
}
