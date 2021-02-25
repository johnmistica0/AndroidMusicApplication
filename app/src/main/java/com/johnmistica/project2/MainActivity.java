/**
 * Author: John Mistica - Code template gathered from Blackboard
 * NetID: jmisti2
 * UIN: 660678902
 * Date: 2/23/21
 * Assignment: Project #2
 * Description: An application that displays the song name, album cover, and artist name.
 * There is an option menu to select to view songs in either a vertical list or a grid with two columns.
 * Each song can be short-clicked which will navigate the user to the songs music video.
 * A long-click will prompt the user with a context menu with three options which will lead
 * the user to either a music video, song titles wiki, and artist wiki respectively
 */

package com.johnmistica.project2;


import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;


import android.os.Bundle;
import android.os.Parcelable;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    //instantiating data structures that hold links, songs, and images
    ArrayList<String> songList;
    ArrayList<String> artistList;
    ArrayList<Integer> albumIds;
    ArrayList<String> urlList;
    ArrayList<String> wikiList;
    ArrayList<String> artistWikiList;
    RecyclerView songView;
    Boolean viewState = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        songView = (RecyclerView) findViewById(R.id.recycler_view);

        albumIds= new ArrayList<Integer>(Arrays.asList(R.drawable.image1, R.drawable.image2,R.drawable.image3, R.drawable.image4, R.drawable.image5,R.drawable.image6,
                R.drawable.image7, R.drawable.image8,R.drawable.image9, R.drawable.image10, R.drawable.image11,R.drawable.image12,R.drawable.image13,R.drawable.image14));

        urlList = new ArrayList<String>(Arrays.asList(
                "https://www.youtube.com/watch?v=ZmDBbnmKpqQ",
                "https://www.youtube.com/watch?v=B6_iQvaIjXw",
                "https://www.youtube.com/watch?v=zzd4ydafGR0",
                "https://www.youtube.com/watch?v=4NRXx6U8ABQ",
                "https://www.youtube.com/watch?v=rCiBgLOcuKU",
                "https://www.youtube.com/watch?v=XXYlFuWEuKI",
                "https://www.youtube.com/watch?v=GrAchTdepsU",
                "https://www.youtube.com/watch?v=dPhwbZBvW2o",
                "https://www.youtube.com/watch?v=TUVcZfQe-Kw",
                "https://www.youtube.com/watch?v=tcYodQoapMg",
                "https://www.youtube.com/watch?v=8xg3vE8Ie_E",
                "https://www.youtube.com/watch?v=uuodbSVO3z0",
                "https://www.youtube.com/watch?v=RUQl6YcMalg",
                "https://www.youtube.com/watch?v=Q9pjm4cNsfc"));

        wikiList = new ArrayList<String>(Arrays.asList(
                "https://en.wikipedia.org/wiki/Drivers_License_(song)",
                "https://en.wikipedia.org/wiki/34%2B35",
                "https://en.wikipedia.org/wiki/Calling_My_Phone",
                "https://en.wikipedia.org/wiki/Blinding_Lights",
                "https://en.wikipedia.org/wiki/Up_(Cardi_B_song)",
                "https://en.wikipedia.org/wiki/Save_Your_Tears",
                "https://en.wikipedia.org/wiki/Mood_(song)",
                "https://en.wikipedia.org/wiki/Go_Crazy_(Chris_Brown_and_Young_Thug_song)",
                "https://en.wikipedia.org/wiki/Levitating_(song)",
                "https://en.wikipedia.org/wiki/Positions_(song)",
                "https://en.wikipedia.org/wiki/Love_Story_(Taylor_Swift_song)",
                "https://en.wikipedia.org/wiki/What_You_Know_Bout_Love",
                "https://en.wikipedia.org/wiki/Therefore_I_Am_(song)",
                "https://en.wikipedia.org/wiki/For_the_Night"));

        artistWikiList = new ArrayList<String>(Arrays.asList(
                "https://en.wikipedia.org/wiki/Olivia_Rodrigo",
                "https://en.wikipedia.org/wiki/Ariana_Grande",
                "https://en.wikipedia.org/wiki/Lil_Tjay",
                "https://en.wikipedia.org/wiki/The_Weeknd",
                "https://en.wikipedia.org/wiki/Cardi_B",
                "https://en.wikipedia.org/wiki/The_Weeknd",
                "https://en.wikipedia.org/wiki/24kGoldn",
                "https://en.wikipedia.org/wiki/Chris_Brown",
                "https://en.wikipedia.org/wiki/Dua_Lipa",
                "https://en.wikipedia.org/wiki/Ariana_Grande",
                "https://en.wikipedia.org/wiki/Taylor_Swift",
                "https://en.wikipedia.org/wiki/Pop_Smoke",
                "https://en.wikipedia.org/wiki/Billie_Eilish",
                "https://en.wikipedia.org/wiki/Pop_Smoke"));


        songList = new ArrayList<String>(Arrays.asList("Drivers License", "34+35", "Calling My Phone", "Blinding Lights", "Up", "Save Your Tears", "Mood",
                "Go Crazy", "Levitating", "Positions", "Love Story", "What You Know Bout Love", "Therefore I Am", "For The Night"));

        artistList = new ArrayList<String>(Arrays.asList("Olivia Rodrigo", "Ariana Grande", "Lil Tjay ft. 6LACK", "The Weeknd", "Cardi B", "The Weeknd", "24kGoldn ft. iann dior",
                "Chris Brown & Young Thug", "Dua Lipa ft. DaBaby", "Ariana Grande", "Taylor Swift", "Pop Smoke", "Billie Eilish", "Pop Smoke ft. Lil Baby & DaBaby"));

        //defines listener with lambda and displays song title on a toast
        RVClickListener listener = (view, position) -> {};

        //instantiates and sets new dynamic adapter class and passes in data
        MyAdapter adapter = new MyAdapter(songList, artistList, albumIds, urlList, wikiList, artistWikiList, listener);
        songView.setHasFixedSize(true);
        songView.setAdapter(adapter);

        //keeps view consistent when changing orientation
        if(savedInstanceState != null){
            viewState = savedInstanceState.getBoolean("list_state");
        }

        //sets the recycleView as standard vertical linear view
        if(viewState){
            songView.setLayoutManager(new LinearLayoutManager(this));
        }
        else{
            songView.setLayoutManager(new GridLayoutManager(this,2));
        }



    }

    //inflates options top options menu
    @Override
    public boolean onCreateOptionsMenu(Menu menu){
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.top_menu, menu);
        return true;
    }

    //sets listener to each menu item between a grid or linear view to songs
    @Override
    public boolean onOptionsItemSelected(MenuItem item){
        switch(item.getItemId()){
            case R.id.menu1:
                songView.setLayoutManager(new LinearLayoutManager(this));
                viewState = true;
                return true;
            case R.id.menu2:
                songView.setLayoutManager(new GridLayoutManager(this,2));
                viewState = false;
                return true;
            default:
                return false;
        }
    }

    //saves the layout view when changing orientation
    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putBoolean("list_state", viewState);
    }

}