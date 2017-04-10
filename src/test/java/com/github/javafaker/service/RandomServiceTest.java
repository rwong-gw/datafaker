package com.github.javafaker.service;

import com.github.javafaker.AbstractFakerTest;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.Random;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.Matchers.greaterThan;
import static org.hamcrest.Matchers.isIn;
import static org.hamcrest.Matchers.lessThan;
import static org.junit.Assert.assertThat;

/**
 * @author pmiklos
 *
 */
@RunWith(Parameterized.class)
public class RandomServiceTest extends AbstractFakerTest {

    private RandomService randomService;

    public RandomServiceTest(String ignoredTitle, RandomService service) {
        this.randomService = service;
    }

    @Parameterized.Parameters(name = "Created via {0}")
    public static Collection<Object[]> data() {
        Object[][] data = new Object[][]{
                {"RandomService(Random)", new RandomService(new Random())},
                {"RandomService()", new RandomService()}
        };
        return Arrays.asList(data);
    }
    
    @Test(expected = IllegalArgumentException.class)
    public void testPositiveBoundariesOnly() {
        randomService.nextLong(0L);
    }

    @Test
    public void testLongWithinBoundary() {
        assertThat(randomService.nextLong(1), is(0L));

        for (int i = 1; i < 10; i++) {
            assertThat(randomService.nextLong(2), lessThan(2L));
        }
    }

    @Test
    public void testLongMaxBoundary() {
        assertThat(randomService.nextLong(Long.MAX_VALUE), greaterThan(0L));
        assertThat(randomService.nextLong(Long.MAX_VALUE), lessThan(Long.MAX_VALUE));
    }

    @Test
    public void testNextArrayElement() {
        Integer[] array = new Integer[] { 1, 2, 3, 5, 8, 13, 21 };

        for (int i = 1; i < 10; i++) {
            assertThat(randomService.nextElement(array), isIn(array));
        }
    }

    @Test
    public void testNextListElement() {
        List<Integer> list = Arrays.asList(new Integer[] { 1, 2, 3, 5, 8, 13, 21 });

        for (int i = 1; i < 10; i++) {
            assertThat(randomService.nextElement(list), isIn(list));
        }
    }

    @Test
    public void testNextEnumValue() {
        for (int i = 1; i < 10; i++) {
            assertThat(randomService.nextEnumValue(TestEnum.class), isIn(TestEnum.values()));
        }
    }

    private enum TestEnum {
        ONE,
        TWO,
        THREE
    }
}
