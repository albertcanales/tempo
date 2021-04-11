package com.cappielloantonio.play.adapter;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.navigation.Navigation;
import androidx.recyclerview.widget.RecyclerView;

import com.cappielloantonio.play.R;
import com.cappielloantonio.play.glide.CustomGlideRequest;
import com.cappielloantonio.play.model.Album;

import java.util.ArrayList;
import java.util.List;

public class AlbumArtistPageAdapter extends RecyclerView.Adapter<AlbumArtistPageAdapter.ViewHolder> {
    private static final String TAG = "AlbumArtistPageAdapter";

    private List<Album> albums;
    private LayoutInflater inflater;
    private Context context;

    public AlbumArtistPageAdapter(Context context) {
        this.context = context;
        this.inflater = LayoutInflater.from(context);
        this.albums = new ArrayList<>();
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.item_artist_page_album, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Album album = albums.get(position);

        holder.textAlbumName.setText(album.getTitle());

        CustomGlideRequest.Builder
                .from(context, album.getPrimary(), album.getBlurHash(), CustomGlideRequest.PRIMARY, CustomGlideRequest.LOW_QUALITY)
                .build()
                .into(holder.cover);
    }

    @Override
    public int getItemCount() {
        return albums.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener, View.OnLongClickListener {
        TextView textAlbumName;
        ImageView cover;

        ViewHolder(View itemView) {
            super(itemView);

            textAlbumName = itemView.findViewById(R.id.album_name_label);
            cover = itemView.findViewById(R.id.artist_page_album_cover_image_view);

            itemView.setOnClickListener(this);
            itemView.setOnLongClickListener(this);
        }

        @Override
        public void onClick(View view) {
            Bundle bundle = new Bundle();
            bundle.putParcelable("album_object", albums.get(getAdapterPosition()));
            Navigation.findNavController(view).navigate(R.id.action_artistPageFragment_to_albumPageFragment, bundle);
        }

        @Override
        public boolean onLongClick(View v) {
            Bundle bundle = new Bundle();
            bundle.putParcelable("album_object", albums.get(getAdapterPosition()));
            Navigation.findNavController(v).navigate(R.id.albumBottomSheetDialog, bundle);
            return true;
        }
    }

    public Album getItem(int position) {
        return albums.get(position);
    }

    public void setItems(List<Album> albums) {
        this.albums = albums;
        notifyDataSetChanged();
    }
}