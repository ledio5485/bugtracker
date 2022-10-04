package com.bugtracker.core;

import com.bugtracker.api.BugDTO;
import com.bugtracker.api.Status;
import com.bugtracker.persistence.BugEntity;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import java.time.Instant;
import java.time.ZoneId;
import java.time.ZonedDateTime;

@Component
public class BugEntityToBugDTOConverter implements Converter<BugEntity, BugDTO> {

    @Override
    public BugDTO convert(BugEntity entity) {
        return new BugDTO(
                entity.getId(),
                entity.getTitle(),
                entity.getDescription(),
                Status.valueOf(entity.getStatus().name()),
                entity.getProjectId(),
                entity.getAssignee(),
                toUTCDateTime(entity.getCreatedDate()),
                toUTCDateTime(entity.getLastModifiedDate())
        );
    }

    private ZonedDateTime toUTCDateTime(long epochMilli) {
        return ZonedDateTime.ofInstant(Instant.ofEpochMilli(epochMilli), ZoneId.of("UTC"));
    }
}
