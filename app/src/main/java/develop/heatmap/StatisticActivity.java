package develop.heatmap;

import android.graphics.Color;
import android.os.Bundle;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;


import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.formatter.PercentFormatter;
import com.github.mikephil.charting.utils.ColorTemplate;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

import okhttp3.Headers;
import okhttp3.MediaType;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class StatisticActivity extends AppCompatActivity {
    private String operation_location;
    private PieChart mChart;
    protected String[] mParties = new String[]{
            "Party A", "Party B", "Party C", "Party D", "Party E", "Party F", "Party G", "Party H",
            "Party I", "Party J", "Party K", "Party L", "Party M", "Party N", "Party O", "Party P",
            "Party Q", "Party R", "Party S", "Party T", "Party U", "Party V", "Party W", "Party X",
            "Party Y", "Party Z"
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_statistic);
        try {
            uploadFile(Environment.getExternalStorageDirectory().getAbsolutePath() +
                    "/HeatMap/Marvel/test/Test.mp4");
        } catch (IOException e) {
            e.printStackTrace();
        }
        PieChart pieChart = (PieChart) findViewById(R.id.chart1);

        pieChart.setUsePercentValues(true);
        ArrayList<PieEntry> yvalues = new ArrayList<PieEntry>();
        yvalues.add(new PieEntry(8f, "neutral"));
        yvalues.add(new PieEntry(15f, "happiness"));
        yvalues.add(new PieEntry(12f, "surprise"));
        yvalues.add(new PieEntry(0f, "sadness"));
        yvalues.add(new PieEntry(23f, "anger"));
        yvalues.add(new PieEntry(0f, "disgust"));
        yvalues.add(new PieEntry(17f, "fear"));
        yvalues.add(new PieEntry(17f, "contempt"));
        PieDataSet dataSet = new PieDataSet(yvalues, "Election Results");
        PieData data = new PieData(dataSet);
        data.setValueFormatter(new PercentFormatter());
        pieChart.setCenterText("Test");
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

        Call<ResponseBody> call = service.videoTask("b7b70297455a468e9acba86b54a72fbf", requestBodyByte);
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                Log.d("Response", "Success");
                operation_location = response.headers().get("Operation-Location");
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                Log.e("Error", t.getMessage());
            }


        });
    }
}



