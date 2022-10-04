package com.bugtracker.persistence;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.Collection;
import java.util.Set;
import java.util.stream.Stream;

import static com.bugtracker.persistence.Status.*;
import static org.assertj.core.api.Assertions.assertThat;

class StatusTest {

    @ParameterizedTest
    @MethodSource("provideExpectedAllowedStatuses")
    void shouldTestNextStatusesAllowed(Status status, Collection<Status> expectedAllowedStatuses) {
        assertThat(status.nextStatusesAllowed()).isEqualTo(expectedAllowedStatuses);
    }

    private static Stream<Arguments> provideExpectedAllowedStatuses() {
        return Stream.of(
                Arguments.of(NEW, Set.of(NEW, OPEN, REJECTED, DUPLICATED)),
                Arguments.of(OPEN, Set.of(NEW, OPEN, FIXED)),
                Arguments.of(FIXED, Set.of(OPEN, FIXED, CLOSED)),
                Arguments.of(REJECTED, Set.of(REJECTED)),
                Arguments.of(DUPLICATED, Set.of(DUPLICATED)),
                Arguments.of(CLOSED, Set.of(CLOSED, REOPENED)),
                Arguments.of(REOPENED, Set.of(NEW, FIXED, REOPENED))
        );
    }
}