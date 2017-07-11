package develop.heatmap.activity;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.os.Environment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;



import java.io.File;
import java.util.ArrayList;

import develop.heatmap.R;
import develop.heatmap.adapter.GalleryAdapter;


public class GalleryActivity extends AppCompatActivity {

    private String TAG = GalleryActivity.class.getSimpleName();
    private ArrayList<String> urls;
    private ProgressDialog pDialog;
    private GalleryAdapter mAdapter;
    private RecyclerView recyclerView;
    private String prototype_user_name;
    private String prototype_name;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gallery);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            prototype_user_name = bundle.getString("prototypeUser_name");
            prototype_name = bundle.getString("prototype_name");
        }
        recyclerView = (RecyclerView) findViewById(R.id.recycler_view);

        pDialog = new ProgressDialog(this);
        urls = new ArrayList<>();
        mAdapter = new GalleryAdapter(getApplicationContext(), urls);

        RecyclerView.LayoutManager mLayoutManager = new GridLayoutManager(getApplicationContext(), 2);
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(mAdapter);

        recyclerView.addOnItemTouchListener(new GalleryAdapter.RecyclerTouchListener(getApplicationContext(), recyclerView, new GalleryAdapter.ClickListener() {
            @Override
            public void onClick(View view, int position) {
                Bundle bundle = new Bundle();
                bundle.putSerializable("images", urls);
                bundle.putInt("position", position);

                FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
                SlideshowDialogFragment newFragment = SlideshowDialogFragment.newInstance();
                newFragment.setArguments(bundle);
                newFragment.show(ft, "slideshow");
            }

            @Override
            public void onLongClick(View view, int position) {

            }
        }));

        fetchImages();
    }

    private void fetchImages() {
        String path = Environment.getExternalStorageDirectory().toString()+"/HeatMap/"+prototype_name+"/"+prototype_user_name+"/";
        Log.d("Files", "Path: " + path);
        File directory = new File(path);
        File[] files = directory.listFiles();
        Log.d("Files", "Size: "+ files.length);
        for (int i = 0; i < files.length; i++)
        {
            if ((!files[i].getName().contains("screen"))&(files[i].getPath().endsWith(".jpeg"))) {
                urls.add(path + files[i].getName());
                Log.d("FileUrl", "Url: "+ path + files[i].getName());
            }
        }
        mAdapter.notifyDataSetChanged();
    }
}