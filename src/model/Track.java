package model;

import java.util.ArrayList;
import java.util.Date;
import java.util.Objects;

public class Track {
    private final String name;
    private final Artist artist;
    private final int duration;
    private final Date releseDate;
    private final boolean type;
    private static final ArrayList<Track> tracks = new ArrayList<>();

    public Track(String name, Artist artist, int duration, Date releseDate, boolean type) {
        this.name = name;
        this.artist = artist;
        this.duration = duration;
        this.releseDate = releseDate;
        this.type = type;
    }

    public String getName() {
        return name;
    }

    public Artist getArtist() {
        return artist;
    }

    public int getDuration() {
        return duration;
    }

    public Date getReleseDate() {
        return releseDate;
    }

    public boolean getType() {
        return type;
    }

    public static void addTrack(Track track) {
        Track.tracks.add(track);
    }

    public static ArrayList<Track> getTracks() {
        return Track.tracks;
    }

    public static Track getTrackByName(String name) {
        for (Track track : tracks)
            if (Objects.equals(track.name, name))
                return track;
        return null;
    }

}
