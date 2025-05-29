import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import utils.DataValidator;

import java.util.Arrays;
import java.util.List;


public class DataValidatorTest {
    private static final Double BASE_X = 0D;
    private static final Double BASE_Y = 0D;
    private static final Integer BASE_R = 1;
    private static final List<Integer> CORRECT_RADIUS = Arrays.asList(1, 2, 3, 4);
    private static final List<Integer> BIGGER_RADIUS = Arrays.asList(5, 6, 10, 1000, 10000);
    private static final List<Integer> SMALLER_RADIUS = Arrays.asList(0, -1, -2, -3, -1, -10, -1000, -10000);
    private static final List<Double> CORRECT_X = Arrays.asList(-2D, -1.5D, -1D, 0D, 1D, 2D);
    private static final List<Double> BIGGER_X = Arrays.asList(20000000000000.1D, 3D, 20D, 1000D, 10000D);
    private static final List<Double> SMALLER_X = Arrays.asList(-2.000000000000001D, -3D, -20D, -1000D, -10000D);
    private static final List<Double> CORRECT_Y = Arrays.asList(-5D, -4.999999999999D, -4D, -3D,  -1.5D, -1D, 0D, 1D, 3D);
    private static final List<Double> BIGGER_Y = Arrays.asList(3.0000000000001D, 20D, 1000D, 10000D);
    private static final List<Double> SMALLER_Y = Arrays.asList(-5.000000001D, -20D, -1000D, -10000D);

    private DataValidator dataValidator;

    @Before
    public void setUp() {
        dataValidator = new DataValidator();
    }

    @Test
    public void testCorrectRadius() {
        CORRECT_RADIUS.forEach(radius -> Assert.assertTrue(dataValidator.isDataCorrect(BASE_X, BASE_Y, radius)));
    }

    @Test
    public void testBiggerRadius() {
        BIGGER_RADIUS.forEach(radius -> Assert.assertFalse(dataValidator.isDataCorrect(BASE_X, BASE_Y, radius)));
    }

    @Test
    public void testSmallerRadius() {
        SMALLER_RADIUS.forEach(radius -> Assert.assertFalse(dataValidator.isDataCorrect(BASE_X, BASE_Y, radius)));
    }

    @Test
    public void testCorrectX() {
        CORRECT_X.forEach(x -> Assert.assertTrue(dataValidator.isDataCorrect(x, BASE_Y, BASE_R)));
    }

    @Test
    public void testIncorrectX() {
        BIGGER_X.forEach(x -> Assert.assertFalse(dataValidator.isDataCorrect(x, BASE_Y, BASE_R)));
        SMALLER_X.forEach(x -> Assert.assertFalse(dataValidator.isDataCorrect(x, BASE_Y, BASE_R)));
    }


    @Test
    public void testCorrectY() {
        CORRECT_Y.forEach(y -> Assert.assertTrue(dataValidator.isDataCorrect(BASE_X, y, BASE_R)));
    }

    @Test
    public void testIncorrectY() {
        BIGGER_Y.forEach(y -> Assert.assertFalse(dataValidator.isDataCorrect(BASE_X, y, BASE_R)));
        SMALLER_Y.forEach(y -> Assert.assertFalse(dataValidator.isDataCorrect(BASE_X, y, BASE_R)));
    }

    @Test
    public void testCorrectCombinedData(){
        CORRECT_RADIUS.forEach(radius -> CORRECT_X
                .forEach(x -> CORRECT_Y
                        .forEach(y -> Assert.assertTrue(
                                dataValidator.isDataCorrect(x, y, radius)
                        ))
                )
        );
    }
    @Test
    public void testIncorrectCombinedData(){
        BIGGER_RADIUS.forEach(radius -> BIGGER_X
                .forEach(x -> BIGGER_Y
                        .forEach(y -> Assert.assertFalse(
                                dataValidator.isDataCorrect(x, y, radius)
                        ))
                )
        );
        SMALLER_RADIUS.forEach(radius -> SMALLER_X
                .forEach(x -> SMALLER_Y
                        .forEach(y -> Assert.assertFalse(
                                dataValidator.isDataCorrect(x, y, radius)
                        ))
                )
        );
    }

}
