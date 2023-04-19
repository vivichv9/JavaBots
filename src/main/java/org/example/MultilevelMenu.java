package org.example;

import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.util.*;
import java.util.concurrent.ThreadLocalRandom;

public class MultilevelMenu {
    private Map<String, SendBotMessage> messagesMap;
    private CommandHistoryControl commandHistory;

    public MultilevelMenu() {
        messagesMap = new HashMap<>();
        commandHistory = new CommandHistoryControl();

        messagesMap.put("menu", this::sendMainMenu);
        messagesMap.put("random", this::sendRandomNumber);
        messagesMap.put("bebra", this::sendBebra);
        messagesMap.put("/start", this::sendStartMessage);
        messagesMap.put("back", this::sendPreviousCommand);
    }

    public void callMethodByName(TgBot tgBot, long chatId, String textFromUser) {
        if (messagesMap.containsKey(textFromUser)) {
            messagesMap.get(textFromUser).editMessage(tgBot, chatId);
            commandHistory.addCommandToHistory(textFromUser);
        } else {
            sendMessage(tgBot, chatId, "Unknown Command!");
        }
    }

    private void addMarkup(InlineKeyboardMarkup markup,
                           List<List<InlineKeyboardButton>> rowList,
                           String text,
                           String callBackData) {
        InlineKeyboardButton inlineKeyboardButton = new InlineKeyboardButton();

        inlineKeyboardButton.setText(text);
        inlineKeyboardButton.setCallbackData(callBackData);

        List<InlineKeyboardButton> keyboardButtonsRow1 = new ArrayList<>();
        keyboardButtonsRow1.add(inlineKeyboardButton);

        rowList.add(keyboardButtonsRow1);
        markup.setKeyboard(rowList);
    }

    private void sendBebra(TgBot tgBot, long chatId) {
        sendMessage(tgBot, chatId, "bebra");
    }

    private void sendStartMessage(TgBot tgBot, long chatId) {
        InlineKeyboardMarkup inlineKeyboardMarkup = new InlineKeyboardMarkup();
        List<List<InlineKeyboardButton>> rowList = new ArrayList<>();

        addMarkup(inlineKeyboardMarkup, rowList, "Open menu", "menu");

        String toUserText = "Program for display phones list! press menu";
        sendMessage(tgBot, chatId, toUserText, inlineKeyboardMarkup);
    }

    private void sendRandomNumber(TgBot tgBot, long chatId) {
        InlineKeyboardMarkup inlineKeyboardMarkup = new InlineKeyboardMarkup();
        List<List<InlineKeyboardButton>> rowList = new ArrayList<>();

        addMarkup(inlineKeyboardMarkup, rowList, "Regenerate number", "random");
        addMarkup(inlineKeyboardMarkup, rowList, "Go back", "back");

        Random random = ThreadLocalRandom.current();
        int toUserText;
        toUserText = random.nextInt(100);

        sendMessage(tgBot, chatId, "Random number: " + toUserText, inlineKeyboardMarkup);
    }

    private void sendMainMenu(TgBot tgBot, long chatID) {
        InlineKeyboardMarkup inlineKeyboardMarkup = new InlineKeyboardMarkup();
        List<List<InlineKeyboardButton>> rowList = new ArrayList<>();

        addMarkup(inlineKeyboardMarkup, rowList, "Generate random number", "random");
        addMarkup(inlineKeyboardMarkup, rowList, "Go back", "back");

        String toUserText = "Choose one of this commands";
        sendMessage(tgBot, chatID, toUserText, inlineKeyboardMarkup);
    }

    private void sendMessage(TgBot tgBot, long chatId, String toUserText) {
        SendMessage message = new SendMessage();
        message.setChatId(chatId);
        message.setText(toUserText);

        try {
            tgBot.execute(message);
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }

    private void sendPreviousCommand(TgBot tgBot, long chatId) {
        String fromUserText = commandHistory.getPreviousCommand();
        callMethodByName(tgBot, chatId, fromUserText);
    }

    private void sendMessage(TgBot tgBot, long chatId, String toUserText, InlineKeyboardMarkup markup) {
        SendMessage message = new SendMessage();
        message.setChatId(chatId);
        message.setText(toUserText);
        message.setReplyMarkup(markup);

        try {
            tgBot.execute(message);
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }

}
