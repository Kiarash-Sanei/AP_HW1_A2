package controller;

import model.Playlist;
import model.Track;
import model.User;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Objects;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class PlaylistMenu {
    public static void run(Scanner scanner) {
        String line = scanner.nextLine();
        Matcher matcher = PlaylistMenu.getCommandMatcher(line, "back|exit");
        while (!matcher.matches()) {
            matcher = PlaylistMenu.getCommandMatcher(line, "show tracks");
            if (matcher.matches()) {
                PlaylistMenu.showTracks();
                line = scanner.nextLine();
                matcher = PlaylistMenu.getCommandMatcher(line, "back|exit");
                continue;
            }
            matcher = PlaylistMenu.getCommandMatcher(line, "show duration");
            if (matcher.matches()) {
                PlaylistMenu.showDuration();
                line = scanner.nextLine();
                matcher = PlaylistMenu.getCommandMatcher(line, "back|exit");
                continue;
            }
            matcher = PlaylistMenu.getCommandMatcher(line, "add -t (?<trackName>[a-zA-Z0-9 ]+)");
            if (matcher.matches()) {
                PlaylistMenu.addTrack(matcher);
                line = scanner.nextLine();
                matcher = PlaylistMenu.getCommandMatcher(line, "back|exit");
                continue;
            }
            matcher = PlaylistMenu.getCommandMatcher(line, "remove -t (?<trackName>[a-zA-Z0-9 ]+)");
            if (matcher.matches()) {
                PlaylistMenu.removeTrack(matcher);
                line = scanner.nextLine();
                matcher = PlaylistMenu.getCommandMatcher(line, "back|exit");
                continue;
            }
            matcher = PlaylistMenu.getCommandMatcher(line, "show menu name");
            if (matcher.matches()) {
                System.out.println("playlist menu");
                line = scanner.nextLine();
                matcher = PlaylistMenu.getCommandMatcher(line, "back|exit");
                continue;
            }
            System.out.println("invalid command");
            line = scanner.nextLine();
            matcher = PlaylistMenu.getCommandMatcher(line, "back|exit");

        }
        if (Objects.equals(line, "back"))
            return;
        System.exit(0);
    }

    private static Matcher getCommandMatcher(String input, String regex) {
        return Pattern.compile(regex).matcher(input);

    }

    private static void showTracks() {
        Playlist playlist = Playlist.getCurrentPlaylist();
        ArrayList<Track> tracks = playlist.getTracks();
        for (int i = 0; i < tracks.size(); i++) {
            for (int j = i + 1; j < tracks.size(); j++) {
                Track track1 = tracks.get(i);
                Track track2 = tracks.get(j);
                if (track1.getReleseDate().before(track2.getReleseDate()))
                    Collections.swap(tracks, i, j);
                else if (Objects.equals(track1.getReleseDate(), track2.getReleseDate()) &&
                        track1.getName().compareTo(track2.getName()) > 0)
                    Collections.swap(tracks, i, j);
            }
        }
        for (Track track : tracks)
            System.out.println(track.getName());
    }

    private static void showDuration() {
        System.out.println(Playlist.getCurrentPlaylist().durationCalculator());
    }

    private static void addTrack(Matcher matcher) {
        User user = User.getLoggedInUser();
        Playlist playlist = Playlist.getCurrentPlaylist();
        Track track = Track.getTrackByName(matcher.group("trackName"));
        if (!Objects.equals(user, playlist.getOwner())) {
            System.out.println("user doesn't own this playlist");
            return;
        }
        if (track == null) {
            System.out.println("no such track");
            return;
        }
        if (playlist.getTracks().contains(track)) {
            System.out.println("track is already in the playlist");
            return;
        }
        playlist.addTrack(track);
        System.out.println("track added to playlist successfully");
    }

    private static void removeTrack(Matcher matcher) {
        Playlist playlist = Playlist.getCurrentPlaylist();
        Track track = Track.getTrackByName(matcher.group("trackName"));
        if (!playlist.getTracks().contains(track)) {
            System.out.println("no such track in playlist");
            return;
        }
        playlist.removeTrack(track);
        System.out.println("track removed from playlist successfully");
    }
}
