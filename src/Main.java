public class Main {
    public static void main(String[] args) throws ClassNotFoundException{
        try{
            Class.forName("com.mysql.jdbc.Driver");
            System.out.println("Drivers loaded successfully...");
        }catch (ClassNotFoundException e){
            System.out.println(e.getMessage());
 }
 }
}