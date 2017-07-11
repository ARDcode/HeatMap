package develop.heatmap.adapter;

import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import develop.heatmap.R;
import develop.heatmap.VideoPlayActivity;
import develop.heatmap.model.Video;


public class VideoAdapter extends RecyclerView.Adapter<VideoAdapter.MyViewHolder> {
    private List<Video> prototypesList;
    private VideoAdapter adapter;

    public VideoAdapter(List<Video> prototypesList) {
        this.prototypesList = prototypesList;
        this.adapter = this;
    }

    @Override
    public VideoAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.videogallery_item, parent, false);

        return new VideoAdapter.MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final VideoAdapter.MyViewHolder holder, final int position) {
        holder.getAdapterPosition();
        final Video prot = prototypesList.get(position);
        holder.name.setText(prot.getName());
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(view.getContext().getApplicationContext(), VideoPlayActivity.class);
                intent.putExtra("video_url", prot.getUrl());
                intent.putExtra("video_name", prot.getName());
                view.getContext().startActivity(intent);

            }
        });
        holder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                // TODO Auto-generated method stub

//                Log.v("long clicked", "pos: " + position);
//                final DBHelperPrototypes db = new DBHelperPrototypes(view.getContext());
//                db.deleteAll();
//                db.close();
//                prototypesList.clear();
//                File dir = new File(Environment.getExternalStorageDirectory().getAbsolutePath()+"/HeatMap/");
//                try {
//                    FileUtils.deleteDirectory(dir);
//                } catch (IOException e) {
//                    e.printStackTrace();
//                }
////                if (dir.isDirectory())
////                {
////                    String[] children = dir.list();
////                    for (int i = 0; i < children.length; i++)
////                    {
////                        new File(dir, children[i]).delete();
////                    }
////                }
//                adapter.notifyDataSetChanged();

                return true;
            }
        });
    }

    @Override
    public int getItemCount() {
        return prototypesList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView name, description, date;

        public MyViewHolder(View view) {
            super(view);
            name = (TextView) view.findViewById(R.id.video_name);

        }
    }
}

