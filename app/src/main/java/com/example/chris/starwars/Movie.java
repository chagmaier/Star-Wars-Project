package com.example.chris.starwars;

import android.content.Context;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.io.InputStream;
import java.lang.reflect.Array;
import java.util.ArrayList;

/**
 * Created by Chris on 2/12/18.
 */

public class Movie {

    public String title;
    public String episode_number;
    public ArrayList<String> main_characters = new ArrayList<>();
    public String description;
    public String poster;
    public String url;


    public static ArrayList<Movie> getMoviesFromFile(String filename, Context context) {

        ArrayList<Movie> movieList = new ArrayList<Movie>();

        try{
            String jsonString = loadJsonFromAsset("movies.json", context);
            JSONObject json = new JSONObject(jsonString);
            JSONArray movies = json.getJSONArray("movies");


            for (int i = 0; i < movies.length(); i++){
                Movie movie = new Movie();
                movie.title = movies.getJSONObject(i).getString("title");
                movie.episode_number = movies.getJSONObject(i).getString("episode_number");

                JSONArray jsonCharacters = (JSONArray)movies.getJSONObject(i).get("main_characters");
                for(int j=0; j<jsonCharacters.length(); j++) {
                    movie.main_characters.add(j, jsonCharacters.getString(j));
                }

                movie.description = movies.getJSONObject(i).getString("description");
                movie.poster = movies.getJSONObject(i).getString("poster");
                movie.url = movies.getJSONObject(i).getString("url");
                // add to arraylist
                movieList.add(movie);

            }

        } catch (JSONException e) {
            e.printStackTrace();
        }
        return movieList;
    }

    private static String loadJsonFromAsset(String filename, Context context) {

        String json = null;

        try {
            InputStream is = context.getAssets().open(filename);
            int size = is.available();
            byte[] buffer = new byte[size];
            is.read(buffer);
            is.close();
            json = new String(buffer, "UTF-8");
        }
        catch (java.io.IOException ex) {
            ex.printStackTrace();
            return null;
        }
        return json;
    }
}
