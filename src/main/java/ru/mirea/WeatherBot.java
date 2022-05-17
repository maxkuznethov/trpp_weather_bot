package ru.mirea;

import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.updatesreceivers.DefaultBotSession;

public class WeatherBot {
    public static void main(String args[]){
        try{
            TelegramBotsApi botApi=new TelegramBotsApi(DefaultBotSession.class);
            botApi.registerBot(new Bot());

        }catch(TelegramApiException e) {
            e.printStackTrace();
        }
    }
}
