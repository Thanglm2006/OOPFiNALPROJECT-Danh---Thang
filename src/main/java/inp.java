import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;

public class inp {
    public static void main(String[] args) {
        FileOutputStream fos=null;
//        jdbc:sqlserver://THE-CONQUEROR\SQLEXPRESST:1433;Database=data;User=thang;Password=thanglm2006;encrypt=true;trustServerCertificate=true;loginTimeout=30;

        ObjectOutputStream oos=null;
        try{
            fos=new FileOutputStream("ConnectionToSQL.dat");
            oos=new ObjectOutputStream(fos);
            oos.writeObject("jdbc:sqlserver://192.168.2.207:1433;Database=data;User=sa;Password=Thanglm#2006;encrypt=true;trustServerCertificate=true;loginTimeout=30;");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
