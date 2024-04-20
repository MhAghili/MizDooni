import application.MizDooni;


public class Main {
    public static void main(String[] args) {
        try {
            MizDooni.getInstance().run();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}