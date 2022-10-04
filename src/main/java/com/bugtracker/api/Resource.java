package com.bugtracker.api;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Collection;

public interface Resource<T, ID> {
    @GetMapping
    default ResponseEntity<Collection<T>> findAll() {
        throw new UnsupportedOperationException("Not implemented yet");
    }

    @GetMapping("{id}")
    default ResponseEntity<T> findById(@PathVariable ID id) {
        throw new UnsupportedOperationException("Not implemented yet");
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    default ResponseEntity<T> create(@RequestBody @Valid T t) {
        throw new UnsupportedOperationException("Not implemented yet");
    }

    @PutMapping
    default ResponseEntity<T> update(@RequestBody @Valid T t) {
        throw new UnsupportedOperationException("Not implemented yet");
    }

    @DeleteMapping("{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    default ResponseEntity<Void> deleteById(@PathVariable ID id) {
        throw new UnsupportedOperationException("Not implemented yet");
    }
}
