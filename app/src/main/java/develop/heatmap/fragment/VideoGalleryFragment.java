package develop.heatmap.fragment;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.os.Environment;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import develop.heatmap.R;
import develop.heatmap.adapter.GalleryAdapter;
import develop.heatmap.adapter.PrototypeAdapter;
import develop.heatmap.adapter.VideoAdapter;
import develop.heatmap.model.Prototype;
import develop.heatmap.model.Video;


public class VideoGalleryFragment extends Fragment{
    private ArrayList<Video> urls;
    private ProgressDialog pDialog;
    private VideoAdapter mAdapter;
    private RecyclerView recyclerView;
    private String prototype_user_name;
    private String prototype_name;

    public VideoGalleryFragment() {

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootview = inflater.inflate(R.layout.fragment_videogallery, container, false);
        prototype_user_name = getArguments().getString("prototypeUser_name");
        prototype_name = getArguments().getString("prototype_name");
        recyclerView = (RecyclerView) rootview.findViewById(R.id.video_recycler_view);

        pDialog = new ProgressDialog(getContext());
        urls = new ArrayList<Video>();
        mAdapter = new VideoAdapter(urls);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(mAdapter);
        videoUrls();
        return rootview;
    }
    private void videoUrls() {
        String path = Environment.getExternalStorageDirectory().toString() + "/HeatMap/" + prototype_name + "/" + prototype_user_name + "/";
        Log.d("Files", "Path: " + path);
        File directory = new File(path);
        File[] files = directory.listFiles();
        if(files!=null) {
            Log.d("Files", "Size: " + files.length);
            for (int i = 0; i < files.length; i++) {
                if ((!files[i].getName().contains("camera"))&((files[i].getPath().endsWith(".mp4"))))
                    urls.add(new Video(files[i].getName().replace(".mp4", ""), path + files[i].getName()));
            }
        }
        mAdapter.notifyDataSetChanged();
    }
}