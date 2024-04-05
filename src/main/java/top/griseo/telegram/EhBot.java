package top.griseo.telegram;

import com.pengrad.telegrambot.TelegramBot;
import lombok.Getter;
import lombok.extern.log4j.Log4j2;
import okhttp3.OkHttpClient;
import top.griseo.telegram.config.Manager;

import java.io.File;
import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.Proxy;

@Log4j2
public class EhBot {
    @Getter
    private static final File dataPath = new File(System.getProperty("user.dir"),"data");
    @Getter
    private static Manager configManager = null;

    static {
        log.info("Initializing Bot...");
        try {
            if(!dataPath.exists())
                if(!dataPath.mkdirs())
                    log.error("Something went wrong",new IOException(String.format("Failed create directory: %s", dataPath.getAbsolutePath())));
            configManager = new Manager(new File(dataPath,"config.json"));
        } catch (IOException e) {
            log.error("Something went wrong",e);
            System.exit(1);
        }
        Runtime.getRuntime().addShutdownHook(new Thread(() -> {
            try {
                if(configManager != null)
                    configManager.save();
            } catch (IOException e) {
                log.error("Something went wrong", e);
            }
        }));
    }

    public static void main(String[] args) {
        TelegramBot bot = new TelegramBot.Builder(
                configManager.getConfig().getTelegram().getBotToken()
        )
                .okHttpClient(
                        new OkHttpClient.Builder().proxy(new Proxy(Proxy.Type.HTTP,new InetSocketAddress("127.0.0.1",2080))).build()
                ).build();
        bot.setUpdatesListener(
                new BotInstance(),
                e -> log.error("Something went wrong",e)
        );
        log.info("Initialized!");
    }
}
