package develop.heatmap;

import android.content.Intent;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Calendar;

import develop.heatmap.model.PrototypeUser;

public class AddUserActivity extends AppCompatActivity {
    private int prototype_id;
    private String _prototype_name;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_user);
        final DBHelperPrototypes db = new DBHelperPrototypes(this);
        final EditText prototype_name = (EditText) findViewById(R.id.input_user_name);
        final EditText prototype_bio = (EditText) findViewById(R.id.input_user_bio);
        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            prototype_id = bundle.getInt("prototype_id");
            _prototype_name = bundle.getString("prototype_name");
        }
        System.out.println("Атеперь " + prototype_id);
        Button add_prototype = (Button) findViewById(R.id.btn_add_user);
        add_prototype.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Calendar c = Calendar.getInstance();
                SimpleDateFormat sdf = new SimpleDateFormat("dd.MM.yy");
                String strDate = sdf.format(c.getTime());
                db.addPrototypeUser(new PrototypeUser(prototype_bio.getText().toString(), prototype_name.getText().toString(), strDate, "", prototype_id));
                Log.d("fff", "Ready");
                Intent intent = new Intent(getApplicationContext(), PrototypeUsers.class);
                intent.putExtra("prot_url", prototype_id);
                intent.putExtra("prot_name", _prototype_name);
                startActivity(intent);
                try {
                    String file_path = Environment.getExternalStorageDirectory().getAbsolutePath() +
                            "/HeatMap/" + _prototype_name +"/"+prototype_name.getText();
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
