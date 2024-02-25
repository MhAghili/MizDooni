import DataBase.MemoryDataBase;
import services.RestaurantServiceImpl;
import services.UserServiceImplementation;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        var memoryDataBase = new MemoryDataBase();
        var mizDooni = new MizDooni(memoryDataBase, new RestaurantServiceImpl(memoryDataBase), new UserServiceImplementation(memoryDataBase));
        mizDooni.run();
    }
}