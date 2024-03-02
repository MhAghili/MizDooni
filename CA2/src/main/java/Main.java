import DataBase.MemoryDataBase;
import services.FeedbackServiceImpl;
import services.RestaurantServiceImpl;
import services.UserServiceImplementation;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        try {
            var memoryDataBase = new MemoryDataBase();
            var mizDooni = new MizDooni(memoryDataBase, new RestaurantServiceImpl(memoryDataBase), new UserServiceImplementation(memoryDataBase), new FeedbackServiceImpl(memoryDataBase));
            mizDooni.run();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}