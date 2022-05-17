package ru.mirea;

import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

public class Bot extends TelegramLongPollingBot {
    @Override
    public String getBotUsername() {
        return "@hhweather_bot";
    }

    @Override
    public String getBotToken() {
        return "5340511592:AAEVL--KnmtE-Tx8gECSvg1pZkbs3UEpw6M";
    }

    @Override
    public void onUpdateReceived(Update update) {
        if(update.hasMessage()&& update.getMessage().hasText()){
            String messageText=update.getMessage().getText();
            SendMessage message = new SendMessage();// Create a SendMessage object with mandatory fields
            message.setChatId(update.getMessage().getChatId().toString());


            if(messageText.equals("/start")||messageText.equals("/help")){
                message.setText("""
                        Команды бота:
                        /current - текущая погода
                        /today - погода на сегодня
                        /tomorrow - погода на завтра
                        /help - список команд
                        """);

            }

            try {
                execute(message);
            }catch (TelegramApiException e){
                e.printStackTrace();
            }
        }
    }
}
