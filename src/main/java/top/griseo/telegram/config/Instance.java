package top.griseo.telegram.config;

import lombok.Data;

import java.util.LinkedList;

@Data
public class Instance {
    private final String version = "0.0.1";
    private Telegram telegram = new Telegram();
    private Proxy proxy = new Proxy();
    @Data
    public static final class Telegram {
        private String BotToken = "";
        private boolean isEnableWhitelist=false;
        private LinkedList<Long> AllowUseChatID = new LinkedList<>();
    }
    @Data
    public static final class Proxy {
        private String type = "DIRECT";
        private String host = "127.0.0.1";
        private int port = 2080;
        private Http http = new Http();
        @Data
        public static final class Http {
            private String account = "";
            private String password = "";
        }
    }
}
