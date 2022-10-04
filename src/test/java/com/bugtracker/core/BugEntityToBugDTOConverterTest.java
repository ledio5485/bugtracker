package com.bugtracker.core;

import com.bugtracker.api.BugDTO;
import com.bugtracker.persistence.BugEntity;
import com.bugtracker.persistence.Status;
import org.junit.jupiter.api.Test;

import java.time.Instant;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

class BugEntityToBugDTOConverterTest {
    private final BugEntityToBugDTOConverter converter = new BugEntityToBugDTOConverter();

    @Test
    void shouldConvertEntityToDTO() {
        UUID id = UUID.randomUUID();
        UUID projectId = UUID.randomUUID();
        UUID assigneeId = UUID.randomUUID();
        long timestamp = System.currentTimeMillis();

        BugEntity entity = new BugEntity();
        entity.setId(id);
        entity.setTitle("title");
        entity.setDescription("description");
        entity.setStatus(Status.NEW);
        entity.setProjectId(projectId);
        entity.setAssignee(assigneeId);
        entity.setCreatedDate(timestamp);
        entity.setLastModifiedDate(timestamp);
        BugDTO actual = converter.convert(entity);

        ZonedDateTime dateTime = ZonedDateTime.ofInstant(Instant.ofEpochMilli(timestamp), ZoneId.of("UTC"));
        BugDTO expected = new BugDTO(id, "title", "description", com.bugtracker.api.Status.NEW, projectId, assigneeId, dateTime, dateTime);
        assertThat(actual).isEqualTo(expected);
    }
}