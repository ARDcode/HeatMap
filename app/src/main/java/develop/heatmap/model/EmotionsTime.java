package develop.heatmap.model;

import java.util.List;

public class EmotionsTime {
    private String time;
    private EmotionStat emotionStats;

    public EmotionsTime(String time, EmotionStat emotionStats) {
        this.time = time;
        this.emotionStats = emotionStats;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public EmotionStat getEmotionStats() {
        return emotionStats;
    }

    public void setEmotionStats(EmotionStat emotionStats) {
        this.emotionStats = emotionStats;
    }
}
