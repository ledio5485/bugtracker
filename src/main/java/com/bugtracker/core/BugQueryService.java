package com.bugtracker.core;

import com.bugtracker.api.BugDTO;
import com.bugtracker.persistence.BugRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.core.convert.ConversionService;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class BugQueryService {
    private final BugRepository bugRepository;
    private final ConversionService conversionService;

    public Collection<BugDTO> findAll() {
        return bugRepository.findAll().stream()
                .map(entity -> conversionService.convert(entity, BugDTO.class))
                .collect(Collectors.toList());
    }

    public Optional<BugDTO> findById(UUID uuid) {
        return bugRepository.findById(uuid)
                .map(entity -> conversionService.convert(entity, BugDTO.class));
    }
}
