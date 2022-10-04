package com.bugtracker.core;

import com.bugtracker.persistence.Status;
import org.springframework.stereotype.Component;

@Component
public class StatusCalculator {

     public Status calculateStatus(com.bugtracker.api.Status statusDTO, Status statusEntity) {
        if (statusEntity == null) {
            return Status.NEW;
        }
        Status newStatus = Status.valueOf(statusDTO.name());
        if (statusEntity.nextStatusesAllowed().contains(newStatus)) {
            return newStatus;
        }
        throw new IllegalArgumentException(String.format("Status %s not allowed. Please choose one of %s", statusDTO, statusEntity.nextStatusesAllowed()));
    }
}
