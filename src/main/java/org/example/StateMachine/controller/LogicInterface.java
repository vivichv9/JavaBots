package org.example.StateMachine.controller;

import org.example.StateMachine.model.TransmittedData;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.objects.Update;

public interface LogicInterface {
    void processMessage(TransmittedData data, TelegramLongPollingBot bot, long chatId, Update update);
}
