package com.locawork.webapi.controller;

import com.locawork.webapi.dao.entity.JobCategoryEntity;
import com.locawork.webapi.service.JobCategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/categories")
public class CategoryController {

    @Autowired
    private JobCategoryService jobCategoryService;

    @GetMapping
    public ResponseEntity<List<JobCategoryEntity>> getAll() {
        List<JobCategoryEntity> categories = jobCategoryService.findAll();

        if (categories != null && categories.isEmpty()) {
            return new ResponseEntity<List<JobCategoryEntity>>(HttpStatus.NO_CONTENT);
        }

        return new ResponseEntity<List<JobCategoryEntity>>(categories, HttpStatus.OK);
    }

    @RequestMapping(method = RequestMethod.POST)
    public ResponseEntity<Void> create(@RequestBody JobCategoryEntity categoryEntity, UriComponentsBuilder ucBuilder) {
        jobCategoryService.save(categoryEntity);

        HttpHeaders headers = new HttpHeaders();
        headers.setLocation(ucBuilder.path("/city/{id}").buildAndExpand(categoryEntity.getId()).toUri());
        return new ResponseEntity<Void>(headers, HttpStatus.CREATED);
    }

    @RequestMapping(value = "{id}", method = RequestMethod.PUT)
    public ResponseEntity<JobCategoryEntity> update(@PathVariable Integer id, @RequestBody JobCategoryEntity jobCategoryEntity) {

        if (!jobCategoryService.exists(id)) {
            return new ResponseEntity<JobCategoryEntity>(HttpStatus.NOT_FOUND);
        }
        jobCategoryService.update(jobCategoryEntity);
        return new ResponseEntity<JobCategoryEntity>(jobCategoryEntity, HttpStatus.OK);
    }

    @RequestMapping(value = "{id}", method = RequestMethod.DELETE)
    public ResponseEntity<Void> delete(@PathVariable("id") Integer id) {
        Optional<JobCategoryEntity> cityEntity = jobCategoryService.findById(id);

        if (!cityEntity.isPresent()) {
            return new ResponseEntity<Void>(HttpStatus.NOT_FOUND);
        }

        jobCategoryService.delete(id);
        return new ResponseEntity<Void>(HttpStatus.OK);
    }
}
