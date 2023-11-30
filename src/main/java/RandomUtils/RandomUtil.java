package RandomUtils;

import java.util.Random;
import java.util.Scanner;

public class RandomUtil {
    public static Random random = new Random();
    public static long getRandomNumber(int n,int m){
        return random.nextLong(n,m)+1;
    }

}
