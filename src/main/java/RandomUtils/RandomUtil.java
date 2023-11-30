package RandomUtils;

import java.util.Random;
import java.util.Scanner;
import java.util.concurrent.ThreadLocalRandom;

public class RandomUtil {
    public static long getRandomNumber(long smallest,long biggest){
        return ThreadLocalRandom.current().nextLong(smallest, biggest+1);
    }

}
