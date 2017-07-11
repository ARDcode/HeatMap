package develop.heatmap.adapter;

import android.content.Intent;
import android.os.Environment;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;
import java.util.List;

import develop.heatmap.DBHelperPrototypes;
import develop.heatmap.model.Prototype;
import develop.heatmap.PrototypeUsers;
import develop.heatmap.R;

public class PrototypeAdapter extends RecyclerView.Adapter<PrototypeAdapter.MyViewHolder> {

    private List<Prototype> prototypesList;
    private PrototypeAdapter adapter;

    public PrototypeAdapter(List<Prototype> prototypesList) {
        this.prototypesList = prototypesList;
        this.adapter = this;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_prototype, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, final int position) {
        holder.getAdapterPosition();
        Prototype prot = prototypesList.get(position);
        holder.name.setText(prot.getName());
        holder.description.setText(prot.getDescription());
        holder.date.setText(prot.getDate_created());
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int url = prototypesList.get(position).getId();
                System.out.println("URLID" + url);
                Intent intent = new Intent(view.getContext().getApplicationContext(), PrototypeUsers.class);
                intent.putExtra("prot_url", url);
                intent.putExtra("prot_name", prototypesList.get(position).getName());
                view.getContext().startActivity(intent);

            }
        });
        holder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                // TODO Auto-generated method stub

                Log.v("long clicked", "pos: " + position);
                final DBHelperPrototypes db = new DBHelperPrototypes(view.getContext());
                db.deleteAll();
                db.close();
                prototypesList.clear();
                File dir = new File(Environment.getExternalStorageDirectory().getAbsolutePath()+"/HeatMap/");
                try {
                    FileUtils.deleteDirectory(dir);
                } catch (IOException e) {
                    e.printStackTrace();
                }
//                if (dir.isDirectory())
//                {
//                    String[] children = dir.list();
//                    for (int i = 0; i < children.length; i++)
//                    {
//                        new File(dir, children[i]).delete();
//                    }
//                }
                adapter.notifyDataSetChanged();

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
            name = (TextView) view.findViewById(R.id.prototype_name);
            description = (TextView) view.findViewById(R.id.prototype_description);
            date = (TextView) view.findViewById(R.id.prototype_date);
        }
    }
}