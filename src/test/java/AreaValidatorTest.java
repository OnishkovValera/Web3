import org.junit.Assert;
import org.junit.Test;
import utils.AreaValidator;

import java.util.Arrays;
import java.util.List;

public class AreaValidatorTest {
    
    // Базовые значения для тестирования
    private static final Integer BASE_R = 1;
    
    // Тестовые точки для первой четверти (x > 0, y > 0) - должны возвращать false
    private static final List<Double[]> FIRST_QUARTER_POINTS = Arrays.asList(
            new Double[]{1.0, 1.0},
            new Double[]{2.0, 3.0},
            new Double[]{0.5, 0.5},
            new Double[]{3.0, 1.5}
    );

    
    private static final List<Double[]> SECOND_QUARTER_OUTSIDE_POINTS = Arrays.asList(
            new Double[]{-2.0, 3.0},
            new Double[]{-3.0, 2.0},
            new Double[]{-1.0, 2.0}
    );

    
    private static final List<Double[]> THIRD_QUARTER_OUTSIDE_POINTS = Arrays.asList(
            new Double[]{-2.0, -2.0},
            new Double[]{-3.0, -1.0},
            new Double[]{-1.5, -1.5}
    );

    
    private static final List<Double[]> FOURTH_QUARTER_OUTSIDE_POINTS = Arrays.asList(
            new Double[]{2.5, -1.5},
            new Double[]{1.0, -1.5},
            new Double[]{3.0, -0.5}
    );
    
    // Точки на осях координат
    private static final List<Double[]> AXIS_POINTS_INSIDE = Arrays.asList(
            new Double[]{0.0, 0.0},   // начало координат
            new Double[]{1.0, 0.0},   // положительная ось X
            new Double[]{-1.0, 0.0},  // отрицательная ось X
            new Double[]{0.0, 1.0},   // положительная ось Y
            new Double[]{0.0, -0.5}   // отрицательная ось Y
    );
    
    // Тестовые радиусы
    private static final List<Integer> TEST_RADII = Arrays.asList(1, 2, 3, 4);
    
    // Специальные тесты для разных радиусов
    private static final List<Object[]> RADIUS_SPECIFIC_INSIDE_POINTS = Arrays.asList(
            new Object[]{-0.5, -0.5, 1, true},   // R=1, внутри четверти окружности
            new Object[]{0.5, -0.3, 1, true},    // R=1, внутри прямоугольника
            new Object[]{-1.5, -1.5, 3, true},   // R=3, внутри четверти окружности
            new Object[]{2.0, -1.0, 3, true},    // R=3, внутри прямоугольника
            new Object[]{-2.0, 2.0, 4, true}     // R=4, внутри треугольника
    );
    
    private static final List<Object[]> RADIUS_SPECIFIC_OUTSIDE_POINTS = Arrays.asList(
            new Object[]{1.5, -0.3, 1, false},   // R=1, вне прямоугольника
            new Object[]{5.0, -1.0, 4, false},   // R=4, вне прямоугольника
            new Object[]{-3.0, -3.0, 2, false}   // R=2, вне четверти окружности
    );

    @Test
    public void testFirstQuarterAlwaysFalse() {
        FIRST_QUARTER_POINTS.forEach(point -> 
            Assert.assertFalse("Point (" + point[0] + ", " + point[1] + ") in first quarter should return false",
                    AreaValidator.isHit(point[0], point[1], BASE_R))
        );
    }


    @Test
    public void testSecondQuarterOutsideTriangle() {
        SECOND_QUARTER_OUTSIDE_POINTS.forEach(point -> 
            Assert.assertFalse("Point (" + point[0] + ", " + point[1] + ") should be outside triangle",
                    AreaValidator.isHit(point[0], point[1], BASE_R))
        );
    }


    @Test
    public void testThirdQuarterOutsideCircle() {
        THIRD_QUARTER_OUTSIDE_POINTS.forEach(point -> 
            Assert.assertFalse("Point (" + point[0] + ", " + point[1] + ") should be outside quarter circle",
                    AreaValidator.isHit(point[0], point[1], BASE_R))
        );
    }


    @Test
    public void testFourthQuarterOutsideRectangle() {
        FOURTH_QUARTER_OUTSIDE_POINTS.forEach(point -> 
            Assert.assertFalse("Point (" + point[0] + ", " + point[1] + ") should be outside rectangle",
                    AreaValidator.isHit(point[0], point[1], BASE_R))
        );
    }

    @Test
    public void testAxisPoints() {
        AXIS_POINTS_INSIDE.forEach(point -> 
            Assert.assertTrue("Point (" + point[0] + ", " + point[1] + ") on axis should be inside",
                    AreaValidator.isHit(point[0], point[1], BASE_R))
        );
    }

    @Test
    public void testDifferentRadiusValues() {
        TEST_RADII.forEach(radius -> {
            // Тестируем некоторые базовые точки для каждого радиуса
            Assert.assertTrue("Origin should always be inside for R=" + radius,
                    AreaValidator.isHit(0.0, 0.0, radius));
            Assert.assertFalse("First quarter should always be false for R=" + radius,
                    AreaValidator.isHit(1.0, 1.0, radius));
        });
    }

    @Test
    public void testRadiusSpecificInsidePoints() {
        RADIUS_SPECIFIC_INSIDE_POINTS.forEach(testCase -> {
            Double x = (Double) testCase[0];
            Double y = (Double) testCase[1];
            Integer r = (Integer) testCase[2];
            Boolean expected = (Boolean) testCase[3];
            
            Assert.assertEquals("Point (" + x + ", " + y + ") with R=" + r + " should return " + expected,
                    expected, AreaValidator.isHit(x, y, r));
        });
    }

    @Test
    public void testRadiusSpecificOutsidePoints() {
        RADIUS_SPECIFIC_OUTSIDE_POINTS.forEach(testCase -> {
            Double x = (Double) testCase[0];
            Double y = (Double) testCase[1];
            Integer r = (Integer) testCase[2];
            Boolean expected = (Boolean) testCase[3];
            
            Assert.assertEquals("Point (" + x + ", " + y + ") with R=" + r + " should return " + expected,
                    expected, AreaValidator.isHit(x, y, r));
        });
    }

    @Test
    public void testOriginForAllRadii() {
        TEST_RADII.forEach(radius -> 
            Assert.assertTrue("Origin (0,0) should always be inside for any radius",
                    AreaValidator.isHit(0.0, 0.0, radius))
        );
    }
}