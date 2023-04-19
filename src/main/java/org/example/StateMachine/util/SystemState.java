package org.example.StateMachine.util;

public enum SystemState {
    PROCESS_START("PROCESS_START"),
    PROCESS_NAME("PROCESS_NAME"),
    PROCESS_AGE("PROCESS_AGE"),
    PROCESS_CITY("PROCESS_CITY");

    private String value;

    SystemState(String value) {
        this.value = value;
    }

    @Override
    public String toString() {
        return value;
    }
}
