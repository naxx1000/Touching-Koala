package com.raki.cociocompany2.myapp2;

public class HSDataProvider {
    private String hsName;
    private Double hsScore;

    public HSDataProvider(String hsName, Double hsScore){
        super();
        this.setHSName(hsName);
        this.setHSScore(hsScore);
    }

    public String getHSName() {
        return hsName;
    }

    public void setHSName(String hsName) {
        this.hsName = hsName;
    }

    public Double getHSScore() {
        return hsScore;
    }

    public void setHSScore(Double hsScore) {
        this.hsScore = hsScore;
    }
}
