package com.example.file.task.cron;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.StandardOpenOption;

@Service
public class LogCleanupService {

    private static final Logger logger = LoggerFactory.getLogger(LogCleanupService.class);
    private static final String LOG_FILE_PATH = "/logs/LoanManSys.log"; // Log fayl manzili

    @Scheduled(cron = "0 0 0 */7 * *") // Har 7 kunda, soat 00:00 da ishga tushadi
    public void clearLogFile() {
        File logFile = new File(LOG_FILE_PATH);
        if (logFile.exists()) {
            try {
                Files.write(logFile.toPath(), new byte[0], StandardOpenOption.TRUNCATE_EXISTING);
                logger.info("✅ Log fayli tozalandi: " + LOG_FILE_PATH);
            } catch (IOException e) {
                logger.error("❌ Log faylni tozalashda xatolik: " + e.getMessage());
            }
        } else {
            logger.warn("⚠ Log fayl topilmadi: " + LOG_FILE_PATH);
        }
    }
}

