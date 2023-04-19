package org.example;

import java.util.Stack;

public class CommandHistoryControl {
    private final Stack<String> commandHistory;

    public CommandHistoryControl() {
        commandHistory = new Stack<>();
        commandHistory.push("/start");
    }

    public void addCommandToHistory(String command) {
        if (!commandHistory.empty()) {
            String lastCommand = commandHistory.peek();

            if (!command.equals(lastCommand)) {
                commandHistory.push(command);
            }
        } else {
            commandHistory.push("/start");
        }
    }

    public String getPreviousCommand() {
        if (commandHistory.empty()) {
            commandHistory.push("/start");
            return "/start";
        } else {
            commandHistory.pop();
            return !commandHistory.empty() ? commandHistory.peek() : "/start";
        }
    }
}
