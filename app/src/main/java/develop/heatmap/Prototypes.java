package develop.heatmap;

import android.content.Intent;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;

import java.util.List;

import develop.heatmap.adapter.PrototypeAdapter;
import develop.heatmap.model.Prototype;

public class Prototypes extends AppCompatActivity {
    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    private List<Prototype> prototypes_list;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.activity_prototypes);
        setContentView(R.layout.activity_prototypes); // an example activity_main.xml is provided below
        final DBHelperPrototypes db = new DBHelperPrototypes(this);
        // Always cast your custom Toolbar here, and set it as the ActionBar.
        Toolbar tb = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(tb);

        // Get the ActionBar here to configure the way it behaves.
        final ActionBar ab = getSupportActionBar();
        //ab.setHomeAsUpIndicator(R.drawable.ic_menu); // set a custom icon for the default home button
        ab.setDisplayShowHomeEnabled(false); // show or hide the default home button
        ab.setDisplayHomeAsUpEnabled(false);
        ab.setDisplayShowCustomEnabled(true); // enable overriding the default toolbar layout
        ab.setDisplayShowTitleEnabled(false); // disable the default title element here (for centered title)
        Button btn_add = (Button)findViewById(R.id.btn_add);
        prototypes_list = db.getPrototypes();
        mRecyclerView = (RecyclerView) findViewById(R.id.prototypes_list);
        mAdapter = new PrototypeAdapter(prototypes_list);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());
        mRecyclerView.setAdapter(mAdapter);
        btn_add.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), AddPrototypeActivity.class);
                startActivity(intent);
            }
        });

        System.out.println( mAdapter.getItemCount());

    }
    @Override
    public void onResume()
    {
        super.onResume();
        mAdapter.notifyDataSetChanged();
    }
}
