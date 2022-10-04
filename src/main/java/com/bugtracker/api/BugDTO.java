package com.bugtracker.api;

import java.time.ZonedDateTime;
import java.util.UUID;

public record BugDTO(UUID id,
                     String title,
                     String description,
                     Status status,
                     UUID projectId,
                     UUID assignee,
                     ZonedDateTime createdAt,
                     ZonedDateTime lastModifiedAt) {
}
