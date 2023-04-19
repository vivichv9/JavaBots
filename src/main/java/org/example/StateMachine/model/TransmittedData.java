package org.example.StateMachine.model;

import org.example.StateMachine.model.DataStorage;
import org.example.StateMachine.util.SystemState;

public class TransmittedData {
    private DataStorage dataStorage;
    private SystemState systemState;

    public TransmittedData(DataStorage dataStorage, SystemState systemState) {
        this.dataStorage = dataStorage;
        this.systemState = systemState;
    }

    public DataStorage getDataStorage() {
        return dataStorage;
    }

    public SystemState getSystemState() {
        return systemState;
    }

    public void setSystemState(SystemState systemState) {
        this.systemState = systemState;
    }
}
