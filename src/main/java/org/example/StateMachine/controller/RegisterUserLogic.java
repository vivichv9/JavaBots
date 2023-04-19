package org.example.StateMachine.controller;

import org.example.StateMachine.model.DataStorage;
import org.example.StateMachine.model.DataStorageKeys;
import org.example.StateMachine.model.TransmittedData;
import org.example.StateMachine.model.User;
import org.example.StateMachine.util.SystemState;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.util.regex.Pattern;

public class RegisterUserLogic {
    public void processName(
            TransmittedData transmittedData,
            TelegramLongPollingBot bot,
            long chatId,
            Update update
    ) {
        String receivedMessage = update.getMessage().getText();
        String responseMessage = "";

        if (receivedMessage.length() >= 2 && receivedMessage.length() <= 32) {
            User user = (User) transmittedData.getDataStorage().get(DataStorageKeys.USER);
            user.setName(receivedMessage);

            responseMessage = "The name is recorded!\n Enter the age";
            transmittedData.setSystemState(SystemState.PROCESS_AGE);
        } else {
            responseMessage = "The name is not recorded!\n The length of the name is from 2 to 32 characters";
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

    public void processAge(
            TransmittedData data,
            TelegramLongPollingBot bot,
            long chatId,
            Update update
    ) {
        String receivedMessage = update.getMessage().getText();
        String responseMessage = "";

        if (Pattern.matches("[0-9]{0,3}", receivedMessage)) {
            User user = (User) data.getDataStorage().get(DataStorageKeys.USER);
            user.setAge(Integer.parseInt(receivedMessage));

            responseMessage = "The age is recorded!\n Enter the city";
            data.setSystemState(SystemState.PROCESS_CITY);
        } else {
            responseMessage = "The age is not recorded!\n The length of the age is from 1 to 3 digits and and contain only numbers";
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

    public void processCity(
            TransmittedData data,
            TelegramLongPollingBot bot,
            long chatId,
            Update update
    ) {
        String receivedMessage = update.getMessage().getText();
        String responseMessage = "";

        if (Pattern.matches("[A-Z]{1}[a-z]+", receivedMessage)) {
            User user = (User) data.getDataStorage().get(DataStorageKeys.USER);
            user.setCity(receivedMessage);

            responseMessage = "The city is recorded!\nCheck your data\n" + user + "\n For new reg press /start";
            data.setSystemState(SystemState.PROCESS_START);
        } else {
            responseMessage = "The city is not recorded!";
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
}
