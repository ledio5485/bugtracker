package com.bugtracker.persistence;

import java.util.Collection;
import java.util.Collections;
import java.util.Set;

public enum Status {
    NEW {
        @Override
        public Collection<Status> nextStatusesAllowed() {
            return Set.of(NEW, OPEN, REJECTED, DUPLICATED);
        }
    },
    OPEN {
        @Override
        public Collection<Status> nextStatusesAllowed() {
            return Set.of(NEW, OPEN, FIXED);
        }
    },
    FIXED {
        @Override
        public Collection<Status> nextStatusesAllowed() {
            return Set.of(OPEN, FIXED, CLOSED);
        }
    },
    REJECTED,
    DUPLICATED,
    CLOSED {
        @Override
        public Collection<Status> nextStatusesAllowed() {
            return Set.of(CLOSED, REOPENED);
        }
    },
    REOPENED {
        @Override
        public Collection<Status> nextStatusesAllowed() {
            return Set.of(NEW, FIXED, REOPENED);
        }
    };

    public Collection<Status> nextStatusesAllowed() {
        return Collections.singleton(this);
    }
}