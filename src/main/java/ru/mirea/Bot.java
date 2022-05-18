package ru.mirea;

import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

public class Bot extends TelegramLongPollingBot {
    private BotState botState = BotState.DEFAULT;

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
        if (update.hasMessage() && update.getMessage().hasText()) {
            String messageText = update.getMessage().getText();
            SendMessage message = new SendMessage();
            message.setChatId(update.getMessage().getChatId().toString());


            if (messageText.equals("/start") || messageText.equals("/help")) {
                message.setText("""
                        Команды бота:
                        /current - текущая погода
                        /today - погода на сегодня
                        /tomorrow - погода на завтра
                        /help - список команд
                        """);
                setBotState(BotState.DEFAULT);
            } else if (messageText.equals("/current")) {
                message.setText("Введите город");
                setBotState(BotState.CURRENT);
            } else if (messageText.equals("/today")) {
                message.setText("Введите город");
                setBotState(BotState.TODAY);
            } else if (messageText.equals("/tomorrow")) {
                message.setText("Введите город");
                setBotState(BotState.TOMORROW);
            } else {
                switch (getBotState()) {
                    case CURRENT -> message.setText(Weather.getCurrentWeather(messageText));
                    case TODAY -> message.setText(Weather.getDayWeather(messageText, 0));
                    case TOMORROW -> message.setText(Weather.getDayWeather(messageText, 1));
                }
            }
            try {
                execute(message);
            } catch (TelegramApiException e) {
                e.printStackTrace();
            }
        }
    }

    public BotState getBotState() {
        return botState;
    }

    public void setBotState(BotState botState) {
        this.botState = botState;
    }
}
