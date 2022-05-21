package hu.webuni.hr.ah.config;

import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties(prefix = "hr")
public class HrConfigurationProperties {

    // --- attributes ---------------------------------------------------------

    private PayRaise payRaise;

    // --- constructors -------------------------------------------------------

    public HrConfigurationProperties() {
        this.payRaise = new PayRaise();
    }

    // --- getters and setters ------------------------------------------------

    public PayRaise getPayRaise() { return payRaise; }

    // --- nested classes -----------------------------------------------------

    public static class PayRaise {

        private Default defaultConfig;
        private Smart smartConfig;
        private Advanced advancedConfig;
        private Top topConfig;

        public PayRaise() {
            defaultConfig = new Default();
            smartConfig = new Smart();
            advancedConfig = new Advanced();
            topConfig = new Top();
        }

        public Default getDefaultConfig() { return defaultConfig; }
        public Smart getSmartConfig() { return smartConfig; }
        public Advanced getAdvancedConfig() { return advancedConfig; }
        public Top getTopConfig() { return topConfig; }
    }

    public static class Default {

        private Percent percent;

        public Default() {
            percent = new Percent();
        }

        public Percent getPercent() { return percent; }
    }

    public static class Smart {

        private Limit limit;
        private Percent percent;

        public Smart() {
            limit = new Limit();
            percent = new Percent();
        }

        public Limit getLimit() { return limit; }
        public Percent getPercent() { return percent; }
    }

    public static class Advanced {

        private Limit limit;
        private Percent percent;

        public Advanced() {
            limit = new Limit();
            percent = new Percent();
        }

        public Limit getLimit() { return limit; }
        public Percent getPercent() { return percent; }
    }

    public static class Top {

        private Percent percent;
        private Map<Double, Integer> percentsToLimits;

        public Top() {
            percent = new Percent();
            percentsToLimits = new TreeMap<>(Comparator.reverseOrder());
        }

        public Percent getPercent() { return percent; }
        public Map<Double, Integer> getPercentsToLimits() { return percentsToLimits; }

        public void setPercentsToLimits(Map<Double, Integer> percentsToLimits) {
            this.percentsToLimits.putAll(percentsToLimits);
        }
    }

    public static class Limit {

        private double lowValue;
        private double middleValue;
        private double highValue;
        private List<Double> arbitraryValues;

        public double getLowValue() { return lowValue; }
        public double getMiddleValue() { return middleValue; }
        public double getHighValue() { return highValue; }
        public List<Double> getArbitraryValues() { return arbitraryValues; }

        public void setLowValue(double lowValue) { this.lowValue = lowValue; }
        public void setMiddleValue(double middleValue) { this.middleValue = middleValue; }
        public void setHighValue(double highValue) { this.highValue = highValue; }
        public void setArbitraryValues(List<Double> arbitraryValues) { this.arbitraryValues = arbitraryValues; }
    }

    public static class Percent {

        private int baseValue;
        private int lowValue;
        private int middleValue;
        private int highValue;
        private List<Integer> arbitraryValues;

        public int getBaseValue() { return baseValue; }
        public int getLowValue() { return lowValue; }
        public int getMiddleValue() { return middleValue; }
        public int getHighValue() { return highValue; }
        public List<Integer> getArbitraryValues() { return arbitraryValues; }

        public void setBaseValue(int baseValue) { this.baseValue = baseValue; }
        public void setLowValue(int lowValue) { this.lowValue = lowValue; }
        public void setMiddleValue(int middleValue) { this.middleValue = middleValue; }
        public void setHighValue(int highValue) { this.highValue = highValue; }
        public void setArbitraryValues(List<Integer> arbitraryValues) { this.arbitraryValues = arbitraryValues; }
    }
}