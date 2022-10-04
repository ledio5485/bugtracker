package com.bugtracker.core;

import com.bugtracker.api.BugDTO;
import com.bugtracker.persistence.BugEntity;
import com.bugtracker.persistence.BugRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.core.convert.ConversionService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
@Transactional
@RequiredArgsConstructor
public class BugCommandService {
    private final BugRepository bugRepository;
    private final ConversionService conversionService;
    private final StatusCalculator statusCalculator;

    public BugDTO create(BugDTO bugDTO) {
        BugEntity savedBugEntity = upsert(bugDTO, new BugEntity());
        return conversionService.convert(savedBugEntity, BugDTO.class);
    }

    public BugDTO update(BugDTO bugDTO) {
        BugEntity savedBugEntity = upsert(bugDTO, bugRepository.getReferenceById(bugDTO.id()));
        return conversionService.convert(savedBugEntity, BugDTO.class);
    }

    private BugEntity upsert(BugDTO bugDTO, BugEntity bugEntity) {
        bugEntity.setTitle(bugDTO.title());
        bugEntity.setDescription(bugDTO.description());
        bugEntity.setStatus(statusCalculator.calculateStatus(bugDTO.status(), bugEntity.getStatus()));
        bugEntity.setProjectId(bugDTO.projectId());
        bugEntity.setAssignee(bugDTO.assignee());

        return bugRepository.save(bugEntity);
    }

    public void deleteById(UUID uuid) {
        bugRepository.deleteById(uuid);
    }
}
