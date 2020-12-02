package ftn.kts.controller;

import ftn.kts.dto.SubcategoryDTO;
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
        List<SubcategoryDTO> subcategories = service.getAll();
        return new ResponseEntity<>(subcategories, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<SubcategoryDTO> getSubcategory(@PathVariable("id") long id) {
        try {
            return new ResponseEntity<>(service.getOne(id), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }
    
    @PostMapping
    public ResponseEntity<Object> addSubcategory(@Valid @RequestBody SubcategoryDTO dto) {
        try {
            service.create(dto);
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<Object> updateSubcategory(@Valid @RequestBody SubcategoryDTO dto, @PathVariable long id) {
        try {
            SubcategoryDTO updated = service.update(dto, id);
            return new ResponseEntity<>(updated, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<SubcategoryDTO> deleteSubcategory(@PathVariable("id") long id) {
        try {
            service.delete(id);
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }
}
