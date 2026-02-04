package com.warden.client.settings;

public class NumberSetting {
    public String name;
    public double value;
    public double min;
    public double max;
    public double increment;

    public NumberSetting(String name, double value, double min, double max, double increment) {
        this.name = name;
        this.value = value;
        this.min = min;
        this.max = max;
        this.increment = increment;
    }

    public double getValue() { return value; }
    public void setValue(double value) {
        // Değeri min-max arasında tut
        if (value < min) value = min;
        if (value > max) value = max;
        this.value = value;
    }
}
