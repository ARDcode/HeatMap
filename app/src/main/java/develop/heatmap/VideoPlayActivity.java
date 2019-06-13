package develop.heatmap;

import android.app.ProgressDialog;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.MediaController;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.VideoView;

import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.formatter.PercentFormatter;
import com.github.mikephil.charting.utils.ColorTemplate;
import com.google.gson.Gson;
import com.google.gson.annotations.SerializedName;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import develop.heatmap.model.Emotion;
import develop.heatmap.model.Emotions;
import develop.heatmap.model.Fragment;
import develop.heatmap.model.VideoEmotion;
import okhttp3.MediaType;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class VideoPlayActivity extends AppCompatActivity {

    private VideoView video1;
    private VideoView video2;
    private String videourl;
    private String operation_location;
    private Boolean success = false;
    private Integer neutral = 1;
    private Integer happiness = 1;
    private Integer sadness = 1;
    private Integer anger = 1;
    private Integer disgust = 1;
    private Integer fear = 1;
    private Integer contempt = 1;
    private Integer surprise = 1;
    private PieChart pieChart;
    private String emotionsResponce;
    private ProgressDialog progressDialog;
    private TextView text;
    final DBHelperPrototypes db = new DBHelperPrototypes(this);
    private String videoname;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_video_play);
        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            videourl = bundle.getString("video_url");
            videoname = bundle.getString("video_name");
        }

        if (db.getEmotionUrl(videoname)==null){
            try {

                progressDialog = new ProgressDialog(this);
                progressDialog.setMessage("loading...");
                progressDialog.setTitle("Upload video");
                progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
                progressDialog.setCancelable(false);
                progressDialog.show();
                uploadFile(videourl.replace(".mp4", "_camera.mp4"));

            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        else{
            operation_location = db.getEmotionUrl(videoname).get(1);
            videoStart();
            System.out.println("EMOTION " + operation_location);
        }

        pieChart = (PieChart) findViewById(R.id.chart1);
        pieChart.setNoDataText("Press show diagram button");
        text = (TextView) findViewById(R.id.textAct);
        text.setText("Show diagram");
        text.setBackgroundColor(Color.parseColor("#37474F"));


        text.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    getEmotions();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });

    }

    private void videoStart() {
        video1 = (VideoView) findViewById(R.id.video1);
        video1.setVideoURI(Uri.parse(videourl));
        video1.setMediaController(new MediaController(this));
        video1.requestFocus();

        video1.start();
        video2 = (VideoView) findViewById(R.id.video2);
        video2.setVideoURI(Uri.parse(videourl.replace(".mp4", "_camera.mp4")));
        video2.setMediaController(new MediaController(this));
        video2.requestFocus();
        video2.start();
    }

    private void setPieData() {

        pieChart.setUsePercentValues(true);
        ArrayList<PieEntry> yvalues = new ArrayList<PieEntry>();
        if(neutral.floatValue()>0)yvalues.add(new PieEntry(neutral.floatValue(), "neutral"));
        if(happiness.floatValue()>0)yvalues.add(new PieEntry(happiness.floatValue(), "happiness"));
        if(surprise.floatValue()>0)yvalues.add(new PieEntry(surprise.floatValue(), "surprise"));
        if(sadness.floatValue()>0)yvalues.add(new PieEntry(sadness.floatValue(), "sadness"));
        if(anger.floatValue()>0)yvalues.add(new PieEntry(anger.floatValue(), "anger"));
        if(disgust.floatValue()>0)yvalues.add(new PieEntry(disgust.floatValue(), "disgust"));
        if(fear.floatValue()>0) yvalues.add(new PieEntry(fear.floatValue(), "fear"));
        if(contempt.floatValue()>0)yvalues.add(new PieEntry(contempt.floatValue(), "contempt"));
        PieDataSet dataSet = new PieDataSet(yvalues, "Election Results");
        PieData data = new PieData(dataSet);
        data.setValueFormatter(new PercentFormatter());
        pieChart.setCenterText("Emotions");


        ArrayList<Integer> colors = new ArrayList<Integer>();
        for (int c : ColorTemplate.VORDIPLOM_COLORS)
            colors.add(c);

        for (int c : ColorTemplate.JOYFUL_COLORS)
            colors.add(c);

        for (int c : ColorTemplate.COLORFUL_COLORS)
            colors.add(c);

        for (int c : ColorTemplate.LIBERTY_COLORS)
            colors.add(c);

        for (int c : ColorTemplate.PASTEL_COLORS)
            colors.add(c);
        dataSet.setColors(colors);
        dataSet.setValueLineWidth(20);
        dataSet.setValueTextSize(12);
        pieChart.setEntryLabelColor(Color.parseColor("#000000"));
        pieChart.getDescription().setEnabled(false);
        pieChart.getLegend().setEnabled(false);
        pieChart.setEntryLabelTextSize(12);
        pieChart.setCenterTextColor(Color.parseColor("#FFFFFF"));
        pieChart.setHoleColor(Color.parseColor("#000000"));
        pieChart.setData(data);
        pieChart.animateY(500);
    }

    private void uploadFile(String fileUri) throws IOException {
        FileUploadService service =
                ServiceGenerator.createService(FileUploadService.class);
        File file = new File(fileUri);
        InputStream in = new FileInputStream(file);
        byte[] buf = new byte[in.available()];
        while (in.read(buf) != -1) ;
        RequestBody requestBodyByte = RequestBody
                .create(MediaType.parse("application/octet-stream"), buf);

        Call<ResponseBody> call = service.videoTask("6fbfd6b0-bf19-4254-b4a4-078d92e7f1bf", requestBodyByte);
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                Log.d("Response", "Upload Success");
                operation_location = response.headers().get("Operation-Location");
                System.out.println(response.message());
                progressDialog.dismiss();
                db.addVideoEmotion(new VideoEmotion(videoname, operation_location, ""));
                videoStart();
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                Log.e("Error", t.getMessage());
            }


        });
    }

    private void getEmotions() throws IOException {
        video1.pause();
        video2.pause();
        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("loading...");
        progressDialog.setTitle("Diagram");
        progressDialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
        progressDialog.setCancelable(false);
        progressDialog.show();
        FileUploadService service =
                ServiceGenerator.createService(FileUploadService.class);
        Call<Emotion> call = service.getEmotion(operation_location, "64f96aed99cb4970bcb35f365d1d5746");
        call.enqueue(new Callback<Emotion>() {
            @Override
            public void onResponse(Call<Emotion> call, Response<Emotion> response) {
                Log.d("Response", "Success");

                Gson gson = new Gson();
                if(db.getEmotionUrl(videoname).get(2).equals("")){
                    if (response.message().equals("OK")) {
                        progressDialog.setProgress(response.body().getProgress().intValue());
                        if (response.body().getStatus().equals("Succeeded")) {
                            neutral = 0;
                            happiness = 0;
                            sadness = 0;
                            anger = 0;
                            disgust = 0;
                            fear = 0;
                            contempt = 0;
                            surprise = 0;
                            Emotions emotions = gson.fromJson(response.body().getProcessingResult(), Emotions.class);
                            List<Fragment> fragments = emotions.getFragments();
                            for (Fragment fragment : fragments) {
                                if(fragment.getEvents()!=null) {
                                    neutral += fragment.getEvents().get(0).get(0).getWindowFaceDistribution().getNeutral().intValue();
                                    happiness += fragment.getEvents().get(0).get(0).getWindowFaceDistribution().getHappiness().intValue();;
                                    sadness += fragment.getEvents().get(0).get(0).getWindowFaceDistribution().getSadness().intValue();;
                                    anger += fragment.getEvents().get(0).get(0).getWindowFaceDistribution().getAnger().intValue();;
                                    disgust += fragment.getEvents().get(0).get(0).getWindowFaceDistribution().getDisgust().intValue();;
                                    fear += fragment.getEvents().get(0).get(0).getWindowFaceDistribution().getFear().intValue();;
                                    contempt += fragment.getEvents().get(0).get(0).getWindowFaceDistribution().getContempt().intValue();;
                                    surprise += fragment.getEvents().get(0).get(0).getWindowFaceDistribution().getSurprise().intValue();;
                                }
                            }
                            db.updateEmotions(videoname, response.body().getProcessingResult());
                            progressDialog.dismiss();
                            setPieData();
                            video1.start();
                            video2.start();
                            text.setText("User reaction diagram:");
                            text.setBackgroundColor(Color.TRANSPARENT);

                        }  else {
                            call.clone().enqueue(this);
                            try {
                                Thread.sleep(12000);
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }
                        }
                    }  else {
                        call.clone().enqueue(this);
                        try {
                            Thread.sleep(12000);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                }
                else{
                    neutral = 0;
                    happiness = 0;
                    sadness = 0;
                    anger = 0;
                    disgust = 0;
                    fear = 0;
                    contempt = 0;
                    surprise = 0;
                    Emotions emotions = gson.fromJson(db.getEmotionUrl(videoname).get(2), Emotions.class);
                    List<Fragment> fragments = emotions.getFragments();
                    for (Fragment fragment : fragments) {
                        if(fragment.getEvents()!=null) {
                            neutral += fragment.getEvents().get(0).get(0).getWindowFaceDistribution().getNeutral().intValue();
                            happiness += fragment.getEvents().get(0).get(0).getWindowFaceDistribution().getHappiness().intValue();
                            sadness += fragment.getEvents().get(0).get(0).getWindowFaceDistribution().getSadness().intValue();
                            anger += fragment.getEvents().get(0).get(0).getWindowFaceDistribution().getAnger().intValue();
                            disgust += fragment.getEvents().get(0).get(0).getWindowFaceDistribution().getDisgust().intValue();
                            fear += fragment.getEvents().get(0).get(0).getWindowFaceDistribution().getFear().intValue();
                            contempt += fragment.getEvents().get(0).get(0).getWindowFaceDistribution().getContempt().intValue();
                            surprise += fragment.getEvents().get(0).get(0).getWindowFaceDistribution().getSurprise().intValue();
                        }
                    }
                    progressDialog.dismiss();
                    setPieData();
                    video1.start();
                    video2.start();
                    text.setText("User reaction diagram:");
                    text.setBackgroundColor(Color.TRANSPARENT);

                }

            }

            @Override
            public void onFailure(Call<Emotion> call, Throwable t) {
                Log.e("Error", t.getMessage());
                progressDialog.dismiss();
            }


        });
    }
}
