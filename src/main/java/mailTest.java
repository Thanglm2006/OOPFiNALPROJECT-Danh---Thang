import javax.mail.*;
import javax.mail.internet.*;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;
public class mailTest {
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

    public mailTest() {
        FileInputStream f1=null;
        ObjectInputStream ois1=null;
        FileInputStream f2=null;
        ObjectInputStream ois2=null;
        try {
            f1= new FileInputStream(getClass().getClassLoader().getResource("email.dat").getPath());
            ois1= new ObjectInputStream(f1);
            f2= new FileInputStream(getClass().getClassLoader().getResource("pass.dat").getPath());
            ois2= new ObjectInputStream(f2);
            Sender=(String)ois1.readObject();
            Pass= (String)ois2.readObject();
        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (ClassNotFoundException e) {

        } finally{
            if(f1!=null) {
                try {
                    f1.close();
                } catch (IOException e) {

                }
            }
            if(ois1!=null){
                try {
                    ois1.close();
                } catch (IOException e) {

                }
            }
            if(f2!=null) {
                try {
                    f2.close();
                } catch (IOException e) {

                }
            }
            if(ois2!=null){
                try {
                    ois2.close();
                } catch (IOException e) {

                }
            }
        }
    }



    public static void main(String[] args) {
        mailTest m= new mailTest();
        m.sendmail("thanglm.24ai@vku.udn.vn","Hello","hi");
    }
}
