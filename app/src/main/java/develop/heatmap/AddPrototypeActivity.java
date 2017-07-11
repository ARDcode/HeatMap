package develop.heatmap;

import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Calendar;

import develop.heatmap.model.Prototype;

public class AddPrototypeActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_prototype);
        final DBHelperPrototypes db = new DBHelperPrototypes(this);
        final EditText prototype_name = (EditText) findViewById(R.id.input_name);
        final EditText prototype_url = (EditText) findViewById(R.id.input_url);
        final EditText prototype_description = (EditText) findViewById(R.id.input_description);
        Button add_prototype = (Button) findViewById(R.id.btn_add_prototype);
        add_prototype.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Calendar c = Calendar.getInstance();
                SimpleDateFormat sdf = new SimpleDateFormat("dd.MM.yy");
                String strDate = sdf.format(c.getTime());
                db.addPrototype(new Prototype(prototype_url.getText().toString(), prototype_name.getText().toString(), prototype_description.getText().toString(), strDate, ""));
                Log.d("fff", "Ready");
                startActivity(new Intent(getApplicationContext(), Prototypes.class));
                try {
                    String file_path = Environment.getExternalStorageDirectory().getAbsolutePath() +
                            "/HeatMap/" + prototype_name.getText();
                    File dir = new File(file_path);
                    if (!dir.exists())
                        dir.mkdirs();
                    Toast.makeText(view.getContext(), "Ready", Toast.LENGTH_SHORT).show();
                } catch (Exception e) {
                    e.printStackTrace();
                } finally {

                }
            }
        });
    }
}
