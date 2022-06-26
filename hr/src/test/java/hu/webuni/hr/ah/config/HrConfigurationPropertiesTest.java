package hu.webuni.hr.ah.config;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Random;

import static org.assertj.core.api.Assertions.*;

class HrConfigurationPropertiesTest {

    HrConfigurationProperties.Top top;
    Map<Double, Integer> percentsToLimits;
    Map<Double, Integer> percentsToLimitsToSet;

    Random random;
    double epsilon;

    @BeforeEach
    void setUp() {
        top = new HrConfigurationProperties.Top();

        random = new Random();
        epsilon = Math.pow(10, -5);
    }

    @Test
    void testSetPercentsToLimits() {
        percentsToLimitsToSet = new LinkedHashMap<>();
        percentsToLimitsToSet.put((double) random.nextInt(20), 0);
        percentsToLimitsToSet.put((double) random.nextInt(20), 0);
        percentsToLimitsToSet.put((double) random.nextInt(20), 0);
        percentsToLimitsToSet.put((double) random.nextInt(20), 0);

        top.setPercentsToLimits(percentsToLimitsToSet);
        percentsToLimits = top.getPercentsToLimits();

        assertThat(isReverseOrdered(percentsToLimits)).isTrue();
    }

    // --- private methods ----------------------------------------------------

    private boolean isReverseOrdered(Map<Double, Integer> map) {
        double lastKey = Double.MAX_VALUE;
        for (double key : map.keySet()) {
            if (isGreater(key, lastKey)) {
                return false;
            }
            lastKey = key;
        }
        return true;
    }

    private boolean isGreater(double value, double other) {
        return value > other + epsilon;
    }
}