package core.myapplication;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.Collections;
import java.util.List;

/**
 * Created by A.Barredo on 02/12/2015.
 * Student of Deusto University
 */
public class NavDrawerAdapter extends RecyclerView.Adapter<NavDrawerAdapter.MyViewHolder> {
    private LayoutInflater inflater;
    //private ClickListener clickListener;
    Context context;
    List<DrawerItems> items = Collections.emptyList();

    public NavDrawerAdapter(Context context, List<DrawerItems> items){
        this.context = context;
        this.items = items;
        inflater = LayoutInflater.from(context);
    }
    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.recycler_row,parent,false);

        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        DrawerItems current = items.get(position);
        holder.title.setText(current.title);
        holder.icon.setImageResource(current.icon);
    }
    @Override
    public int getItemCount() {
        return items.size();
    }
    //need to be added if using the ClickListener Interface --> implements View.OnClickListener
    class MyViewHolder extends RecyclerView.ViewHolder{
        TextView title;
        ImageView icon;
        public MyViewHolder(View itemView) {
            super(itemView);
            title = (TextView) itemView.findViewById(R.id.recyclerTitle);
            icon = (ImageView) itemView.findViewById(R.id.recyclerIcon);
            //itemView.setOnClickListener(this);
        }

    }

}
