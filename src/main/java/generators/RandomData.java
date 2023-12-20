package generators;

import java.text.DecimalFormat;
import java.util.Random;

public class RandomData {
    public static String getPrice(){
        Random rand = new Random();
        float min = 1.00f;
        float max = 1.10f;
        float randomFloat = min + rand.nextFloat() * (max - min);
        DecimalFormat df = new DecimalFormat("0.00");
        return df.format(randomFloat);
    }

}
