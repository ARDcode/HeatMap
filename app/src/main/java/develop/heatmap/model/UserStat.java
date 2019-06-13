package develop.heatmap.model;

public class UserStat {
    String genderValue;
    String ageValue;
    String ethnicityValue;

    public UserStat(String genderValue, String ageValue, String ethnicityValue) {
        this.genderValue = genderValue;
        this.ageValue = ageValue;
        this.ethnicityValue = ethnicityValue;
    }

    public String getGenderValue() {
        return genderValue;
    }

    public void setGenderValue(String genderValue) {
        this.genderValue = genderValue;
    }

    public String getAgeValue() {
        return ageValue;
    }

    public void setAgeValue(String ageValue) {
        this.ageValue = ageValue;
    }

    public String getEthnicityValue() {
        return ethnicityValue;
    }

    public void setEthnicityValue(String ethnicityValue) {
        this.ethnicityValue = ethnicityValue;
    }
}
