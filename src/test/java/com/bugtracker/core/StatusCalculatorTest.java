package com.bugtracker.core;

import com.bugtracker.persistence.Status;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

class StatusCalculatorTest {
    private final StatusCalculator statusCalculator = new StatusCalculator();

    @Test
    void shouldReturnNEW_whenEntityStatusIsNull() {
        Status actual = statusCalculator.calculateStatus(com.bugtracker.api.Status.NEW, null);
        assertThat(actual).isEqualTo(Status.NEW);
    }

    @Test
    void shouldReturnDTOStatus_whenItIsAllowed() {
        Status actual = statusCalculator.calculateStatus(com.bugtracker.api.Status.OPEN, Status.NEW);
        assertThat(actual).isEqualTo(Status.OPEN);
    }

    @Test
    void shouldThrowIllegalArgumentException_WhenItIsNotAllowed() {
        IllegalArgumentException thrown = assertThrows(IllegalArgumentException.class,
                () -> statusCalculator.calculateStatus(com.bugtracker.api.Status.OPEN, Status.REJECTED));

        assertThat(thrown.getMessage()).isEqualTo(String.format("Status %s not allowed. Please choose one of %s", Status.OPEN, Status.REJECTED.nextStatusesAllowed()));

    }
}