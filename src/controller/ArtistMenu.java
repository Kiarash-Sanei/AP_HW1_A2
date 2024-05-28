package controller;

import model.Artist;
import model.Track;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ArtistMenu {
    public static void run(Scanner scanner) throws ParseException {
        String line = scanner.nextLine();
        Matcher matcher = ArtistMenu.getCommandMatcher(line, "back|exit");
        while (!matcher.matches()) {
            matcher = ArtistMenu.getCommandMatcher(line, "show tracks");
            if (matcher.matches()) {
                ArtistMenu.showTracks();
                line = scanner.nextLine();
                matcher = ArtistMenu.getCommandMatcher(line, "back|exit");
                continue;
            }
            matcher = ArtistMenu.getCommandMatcher(line, "show songs");
            if (matcher.matches()) {
                ArtistMenu.showSongs();
                line = scanner.nextLine();
                matcher = ArtistMenu.getCommandMatcher(line, "back|exit");
                continue;
            }
            matcher = ArtistMenu.getCommandMatcher(line, "show podcasts");
            if (matcher.matches()) {
                ArtistMenu.showPodcasts();
                line = scanner.nextLine();
                matcher = ArtistMenu.getCommandMatcher(line, "back|exit");
                continue;
            }
            matcher = ArtistMenu.getCommandMatcher(line, "release -n (?<trackName>[a-zA-Z0-9 ]+) -t (?<type>podcast|song) -d (?<duration>\\d+) -r (?<releaseDate>\\d{4}/\\d{2}/\\d{2})");
            if (matcher.matches()) {
                ArtistMenu.releaseTrack(matcher);
                line = scanner.nextLine();
                matcher = ArtistMenu.getCommandMatcher(line, "back|exit");
                continue;
            }
            matcher = ArtistMenu.getCommandMatcher(line, "num of followers");
            if (matcher.matches()) {
                ArtistMenu.getNumberOfFollowers();
                line = scanner.nextLine();
                matcher = ArtistMenu.getCommandMatcher(line, "back|exit");
                continue;
            }
            matcher = ArtistMenu.getCommandMatcher(line, "get rank");
            if (matcher.matches()) {
                ArtistMenu.getRank();
                line = scanner.nextLine();
                matcher = ArtistMenu.getCommandMatcher(line, "back|exit");
                continue;
            }
            matcher = ArtistMenu.getCommandMatcher(line, "show menu name");
            if (matcher.matches()) {
                System.out.println("artist menu");
                line = scanner.nextLine();
                matcher = ArtistMenu.getCommandMatcher(line, "back|exit");
                continue;
            }
            System.out.println("invalid command");
            line = scanner.nextLine();
            matcher = ArtistMenu.getCommandMatcher(line, "back|exit");
        }
        if (Objects.equals(line, "back"))
            return;
        System.exit(0);
    }

    private static Matcher getCommandMatcher(String input, String regex) {
        return Pattern.compile(regex).matcher(input);
    }

    private static void showTracks() {
        Artist artist = Artist.getLoggedInArtist();
        ArrayList<Track> tracks = artist.getTracks();
        for (int i = 0; i < tracks.size(); i++) {
            for (int j = 0; j < tracks.size(); j++) {
                if (i > j) {
                    Track track1 = tracks.get(i);
                    Track track2 = tracks.get(j);
                    if (track1.getReleseDate().after(track2.getReleseDate())) {
                        tracks.remove(j);
                        tracks.add(j, track1);
                        tracks.remove(i);
                        tracks.add(i, track2);
                    } else if (Objects.equals(track1.getReleseDate(), track2.getReleseDate()) &&
                            track1.getName().compareToIgnoreCase(track2.getName()) < 0) {
                        tracks.remove(j);
                        tracks.add(j, track1);
                        tracks.remove(i);
                        tracks.add(i, track2);
                    }
                }

            }
        }
        for (Track track : tracks)
            System.out.println(track.getName());
    }

    private static void showSongs() {
        Artist artist = Artist.getLoggedInArtist();
        ArrayList<Track> tracks = artist.getTracks();
        for (int i = 0; i < tracks.size(); i++) {
            for (int j = 0; j < tracks.size(); j++) {
                if (i > j) {
                    Track track1 = tracks.get(i);
                    Track track2 = tracks.get(j);
                    if (track1.getReleseDate().after(track2.getReleseDate())) {
                        tracks.remove(j);
                        tracks.add(j, track1);
                        tracks.remove(i);
                        tracks.add(i, track2);
                    } else if (Objects.equals(track1.getReleseDate(), track2.getReleseDate()) &&
                            track1.getName().compareToIgnoreCase(track2.getName()) < 0) {
                        tracks.remove(j);
                        tracks.add(j, track1);
                        tracks.remove(i);
                        tracks.add(i, track2);
                    }
                }

            }
        }
        for (Track track : tracks) {
            if (track.getType())
                System.out.println(track.getName());
        }
    }

    private static void showPodcasts() {
        Artist artist = Artist.getLoggedInArtist();
        ArrayList<Track> tracks = artist.getTracks();
        for (int i = 0; i < tracks.size(); i++) {
            for (int j = 0; j < tracks.size(); j++) {
                if (i > j) {
                    Track track1 = tracks.get(i);
                    Track track2 = tracks.get(j);
                    if (track1.getReleseDate().after(track2.getReleseDate())) {
                        tracks.remove(j);
                        tracks.add(j, track1);
                        tracks.remove(i);
                        tracks.add(i, track2);
                    } else if (Objects.equals(track1.getReleseDate(), track2.getReleseDate()) &&
                            track1.getName().compareToIgnoreCase(track2.getName()) < 0) {
                        tracks.remove(j);
                        tracks.add(j, track1);
                        tracks.remove(i);
                        tracks.add(i, track2);
                    }
                }

            }
        }
        for (Track track : tracks) {
            if (!track.getType())
                System.out.println(track.getName());
        }
    }

    private static void releaseTrack(Matcher matcher) throws ParseException {
        Artist artist = Artist.getLoggedInArtist();
        String name = matcher.group("trackName");
        Track track = Track.getTrackByName(name);
        if (Track.getTracks().contains(track)) {
            System.out.println("track name already exists");
            return;
        }
        int duration = Integer.parseInt(matcher.group("duration"));
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy/MM/dd", Locale.ENGLISH);
        String date = matcher.group("releaseDate");
        Date releaseDate = formatter.parse(date);
        boolean type = Objects.equals(matcher.group("type"), "song");
        track = new Track(name, artist, duration, releaseDate, type);
        Track.addTrack(track);
        artist.addToTracks(track);
        System.out.println("track released successfully");
    }

    private static void getRank() {
        System.out.println(Artist.getLoggedInArtist().rankCalculator());
    }

    private static void getNumberOfFollowers() {
        System.out.println(Artist.getLoggedInArtist().getFollowers());
    }

}
