package com.elakeed.MyEcommerce.controller;

import com.elakeed.MyEcommerce.model.TagName;
import com.elakeed.MyEcommerce.repository.TagNameDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@CrossOrigin("*")
@RestController
@RequestMapping(path = "/tagName")
public class TagNameController {
    private TagNameDAO tagNameDAO;

    @Autowired
    public TagNameController(TagNameDAO tagNameDAO) {
        this.tagNameDAO = tagNameDAO;
    }

    @GetMapping
    public ResponseEntity<List<TagName>> getAllTagNames() {
        return ResponseEntity.ok(tagNameDAO.findAll());
    }

    @GetMapping(path = "/{id}")
    public ResponseEntity<TagName> getTagNameById(@PathVariable("id") Long id) {
        TagName tagName=tagNameDAO.findById(id).orElse(null);
        if(tagName!=null) {
            return ResponseEntity.ok(tagName);
        }
        return ResponseEntity.notFound().header("Message","Id Not Found").build();
    }

    @PostMapping
    public ResponseEntity<TagName> addTagName(@RequestBody TagName tagName) {
        URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(tagNameDAO.save(tagName).getTagNameId()).toUri();
        return ResponseEntity.created(location).build();
    }

    @PutMapping(path = "/{id}")
    public ResponseEntity<TagName> updateTagName(@PathVariable("id") Long id, @RequestBody TagName tagName) {
        TagName updatedTagName = tagNameDAO.findById(id).orElse(null);
        if (updatedTagName != null) {
            updatedTagName.setTagName(tagName.getTagName());
            tagNameDAO.save(updatedTagName);
            return ResponseEntity.ok(updatedTagName);
        }
        return ResponseEntity.notFound().header("Message","Id Not Found").build();

    }

    @DeleteMapping(path = "/{id}")
    public ResponseEntity<?> deleteTagNameById(@PathVariable("id") Long id) {
        TagName tagName=tagNameDAO.findById(id).orElse(null);
        if(tagName!=null) {
            if(tagName.getProductList().size()==0) {
                tagNameDAO.deleteById(id);
                return ResponseEntity.ok().build();
            }else{
                return ResponseEntity.badRequest().header("Message","Can't delete this record because some products depends on it").build();
            }
        }
        return ResponseEntity.notFound().header("Message","Id Not Found").build();
    }
}
