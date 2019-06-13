package develop.heatmap.model;

public class EmotionStat {
    String anger;
    String contempt;
    String disgust;
    String joy;
    String sadness;
    String surprise;

    public EmotionStat(String anger, String contempt, String disgust, String joy, String sadness, String surprise) {
        this.anger = anger;
        this.contempt = contempt;
        this.disgust = disgust;
        this.joy = joy;
        this.sadness = sadness;
        this.surprise = surprise;
    }

    public String getAnger() {
        return anger;
    }

    public void setAnger(String anger) {
        this.anger = anger;
    }

    public String getContempt() {
        return contempt;
    }

    public void setContempt(String contempt) {
        this.contempt = contempt;
    }

    public String getDisgust() {
        return disgust;
    }

    public void setDisgust(String disgust) {
        this.disgust = disgust;
    }

    public String getJoy() {
        return joy;
    }

    public void setJoy(String joy) {
        this.joy = joy;
    }

    public String getSadness() {
        return sadness;
    }

    public void setSadness(String sadness) {
        this.sadness = sadness;
    }

    public String getSurprise() {
        return surprise;
    }

    public void setSurprise(String surprise) {
        this.surprise = surprise;
    }
}
