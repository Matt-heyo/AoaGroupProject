package com.application.model;



import java.io.IOException;
import java.util.logging.*;

public class LoggerConfig {

    private static Logger logger;

    public static Logger getLogger() {
        if (logger != null) return logger;

        logger = Logger.getLogger("AOA_Logger");
        logger.setUseParentHandlers(false);

        try {
            FileHandler fh = new FileHandler("aoa_app.log", true);
            fh.setFormatter(new SimpleFormatter());
            logger.addHandler(fh);
        } catch (IOException e) {
            System.out.println("Logger setup failed: " + e.getMessage());
        }

        return logger;
    }
}

