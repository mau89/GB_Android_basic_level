package com.example.myapplication.data;


public final class HistoryCity {
    private String stringDay;
    private int drawableImageViewDay;
    private String stringTextViewTemperatureDay;

    public HistoryCity(String stringDay, int drawableImageViewDay, String stringTextViewTemperatureDay) {
        this.stringDay = stringDay;
        this.drawableImageViewDay = drawableImageViewDay;
        this.stringTextViewTemperatureDay = stringTextViewTemperatureDay;
    }

    public String getStringDay() {
        return stringDay;
    }

    public int getDrawableImageViewDay() {
        return drawableImageViewDay;
    }

    public String getStringTextViewTemperatureDay() {
        return stringTextViewTemperatureDay;
    }

    @Override
    public String toString() {
        return "HistoryCity{" +
                "stringDay='" + stringDay + '\'' +
                ", drawableImageViewDay='" + drawableImageViewDay + '\'' +
                ", stringTextViewTemperatureDay='" + stringTextViewTemperatureDay + '\'' +
                '}';
    }
}
