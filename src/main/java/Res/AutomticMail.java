package Res;

import javax.mail.*;
import javax.mail.internet.*;
import java.io.*;
import java.net.URL;
import java.util.Properties;
public class AutomticMail {
    private  String Sender;
    private  String Pass;
    private  Properties props = new Properties();

     Session session = Session.getInstance(props, new Authenticator() {
        @Override
        protected PasswordAuthentication getPasswordAuthentication() {
            return new PasswordAuthentication(Sender, Pass);
        }
    });
public  void sendmail(String recipient, String Title,String Text){
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.host", "smtp.gmail.com");
        props.put("mail.smtp.port", "587");
    try {
        Message mess = new MimeMessage(session);
        mess.setFrom(new InternetAddress(Sender));
        mess.setRecipients(Message.RecipientType.TO, InternetAddress.parse(recipient));
        mess.setSubject(Title);
        mess.setText(Text);
        Transport.send(mess);
        System.out.println("successfully send!");

    } catch (Exception e) {
        e.printStackTrace();
    }

    }

    public AutomticMail() {


        try {
            URL resourceUrl = getClass().getClassLoader().getResource("email.dat");
            if (resourceUrl == null) {
                throw new FileNotFoundException("Resource 'email.dat' not found.");
            }
            try (InputStream in = resourceUrl.openStream()) {
                byte[] buffer = new byte[1024];
                int bytesRead;
                ByteArrayOutputStream byteCollector = new ByteArrayOutputStream();

                while ((bytesRead = in.read(buffer)) != -1) {
                    byteCollector.write(buffer, 0, bytesRead);
                }
                byte[] fullBytes = byteCollector.toByteArray();
                Sender  = new String(fullBytes, "UTF-8");
                Sender=Sender.substring(7);
            }

        } catch (IOException e) {
            e.printStackTrace();
        }

        try {
            URL resourceUrl = getClass().getClassLoader().getResource("pass.dat");
            if (resourceUrl == null) {
                throw new FileNotFoundException("Resource 'pass.dat' not found.");
            }
            try (InputStream in = resourceUrl.openStream()) {
                byte[] buffer = new byte[1024];
                int bytesRead;
                ByteArrayOutputStream byteCollector = new ByteArrayOutputStream();

                while ((bytesRead = in.read(buffer)) != -1) {
                    byteCollector.write(buffer, 0, bytesRead);
                }
                byte[] fullBytes = byteCollector.toByteArray();
                Pass  = new String(fullBytes, "UTF-8");
                Pass=Pass.substring(7);
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    public static void main(String[] args) {
        AutomticMail m= new AutomticMail();
        m.sendmail("thanglm.24ai@vku.udn.vn","Hello","hi");
    }
}
