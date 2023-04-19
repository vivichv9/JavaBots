package org.example;

public class TgBotLogic {
    public void routeMessage(TgBot tgBot, long chatId, String fromUserText) {
        MultilevelMenu menu = new MultilevelMenu();
        menu.callMethodByName(tgBot, chatId, fromUserText);
    }
}
