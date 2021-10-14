package com.github.Smaltin;

import java.io.IOException;
import java.sql.Timestamp;
import java.text.DecimalFormat;
import java.time.Instant;

public class SubCount extends Thread {

    @Override
    public void run() {
        while (true) {
            try {
                String subs = Methods.getSubscriberCount();
                Long subsLong = Long.parseLong(subs);
                DecimalFormat myFormatter = new DecimalFormat("###,###");
                if (!subs.equals(Main.postedSubCt)) {
                    Methods.setChannelName(Main.CHANNEL_ID, Main.SUBSCRIBER_NAME + ": " + myFormatter.format(subsLong), false);
                    Main.postedSubCt = subs;
                    System.out.println(Timestamp.from(Instant.now()) + "[Changed] " + myFormatter.format(subsLong) + " " + Main.SUBSCRIBER_NAME);
                } else {
                    System.out.println(Timestamp.from(Instant.now()) + "[No Change] " + myFormatter.format(subsLong) + " " + Main.SUBSCRIBER_NAME);
                }
                Thread.sleep(60000);
            } catch (InterruptedException | IOException e) {
                e.printStackTrace();
            }
        }
    }
}