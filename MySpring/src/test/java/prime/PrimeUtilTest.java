package prime;

import org.junit.Assert;
import org.junit.Test;

/**
 * @author ShaoJiale
 * @date 2019/12/10
 * @description 使用JUnit编写测试代码
 */
public class PrimeUtilTest {
    // 边界条件测试
    @Test
    public void testGetPrimesForEmptyResult(){
        int[] expected = {};
        Assert. assertArrayEquals(expected, PrimeUtil.getPrimes(2));
        Assert.assertArrayEquals(expected, PrimeUtil.getPrimes(0));
        Assert.assertArrayEquals(expected, PrimeUtil.getPrimes(-1));
    }

    // 正常输入测试
    @Test
    public void testGetPrimes(){
        Assert.assertArrayEquals(new int[]{2, 3, 5, 7}, PrimeUtil.getPrimes(9));
        Assert.assertArrayEquals(new int[]{2, 3, 5, 7, 11, 13}, PrimeUtil.getPrimes(17));
        Assert.assertArrayEquals(new int[]{2, 3, 5, 7, 11, 13, 17, 19, 23, 29}, PrimeUtil.getPrimes(31));
    }
}
