package prime;

import java.util.Arrays;

/**
 * @author ShaoJiale
 * @date 2019/12/10
 * @description 测试JUnit使用
 */
public class PrimeUtil {
    public static int[] getPrimes(int max){
        if(max <= 2)
            return new int[]{};

        int[] primes = new int[max];
        int count = 0;
        for(int num = 2; num < max; num++){
            if(isPrime(num))
                primes[count++] = num;
        }
        primes = Arrays.copyOf(primes, count);
        return primes;
    }

    private static boolean isPrime(int num){
        for(int i = 2; i < num / 2 + 1; i++){
            if(num % i == 0)
                return false;
        }
        return true;
    }
}
