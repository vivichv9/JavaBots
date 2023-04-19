package org.example;

import org.example.Config.BotConfig;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.objects.Update;

public class TgBot extends TelegramLongPollingBot {
    private final TgBotLogic tgBotLogic;

    public TgBot() {
        tgBotLogic = new TgBotLogic();
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
        if (update.hasMessage() && update.getMessage().hasText()) {
            long chatId = update.getMessage().getChatId();
            String fromUserText = update.getMessage().getText();

            tgBotLogic.routeMessage(this, chatId, fromUserText);
        } else if (update.hasCallbackQuery()) {
            long chatId = update.getCallbackQuery().getMessage().getChatId();
            String fromUserText = update.getCallbackQuery().getData();

            tgBotLogic.routeMessage(this, chatId, fromUserText);
        }
    }
}
