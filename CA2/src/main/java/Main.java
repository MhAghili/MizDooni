import DataBase.MemoryDataBase;
import services.FeedbackServiceImpl;
import services.RestaurantServiceImpl;
import services.UserServiceImplementation;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        try {
            MizDooni.getInstance().run();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}