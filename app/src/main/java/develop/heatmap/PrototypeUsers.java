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
import android.widget.TextView;

import java.util.List;

import develop.heatmap.adapter.PrototypeUserAdapter;
import develop.heatmap.model.PrototypeUser;

public class PrototypeUsers extends AppCompatActivity {
    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    private List<PrototypeUser> prototypeUsers_list;
    private int prototype_url;
    private String prototype_name;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_prototype_users);
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
        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            prototype_url = bundle.getInt("prot_url");
            prototype_name = bundle.getString("prot_name");
        }
        ab.setTitle(prototype_name);
        TextView title = (TextView)findViewById(R.id.toolbar_title);
        title.setText(prototype_name);
        System.out.println("Атеперь " + prototype_url);
        Button btn_add = (Button)findViewById(R.id.btn_add_user);
        //db.deleteAll();
        prototypeUsers_list = db.getPrototypeUsers(prototype_url);
        mRecyclerView = (RecyclerView) findViewById(R.id.prototype_users_list);
        mAdapter = new PrototypeUserAdapter(prototypeUsers_list);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());
        mRecyclerView.setAdapter(mAdapter);
        btn_add.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                // db.addPrototype(new Prototype("http:/blabla",  "Test", "Description test", "Date", "Users"));
                //Log.d("fff", "Ready");
                Intent intent = new Intent(getApplicationContext(), AddUserActivity.class);
                intent.putExtra("prototype_id", prototype_url);
                intent.putExtra("prototype_name", prototype_name);
                // intent.putExtra(EXTRA_MESSAGE, message);
                startActivity(intent);

            }
        });
    }
    @Override
    public void onResume()
    {
        super.onResume();
        mAdapter.notifyDataSetChanged();
    }
    @Override
    public void onBackPressed() {
        super.onBackPressed();
//        startActivity(new Intent(getApplicationContext(), Prototypes.class));
    }
}
