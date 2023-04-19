package org.example;

import org.example.Config.BotConfig;
import org.example.StateMachine.ChatsRouter;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.objects.Update;

public class TgStateMachine extends TelegramLongPollingBot {

    private ChatsRouter chatsRouter;

    public TgStateMachine() {
        chatsRouter = new ChatsRouter();
    }

    @Override
    public String getBotUsername() {
        return BotConfig.getName();
    }

    @Override
    public String getBotToken() {
        return BotConfig.getToken();
    }

    @Override
    public void onUpdateReceived(Update update) {
        chatsRouter.processMessage(this, update);
    }
}
