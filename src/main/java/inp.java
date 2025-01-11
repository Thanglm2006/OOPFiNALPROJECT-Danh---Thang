import java.io.*;

public class inp {
    public static void main(String[] args) {
//        FileOutputStream fos=null;
////        jdbc:sqlserver://THE-CONQUEROR\SQLEXPRESST:1433;Database=data;User=thang;Password=thanglm2006;encrypt=true;trustServerCertificate=true;loginTimeout=30;
//
//        ObjectOutputStream oos=null;
//        try{
//            fos=new FileOutputStream("ConnectionToSQL.dat");
//            oos=new ObjectOutputStream(fos);
//            oos.writeObject("jdbc:sqlserver://172.17.0.1:1433;Database=data;User=sa;Password=Thanglm#2006;encrypt=true;trustServerCertificate=true;loginTimeout=30;");
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
        InputStream inputStream = inp.class.getResourceAsStream("/Security/ConnectionToSQL.dat");
        System.out.println(inputStream);
        if (inputStream == null) {
            System.out.println("Error");
        }

    }
}
