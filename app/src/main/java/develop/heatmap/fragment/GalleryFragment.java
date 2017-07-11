package develop.heatmap.fragment;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.os.Environment;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.io.File;
import java.util.ArrayList;

import develop.heatmap.R;
import develop.heatmap.activity.GalleryActivity;
import develop.heatmap.activity.SlideshowDialogFragment;
import develop.heatmap.adapter.GalleryAdapter;


public class GalleryFragment extends Fragment {
    private ArrayList<String> urls;
    private ProgressDialog pDialog;
    private GalleryAdapter mAdapter;
    private RecyclerView recyclerView;
    private String prototype_user_name;
    private String prototype_name;

    public GalleryFragment() {

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    private void fetchImages() {
        String path = Environment.getExternalStorageDirectory().toString() + "/HeatMap/" + prototype_name + "/" + prototype_user_name + "/";
        Log.d("Files", "Path: " + path);
        File directory = new File(path);
        File[] files = directory.listFiles();
        if(files!=null) {
            Log.d("Files", "Size: " + files.length);
            for (int i = 0; i < files.length; i++) {
                if ((!files[i].getName().contains("screen"))&((files[i].getPath().endsWith(".jpeg"))))
                    urls.add(path + files[i].getName());
            }
        }
        mAdapter.notifyDataSetChanged();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootview = inflater.inflate(R.layout.fragment_gallery, container, false);
        prototype_user_name = getArguments().getString("prototypeUser_name");
        prototype_name = getArguments().getString("prototype_name");
        recyclerView = (RecyclerView) rootview.findViewById(R.id.recycler_view);

        pDialog = new ProgressDialog(getContext());
        urls = new ArrayList<>();
        mAdapter = new GalleryAdapter(getContext(), urls);

        RecyclerView.LayoutManager mLayoutManager = new GridLayoutManager(getContext(), 2);
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(mAdapter);

        recyclerView.addOnItemTouchListener(new GalleryAdapter.RecyclerTouchListener(getContext(), recyclerView, new GalleryAdapter.ClickListener() {
            @Override
            public void onClick(View view, int position) {
                Bundle bundle = new Bundle();
                bundle.putSerializable("images", urls);
                bundle.putInt("position", position);

                FragmentTransaction ft = getActivity().getSupportFragmentManager().beginTransaction();
                SlideshowDialogFragment newFragment = SlideshowDialogFragment.newInstance();
                newFragment.setArguments(bundle);
                newFragment.show(ft, "slideshow");
            }

            @Override
            public void onLongClick(View view, int position) {

            }
        }));

        fetchImages();
        return rootview;
    }

}