import java.io.*;
import java.util.HashMap;

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
        HashMap<String,Integer> hm= new HashMap<>();
        hm.put("a",1);
        hm.put("b",2);
        hm.put("c",3);
        hm.put("d",4);
        System.out.println(hm.size());

    }
}
