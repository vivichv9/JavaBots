package org.example.StateMachine.controller;

import org.example.StateMachine.util.SystemState;
import org.example.StateMachine.model.TransmittedData;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.util.HashMap;
import java.util.Map;

public class BotLogic {
    private Map<SystemState, LogicInterface> methods;
    private RegisterUserLogic registerUserLogic;

    public BotLogic() {
        methods = new HashMap<>();
        registerUserLogic = new RegisterUserLogic();

        methods.put(SystemState.PROCESS_START, this::processStart);
        methods.put(SystemState.PROCESS_NAME, registerUserLogic::processName);
        methods.put(SystemState.PROCESS_AGE, registerUserLogic::processAge);
        methods.put(SystemState.PROCESS_CITY, registerUserLogic::processCity);
    }

    private void processStart(TransmittedData data, TelegramLongPollingBot bot, long chatId, Update update) {
        String receivedMessage = update.getMessage().getText();
        String responseMessage = "";

        if ("/start".equals(receivedMessage)) {
            responseMessage = "Excellent, let's go start registration!\nPlease, enter your name: ";
            data.setSystemState(SystemState.PROCESS_NAME);
        } else {
            responseMessage = "Command not found!";
        }

        SendMessage message = new SendMessage();
        message.setChatId(chatId);
        message.setText(responseMessage);

        try {
            bot.execute(message);
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }

    public void processMessage(TransmittedData transmittedData, TelegramLongPollingBot bot, long chatId, Update update) {
        methods.get(transmittedData.getSystemState()).processMessage(transmittedData, bot, chatId, update);
    }
}
