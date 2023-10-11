package com.fragmc;

import io.github.cdimascio.dotenv.Dotenv;

public class Config {
    private static Dotenv dotenv = Dotenv.load();

    public static String getBotToken() {
        return dotenv.get("BOT_TOKEN");
    }
}
