package org.example.StateMachine;

import org.example.StateMachine.controller.BotLogic;
import org.example.StateMachine.util.SystemState;
import org.example.StateMachine.model.TransmittedData;
import org.example.StateMachine.model.DataStorage;
import org.example.StateMachine.model.DataStorageKeys;
import org.example.StateMachine.model.User;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.objects.Update;

import java.util.HashMap;
import java.util.Map;

public class ChatsRouter {
    private Map<Long, TransmittedData> chats;
    private BotLogic botLogic;

    public ChatsRouter() {
        chats = new HashMap<>();
        botLogic = new BotLogic();
    }

    public void processMessage(TelegramLongPollingBot bot, Update update) {
        long chatId = -1;

        if (update.hasMessage() && update.getMessage().hasText()) {
            chatId = update.getMessage().getChatId();
        } else if (update.hasCallbackQuery()) {
            chatId = update.getCallbackQuery().getMessage().getChatId();
        }

        if (chats.get(chatId) == null)   {
            DataStorage dataStorage = new DataStorage();
            dataStorage.add(DataStorageKeys.USER, new User());

            TransmittedData transmittedData = new TransmittedData(dataStorage, SystemState.PROCESS_START);
            chats.put(chatId, transmittedData);
        }

        TransmittedData transmittedData = chats.get(chatId);
        botLogic.processMessage(transmittedData, bot, chatId, update);
    }
}
