/**
 * Author: John Mistica - Template gathered from blackboard
 * Description: MyAdapter class defines adapter for recycle view to create a dynamic list which displays widgets and configures menu options
 */

package com.johnmistica.project2;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class MyAdapter extends RecyclerView.Adapter<com.johnmistica.project2.MyAdapter.ViewHolder> {

    private ArrayList<String> songList; //data: the name of songs
    private ArrayList<String> artistList; //data: the names of artists
    private List<Integer> albumIds; //data: the images of each album
    private List<String> urlList; //data: the url links of each music video
    private List<String> wikiList; //data: the wiki links to each song
    private List<String> artistWikiLinks; //data: the wiki links to each artist
    private RVClickListener RVlistener;
    private Context context;


    //constructor that passes in data from main activity
    public MyAdapter(ArrayList<String> theList, ArrayList<String> theList2, List<Integer> ids, List<String> urls, List<String> wikiLinks, List<String> artistWikis, RVClickListener listener){
        songList = theList;
        artistList = theList2;
        albumIds = ids;
        urlList = urls;
        wikiList = wikiLinks;
        artistWikiLinks = artistWikis;
        this.RVlistener = listener;

    }

    //inflates viewHolders and returns it
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);
        View listView = inflater.inflate(R.layout.rv_item, parent, false);
        ViewHolder viewHolder = new ViewHolder(listView, RVlistener);

        return viewHolder;
    }

    //binds text and images to each ViewHolder
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.songName.setText(songList.get(position));
        holder.artistName.setText(artistList.get(position));
        holder.image.setImageResource(albumIds.get(position));

    }

    @Override
    public int getItemCount() {
        return songList.size();
    }


    //creates wrapper object around a view which contains layout for each item in the list
    //adds clickable functionality onto each ViewHolder
    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener, View.OnCreateContextMenuListener{

        //widget variable instantiation
        public TextView songName;
        public TextView artistName;
        public ImageView image;
        private RVClickListener listener;
        private View itemView;


        public ViewHolder(@NonNull View itemView, RVClickListener passedListener) {
            super(itemView);

            //sets variables to widgets
            songName = (TextView) itemView.findViewById(R.id.textView);
            artistName = (TextView) itemView.findViewById(R.id.textView2);
            image = (ImageView) itemView.findViewById(R.id.imageView);

            //sets context menu for each ViewHolder
            this.itemView = itemView;
            itemView.setOnCreateContextMenuListener(this);
            this.listener = passedListener;

            //short click listener
            itemView.setOnClickListener(this);
        }

        //Short click on each song leads to music video link
        @Override
        public void onClick(View v) {
            String url = "";
            listener.onClick(v, getAdapterPosition());
            Log.i("ON_CLICK", "in the onclick in view holder");
            Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(urlList.get(getAdapterPosition())));
            v.getContext().startActivity(intent);
        }

        //creates context menu and assigns listeners to each menu item
        @Override
        public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {

            //inflates menu from xml
            MenuInflater inflater = new MenuInflater(v.getContext());
            inflater.inflate(R.menu.context_menu, menu );

            //assigns listener to each menu item
            //1st link to music video, 2nd link to song wiki, 3rd link to artist wiki
            menu.getItem(0).setOnMenuItemClickListener(x -> {
                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(urlList.get(getAdapterPosition())));
                v.getContext().startActivity(intent);
                return true;
            });
            menu.getItem(1).setOnMenuItemClickListener(x -> {
                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(wikiList.get(getAdapterPosition())));
                v.getContext().startActivity(intent);
                return true;
            });
            menu.getItem(2).setOnMenuItemClickListener(x -> {
                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(artistWikiLinks.get(getAdapterPosition())));
                v.getContext().startActivity(intent);
                return true;
            });

        }

        //listener for menu items clicked
        private final MenuItem.OnMenuItemClickListener onMenu = new MenuItem.OnMenuItemClickListener(){
            @Override
            public boolean onMenuItemClick(MenuItem item){
                Log.i("ON_CLICK", songName.getText() + " adapter pos: " + getAdapterPosition());
                return true;
            }
        };




    }
}
