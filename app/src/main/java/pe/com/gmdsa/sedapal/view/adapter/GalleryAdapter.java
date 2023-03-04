package pe.com.gmdsa.sedapal.view.adapter;

import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;

import pe.com.gmdsa.sedapal.R;


/**
 * Created by jmauriciog on 06/01/2016.
 */
public class GalleryAdapter extends RecyclerView.Adapter<GalleryAdapter.ViewHolder> {

    private ArrayList<String> paths;
    private int itemLayout;

    public GalleryAdapter(ArrayList<String> paths, int itemLayout) {
        this.paths = paths;
        this.itemLayout = itemLayout;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(itemLayout, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        String path = paths.get(position);
        holder.image.setImageBitmap(null);
        Glide.with(holder.image.getContext())
                .load(Uri.parse("file://" + path))
                .into(holder.image);
        holder.itemView.setTag(path);
    }

    @Override
    public int getItemCount() {
        return paths.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public ImageView image;

        public ViewHolder(View itemView) {
            super(itemView);
            image = (ImageView) itemView.findViewById(R.id.image);
        }
    }
}