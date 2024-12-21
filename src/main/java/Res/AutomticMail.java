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

    public AutomticMail(SQLQueries sql) {
        String[] acc=sql.getAdminMail();
        Sender=acc[0];
        Pass=acc[1];
    }


    public static void main(String[] args) {
        AutomticMail m= new AutomticMail(new SQLQueries());
        m.sendmail("thanglm.24ai@vku.udn.vn","Hello","hi");
    }
}
