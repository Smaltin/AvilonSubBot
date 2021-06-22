import java.io.IOException;
import java.text.DecimalFormat;

public class SubCount extends Thread {

    @Override
    public void run() {
        while (true) {
            try {
                String subs = Methods.getSubscriberCount();
                Long subsLong = Long.parseLong(subs);
                DecimalFormat myFormatter = new DecimalFormat("###,###");
                if (!subs.equals(Main.postedSubCt)) {
                    Methods.setChannelName(Main.CHANNEL_ID, "Boobas: " + myFormatter.format(subsLong), false);
                    Main.postedSubCt = subs;
                    System.out.println("[Changed] " + myFormatter.format(subsLong) + " boobas");
                } else {
                    System.out.println("[No Change] " + myFormatter.format(subsLong) + " boobas");
                }
                Thread.sleep(60000);
            } catch (InterruptedException | IOException e) {
                e.printStackTrace();
            }
        }
    }
}