import java.io.IOException;

public class SubCount extends Thread {

    @Override
    public void run() {
        while (true) {
            try {
                String subs = Methods.getSubscriberCount();
                if (!subs.equals(Main.postedSubCt)) {
                    Methods.setChannelName(Main.CHANNEL_ID, "Subscribers: " + subs, false);
                    Main.postedSubCt = subs;
                    System.out.println("[Changed] " + subs);
                } else {
                    System.out.println("[No Change] " + subs);
                }
                Thread.sleep(60000);
            } catch (InterruptedException | IOException e) {
                e.printStackTrace();
            }
        }
    }
}