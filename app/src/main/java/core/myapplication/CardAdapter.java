package core.myapplication;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.VideoView;

import java.io.File;

/**
 * Created by A.Barredo on 17/12/2015.
 * Student of Deusto University
 */
public class CardAdapter extends RecyclerView.Adapter<CardAdapter.MyViewHolder> {
    private LayoutInflater inflater;
    Context context;
    Trick trick;

    public CardAdapter(Context context, Trick trick){
        this.context = context;
        this.trick= trick;
        inflater = LayoutInflater.from(context);
    }
    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.recycler_card,parent,false);
        return new MyViewHolder(view,context);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        //holder.videoUri = trick.getVideofile();
        holder.skateUri = trick.getSkateBoardPhoto();
        holder.graphUri = trick.getGraph();
    }
    @Override
    public int getItemCount() {
        return 1;
    }
    //need to be added if using the ClickListener Interface --> implements View.OnClickListener
    class MyViewHolder extends RecyclerView.ViewHolder{
        Uri videoUri;
        Uri skateUri;
        Uri graphUri;
        VideoView video;
        ImageView skate;
        ImageView grafica;
        public MyViewHolder(View itemView,Context context) {
            super(itemView);
            //video = (VideoView) itemView.findViewById(R.id.card_video);
            skate = (ImageView) itemView.findViewById(R.id.card_skate_image);
            grafica = (ImageView) itemView.findViewById(R.id.card_graph_image);

            //video.setMediaController(new MediaController(context));
            //video.setVideoURI(videoUri);
            if(findImage(skateUri)!=null) {
                skate.setImageBitmap(findImage(skateUri));
            }
            if(findImage(graphUri)!=null) {
                skate.setImageBitmap(findImage(graphUri));
            }
        }

    }
    public Bitmap findImage(Uri uri){
        String path = uri.getPath().toString();
        File imgFile = new File(path);
        if(imgFile.exists()){
            Bitmap myBitmap = BitmapFactory.decodeFile(imgFile.getAbsolutePath());
            return myBitmap;
        }else{
            return null;
        }
    }

}
