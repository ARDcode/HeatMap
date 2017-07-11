package develop.heatmap.adapter;

import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import develop.heatmap.DBHelperPrototypes;
import develop.heatmap.activity.TabGalleryActivity;
import develop.heatmap.model.PrototypeUser;
import develop.heatmap.R;

public class PrototypeUserAdapter extends RecyclerView.Adapter<PrototypeUserAdapter.MyViewHolder> {

    private List<PrototypeUser> prototypeUsersList;

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView name, bio, date;

        public MyViewHolder(View view) {
            super(view);
            name = (TextView) view.findViewById(R.id.prototype_name);
            bio = (TextView) view.findViewById(R.id.prototype_description);
            date = (TextView) view.findViewById(R.id.prototype_date);
        }
    }


    public PrototypeUserAdapter(List<PrototypeUser> prototypeUsersList) {
        this.prototypeUsersList = prototypeUsersList;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_user, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, final int position) {
        holder.getAdapterPosition();
        PrototypeUser prot = prototypeUsersList.get(position);
        holder.name.setText(prot.getName());
        holder.bio.setText(prot.getBio());
        holder.date.setText(prot.getDate_created());
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final DBHelperPrototypes db = new DBHelperPrototypes(view.getContext());

                Intent intent = new Intent(view.getContext().getApplicationContext(), TabGalleryActivity.class);

                intent.putExtra("prototypeUser_id", String.valueOf(prototypeUsersList.get(position).getId()));
                intent.putExtra("prototype_url", db.getPrototypeUrl(prototypeUsersList.get(position).getPrototype_id()));
                intent.putExtra("prototypeUser_name", String.valueOf(prototypeUsersList.get(position).getName()));
                intent.putExtra("prototype_name", db.getName(String.valueOf(prototypeUsersList.get(position).getPrototype_id())));
//                Intent intent = new Intent(view.getContext().getApplicationContext(), GalleryActivity.class);
//                intent.putExtra("prototypeUser_name", String.valueOf(prototypeUsersList.get(position).getName()));
//                intent.putExtra("prototype_name", db.getName(String.valueOf(prototypeUsersList.get(position).getPrototype_id())));
//                System.out.println(prototypeUsersList.get(position).getPrototype_id());
                // intent.putExtra(EXTRA_MESSAGE, message);
                db.close();
                view.getContext().startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return prototypeUsersList.size();
    }
}