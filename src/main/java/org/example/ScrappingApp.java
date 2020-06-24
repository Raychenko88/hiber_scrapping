package org.example;

import lombok.extern.slf4j.Slf4j;
import org.example.handler.ParserHandler;
import org.example.model.Item;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;


public class ScrappingApp {
    private static final Logger LOGGER = Logger.getLogger(ScrappingApp.class.getName());
    public static void main(String[] args) {

        final List<Thread> threads = new ArrayList<>();
        final List<Item> items = new ArrayList<>();

        if (args.length == 0) {

        } else {
            String keyWord = args[0];
            ParserHandler parserHandler = new ParserHandler(threads, items);
            parserHandler.startScrapping(keyWord);
        }
        boolean threadsFinished;
        do {
            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            threadsFinished = checkThreads(threads);
        } while  (threadsFinished);

        LOGGER.info("Parser finished!!! " + items.size() + " were extracted!");
    }

    private static boolean checkThreads(List<Thread> threads) {
        for (Thread thread : threads) {
            if (thread.isAlive() || thread.getState().equals(Thread.State.NEW)) {
                return true;
            }
        }
        return false;
    }

}
