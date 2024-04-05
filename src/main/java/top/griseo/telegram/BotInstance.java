package top.griseo.telegram;

import com.pengrad.telegrambot.UpdatesListener;
import com.pengrad.telegrambot.model.Update;
import lombok.extern.log4j.Log4j2;
import org.apache.logging.log4j.LogManager;

import java.util.List;

@Log4j2
public class BotInstance implements UpdatesListener {
    @Override
    public int process(List<Update> list) {
        for (Update update : list) {
            this.process(update);
        }
        return UpdatesListener.CONFIRMED_UPDATES_ALL;
    }

    private void process(Update update) {
        if(update.message() != null && update.message().text() != null) {
            switch (update.message().chat().type()) {
                case Private -> log.info("[Private]<{}> {}",update.message().from().username(),update.message().text());
                case group ->  log.info("[{}]<{}> {}",update.message().chat().username(),update.message().from().username(),update.message().text());
            }
        }
    }
}
