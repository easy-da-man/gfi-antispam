package ch.bemed.antispam;

import ch.bemed.antispam.mail.Splitter;

import javax.mail.Address;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.Arrays;
import java.util.List;
import java.util.Properties;

public class Main {

    public static void main(String[] args) {
        Main m = new Main();
        m.parseTextFile();
    }


    public void parseTextFile() {
        Splitter splitter = new Splitter();
        List<String> mails = splitter.split();
        this.parseMails(mails);

    }

    public void parseMails(List<String> mails) {
        mails.forEach(m -> {
            String content = m;
            Session session = Session.getInstance(new Properties());
            InputStream is = new ByteArrayInputStream(content.getBytes());
            try {
                MimeMessage message = new MimeMessage(session, is);
                //message.writeTo(System.out);
                Address[] addresses = message.getFrom();
//                for (int i = 0; i<addresses.length; i++) {
//                    System.out.println(addresses[i].toString());
//                }
                Address[] replys = message.getReplyTo();
                Arrays.stream(replys).forEach(r -> {
                    System.out.println(r.toString());
                });

                Arrays.stream(addresses).forEach(a -> {
//                    System.out.println(a.toString());
                });
                System.out.println((InternetAddress)message.getFrom()[0]);
            } catch (MessagingException e) {
                e.printStackTrace();
            }
        });
    }

}
