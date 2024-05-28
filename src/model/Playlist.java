package model;

import java.util.ArrayList;
import java.util.Objects;

public class Playlist {
    private final String name;
    private final User owner;
    private final ArrayList<Track> tracks;
    private static final ArrayList<Playlist> playlists = new ArrayList<>();
    private static Playlist currentPlaylist;

    public Playlist(String name, User owner) {
        this.name = name;
        this.owner = owner;
        this.tracks = new ArrayList<>();
    }

    public void addTrack(Track track) {
        this.tracks.add(track);
    }

    public void removeTrack(Track track) {
        this.tracks.remove(track);
    }

    public User getOwner() {
        return owner;
    }

    public int durationCalculator() {
        int result = 0;
        for (Track track : this.tracks)
            result += track.getDuration();
        return result;
    }

    public String getName() {
        return name;
    }

    public static void addPlaylist(Playlist playlist) {
        Playlist.playlists.add(playlist);
    }

    public static void removePlaylist(Playlist playlist) {
        Playlist.playlists.remove(playlist);
    }

    public static Playlist getPlaylistByName(String name) {
        for (Playlist playlist : Playlist.playlists)
            if (Objects.equals(playlist.name, name))
                return playlist;
        return null;
    }

    public static Playlist getCurrentPlaylist() {
        return Playlist.currentPlaylist;
    }

    public static void setCurrentPlaylist(Playlist playlist) {
        Playlist.currentPlaylist = playlist;
    }

    public ArrayList<Track> getTracks() {
        return tracks;
    }
}
