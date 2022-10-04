package com.bugtracker.api;

import com.bugtracker.core.BugCommandService;
import com.bugtracker.core.BugQueryService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collection;
import java.util.UUID;

@RestController
@RequestMapping("bugs")
@RequiredArgsConstructor
public class BugResource implements Resource<BugDTO, UUID> {
    private final BugQueryService bugQueryService;
    private final BugCommandService bugCommandService;

    @Override
    public ResponseEntity<Collection<BugDTO>> findAll() {
        return ResponseEntity.ok(bugQueryService.findAll());
    }

    @Override
    public ResponseEntity<BugDTO> findById(UUID id) {
        return ResponseEntity.of(bugQueryService.findById(id));
    }

    @Override
    public ResponseEntity<BugDTO> create(BugDTO bugDTO) {
        return ResponseEntity.status(HttpStatus.CREATED).body(bugCommandService.create(bugDTO));
    }

    @Override
    public ResponseEntity<BugDTO> update(BugDTO bugDTO) {
        return ResponseEntity.ok(bugCommandService.update(bugDTO));
    }

    @Override
    public ResponseEntity<Void> deleteById(UUID id) {
        bugCommandService.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
