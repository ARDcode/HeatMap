package develop.heatmap.model;



public class Emotion {
    private String status;
    private String processingResult;
    private Double progress;
    public String getProcessingResult() {
        return processingResult;
    }

    public Emotion(String status, String processingResult, Double progress) {
        this.status = status;
        this.processingResult = processingResult;
        this.progress = progress;
    }

    public void setProcessingResult(String processingResult) {
        this.processingResult = processingResult;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Double getProgress() {
        return progress;
    }

    public void setProgress(Double progress) {
        this.progress = progress;
    }
}
