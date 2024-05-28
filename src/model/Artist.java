package model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Objects;

public class Artist {
    private final String username;
    private final String password;
    private final String nickname;
    private int followers;
    private final ArrayList<Track> tracks;
    private static Artist loggedInArtist;
    private static final ArrayList<Artist> artists = new ArrayList<>();

    public Artist(String username, String password, String nickname) {
        this.username = username;
        this.password = password;
        this.nickname = nickname;
        this.tracks = new ArrayList<>();
    }

    public static Artist getArtistByUsername(String username) {
        for (Artist artist : artists)
            if (Objects.equals(artist.username, username))
                return artist;
        return null;
    }

    public static void addArtist(Artist artist) {
        Artist.artists.add(artist);
    }

    public String getUsername() {
        return this.username;
    }

    public String getPassword() {
        return this.password;
    }

    public String getNickname() {
        return this.nickname;
    }

    public int getFollowers() {
        return this.followers;
    }

    public void setFollowers(int followers) {
        this.followers = followers;
    }

    public ArrayList<Track> getTracks() {
        return this.tracks;
    }

    public void addToTracks(Track track) {
        this.tracks.add(track);
    }

    public static Artist getLoggedInArtist() {
        return loggedInArtist;
    }

    public static void setLoggedInArtist(Artist artist) {
        Artist.loggedInArtist = artist;
    }

    public int rankCalculator() {
        for (int i = 0; i < artists.size(); i++) {
            for (int j = 0; j < artists.size(); j++) {
                if (i > j) {
                    Artist artist1 = artists.get(i);
                    Artist artist2 = artists.get(j);
                    if (artist1.followers > artist2.followers) {
                        artists.remove(j);
                        artists.add(j, artist1);
                        artists.remove(i);
                        artists.add(i, artist2);
                    }
                }
            }
        }
        HashMap<Artist, Integer> ranking = new HashMap<>();
        int rank = 1;
        for (int i = 0; i < artists.size(); i++) {
            Artist artist1 = artists.get(i);
            ranking.put(artist1, rank);
            int counter = 0;
            for (int j = i + 1; j < artists.size(); j++) {
                Artist artist2 = artists.get(j);
                if (artist1.followers == artist2.followers) {
                    ranking.put(artist2, rank);
                    counter++;
                } else
                    break;
            }
            i += counter;
            rank++;
        }
        return ranking.get(this);
    }
}
