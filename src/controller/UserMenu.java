package controller;

import model.Artist;
import model.Playlist;
import model.Track;
import model.User;

import java.text.SimpleDateFormat;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class UserMenu {
    public static void run(Scanner scanner) {
        String line = scanner.nextLine();
        Matcher matcher = UserMenu.getCommandMatcher(line, "back|exit");
        while (!matcher.matches()) {
            matcher = UserMenu.getCommandMatcher(line, "show playlists");
            if (matcher.matches()) {
                UserMenu.showPlaylists();
                line = scanner.nextLine();
                matcher = UserMenu.getCommandMatcher(line, "back|exit");
                continue;
            }
            matcher = UserMenu.getCommandMatcher(line, "show liked tracks");
            if (matcher.matches()) {
                UserMenu.showLikedTracks();
                line = scanner.nextLine();
                matcher = UserMenu.getCommandMatcher(line, "back|exit");
                continue;
            }
            matcher = UserMenu.getCommandMatcher(line, "show queue");
            if (matcher.matches()) {
                UserMenu.showQueue();
                line = scanner.nextLine();
                matcher = UserMenu.getCommandMatcher(line, "back|exit");
                continue;
            }
            matcher = UserMenu.getCommandMatcher(line, "add -t (?<trackName>[a-zA-Z0-9 ]+) to queue");
            if (matcher.matches()) {
                UserMenu.addTrackToQueue(matcher);
                line = scanner.nextLine();
                matcher = UserMenu.getCommandMatcher(line, "back|exit");
                continue;
            }
            matcher = UserMenu.getCommandMatcher(line, "like track -t (?<trackName>[a-zA-Z0-9 ]+)");
            if (matcher.matches()) {
                UserMenu.addTrackToLikedTracks(matcher);
                line = scanner.nextLine();
                matcher = UserMenu.getCommandMatcher(line, "back|exit");
                continue;
            }
            matcher = UserMenu.getCommandMatcher(line, "remove -t (?<trackName>[a-zA-Z0-9 ]+) from queue");
            if (matcher.matches()) {
                UserMenu.removeTrackFromQueue(matcher);
                line = scanner.nextLine();
                matcher = UserMenu.getCommandMatcher(line, "back|exit");
                continue;
            }
            matcher = UserMenu.getCommandMatcher(line, "remove -t (?<trackName>[a-zA-Z0-9 ]+) from liked tracks");
            if (matcher.matches()) {
                UserMenu.removeTrackFromLikedTracks(matcher);
                line = scanner.nextLine();
                matcher = UserMenu.getCommandMatcher(line, "back|exit");
                continue;
            }
            matcher = UserMenu.getCommandMatcher(line, "reverse order of queue from (?<start>[\\d-]+) to (?<end>[\\d-]+)");
            if (matcher.matches()) {
                UserMenu.reverseOrderOfQueue(matcher);
                line = scanner.nextLine();
                matcher = UserMenu.getCommandMatcher(line, "back|exit");
                continue;
            }
            matcher = UserMenu.getCommandMatcher(line, "create -p (?<playlistName>[a-zA-Z0-9 ]+)");
            if (matcher.matches()) {
                UserMenu.createPlaylist(matcher);
                line = scanner.nextLine();
                matcher = UserMenu.getCommandMatcher(line, "back|exit");
                continue;
            }
            matcher = UserMenu.getCommandMatcher(line, "delete -p (?<playlistName>[a-zA-Z0-9 ]+)");
            if (matcher.matches()) {
                UserMenu.removePlaylist(matcher);
                line = scanner.nextLine();
                matcher = UserMenu.getCommandMatcher(line, "back|exit");
                continue;
            }
            matcher = UserMenu.getCommandMatcher(line, "follow user -u (?<username>[a-zA-Z0-9]+)");
            if (matcher.matches()) {
                UserMenu.followUser(matcher);
                line = scanner.nextLine();
                matcher = UserMenu.getCommandMatcher(line, "back|exit");
                continue;
            }
            matcher = UserMenu.getCommandMatcher(line, "follow artist -u (?<username>[a-zA-Z0-9]+)");
            if (matcher.matches()) {
                UserMenu.followArtist(matcher);
                line = scanner.nextLine();
                matcher = UserMenu.getCommandMatcher(line, "back|exit");
                continue;
            }
            matcher = UserMenu.getCommandMatcher(line, "unfollow user -u (?<username>[a-zA-Z0-9]+)");
            if (matcher.matches()) {
                UserMenu.unfollowUser(matcher);
                line = scanner.nextLine();
                matcher = UserMenu.getCommandMatcher(line, "back|exit");
                continue;
            }
            matcher = UserMenu.getCommandMatcher(line, "unfollow artist -u (?<username>[a-zA-Z0-9]+)");
            if (matcher.matches()) {
                UserMenu.unfollowArtist(matcher);
                line = scanner.nextLine();
                matcher = UserMenu.getCommandMatcher(line, "back|exit");
                continue;
            }
            matcher = UserMenu.getCommandMatcher(line, "go to playlist menu -p (?<playlistName>[a-zA-Z0-9 ]+)");
            if (matcher.matches()) {
                UserMenu.goToPlaylistMenu(scanner, matcher);
                line = scanner.nextLine();
                matcher = UserMenu.getCommandMatcher(line, "back|exit");
                continue;
            }
            matcher = UserMenu.getCommandMatcher(line, "show track info -t (?<trackName>[a-zA-Z0-9 ]+)");
            if (matcher.matches()) {
                UserMenu.showTrackInfo(matcher);
                line = scanner.nextLine();
                matcher = UserMenu.getCommandMatcher(line, "back|exit");
                continue;
            }
            matcher = UserMenu.getCommandMatcher(line, "show playlist info -p (?<playlistName>[a-zA-Z0-9 ]+)");
            if (matcher.matches()) {
                UserMenu.showPlaylistInfo(matcher);
                line = scanner.nextLine();
                matcher = UserMenu.getCommandMatcher(line, "back|exit");
                continue;
            }
            matcher = UserMenu.getCommandMatcher(line, "show artist info -u (?<username>[a-zA-Z0-9]+)");
            if (matcher.matches()) {
                UserMenu.showArtistInfo(matcher);
                line = scanner.nextLine();
                matcher = UserMenu.getCommandMatcher(line, "back|exit");
                continue;
            }
            matcher = UserMenu.getCommandMatcher(line, "show user info -u (?<username>[a-zA-Z0-9]+)");
            if (matcher.matches()) {
                UserMenu.showUserInfo(matcher);
                line = scanner.nextLine();
                matcher = UserMenu.getCommandMatcher(line, "back|exit");
                continue;
            }
            matcher = getCommandMatcher(line, "show menu name");
            if (matcher.matches()) {
                System.out.println("user menu");
                line = scanner.nextLine();
                matcher = UserMenu.getCommandMatcher(line, "back|exit");
                continue;
            }
            System.out.println("invalid command");
            line = scanner.nextLine();
            matcher = UserMenu.getCommandMatcher(line, "back|exit");
        }
        if (Objects.equals(line, "back"))
            return;
        System.exit(0);
    }

    private static Matcher getCommandMatcher(String input, String regex) {
        return Pattern.compile(regex).matcher(input);
    }

    private static void showPlaylists() {
        User user = User.getLoggedInUser();
        ArrayList<String> playlistNames = new ArrayList<>();
        for (Playlist playlist : user.getPlaylists())
            playlistNames.add(playlist.getName());
        Collections.sort(playlistNames);
        for (String s : playlistNames)
            System.out.println(s);
    }

    private static void showLikedTracks() {
        User user = User.getLoggedInUser();
        ArrayList<String> trackNames = new ArrayList<>();
        for (Track track : user.getLikedTracks())
            trackNames.add(track.getName());
        Collections.sort(trackNames);
        for (String s : trackNames)
            System.out.println(s);
    }

    private static void showQueue() {
        User user = User.getLoggedInUser();
        for (Track track : user.getQueue())
            System.out.println(track.getName());
    }

    private static void addTrackToQueue(Matcher matcher) {
        User user = User.getLoggedInUser();
        Track track = Track.getTrackByName(matcher.group("trackName"));
        if (track == null) {
            System.out.println("no such track");
            return;
        }
        user.addToQueue(track);
        System.out.println("track added to queue successfully");
    }

    private static void addTrackToLikedTracks(Matcher matcher) {
        User user = User.getLoggedInUser();
        Track track = Track.getTrackByName(matcher.group("trackName"));
        if (track == null) {
            System.out.println("no such track");
            return;
        }
        if (user.getLikedTracks().contains(track)) {
            System.out.println("track is already liked");
            return;
        }
        user.addLikedTrack(track);
        System.out.println("liked track successfully");
    }

    private static void removeTrackFromQueue(Matcher matcher) {
        User user = User.getLoggedInUser();
        Track track = Track.getTrackByName(matcher.group("trackName"));
        if (!user.getQueue().contains(track)) {
            System.out.println("no such track in queue");
            return;
        }
        user.removeFromQueue(track);
        System.out.println("track removed from queue successfully");
    }

    private static void removeTrackFromLikedTracks(Matcher matcher) {
        User user = User.getLoggedInUser();
        Track track = Track.getTrackByName(matcher.group("trackName"));
        if (!user.getLikedTracks().contains(track)) {
            System.out.println("no such track in liked tracks");
            return;
        }
        user.removeLikedTrack(track);
        System.out.println("track removed from liked tracks successfully");
    }

    private static void reverseOrderOfQueue(Matcher matcher) {
        User user = User.getLoggedInUser();
        ArrayList<Track> queue = user.getQueue();
        int start = Integer.parseInt(matcher.group("start"));
        int end = Integer.parseInt(matcher.group("end"));
        if (queue.isEmpty()) {
            System.out.println("queue is empty");
            return;
        }
        if (1 > start ||
                start >= end ||
                end > queue.size()) {
            System.out.println("invalid bounds");
            return;
        }
        ArrayList<Track> newQueue = new ArrayList<>();
        ArrayList<Track> reversedPart = new ArrayList<>();
        for (int i = 0; i < start - 1; i++)
            newQueue.add(queue.get(i));
        for (int i = start - 1; i < end; i++)
            reversedPart.add(queue.get(i));
        Collections.reverse(reversedPart);
        newQueue.addAll(reversedPart);
        for (int i = end; i < queue.size(); i++)
            newQueue.add(queue.get(i));
        user.setQueue(newQueue);
        System.out.println("order of queue reversed successfully");
    }

    private static void createPlaylist(Matcher matcher) {
        User user = User.getLoggedInUser();
        String playlistName = matcher.group("playlistName");
        Playlist playlist = Playlist.getPlaylistByName(playlistName);
        if (playlist != null) {
            System.out.println("playlist name already exists");
            return;
        }
        playlist = new Playlist(playlistName, user);
        user.addPlaylist(playlist);
        Playlist.addPlaylist(playlist);
        System.out.println("playlist created successfully");
    }

    private static void removePlaylist(Matcher matcher) {
        User user = User.getLoggedInUser();
        String playlistName = matcher.group("playlistName");
        Playlist playlist = null;
        for (Playlist playlist1 : user.getPlaylists())
            if (Objects.equals(playlistName, playlist1.getName()))
                playlist = playlist1;
        if (playlist == null) {
            System.out.println("user doesn't own such playlist");
            return;
        }
        user.removePlaylist(playlist);
        Playlist.removePlaylist(playlist);
        System.out.println("playlist deleted successfully");
    }

    private static void followUser(Matcher matcher) {
        User me = User.getLoggedInUser();
        User who = User.getUserByUsername(matcher.group("username"));
        if (who == null) {
            System.out.println("no such user");
            return;
        }
        if (Objects.equals(me, who)) {
            System.out.println("you can't follow yourself");
            return;
        }
        me.setFollowing(me.getFollowing() + 1);
        who.setFollowers(who.getFollowers() + 1);
        System.out.println("added user to followings");
    }

    private static void followArtist(Matcher matcher) {
        User me = User.getLoggedInUser();
        Artist who = Artist.getArtistByUsername(matcher.group("username"));
        if (who == null) {
            System.out.println("no such artist");
            return;
        }
        me.setFollowing(me.getFollowing() + 1);
        who.setFollowers(who.getFollowers() + 1);
        System.out.println("added artist to followings");
    }

    private static void unfollowUser(Matcher matcher) {
        User me = User.getLoggedInUser();
        User who = User.getUserByUsername(matcher.group("username"));
        if (who == null) {
            System.out.println("no such user");
            return;
        }
        if (Objects.equals(me, who)) {
            System.out.println("you can't unfollow yourself");
            return;
        }
        me.setFollowing(me.getFollowing() - 1);
        who.setFollowers(who.getFollowers() - 1);
        System.out.println("user unfollowed successfully");
    }

    private static void unfollowArtist(Matcher matcher) {
        User me = User.getLoggedInUser();
        Artist who = Artist.getArtistByUsername(matcher.group("username"));
        if (who == null) {
            System.out.println("no such artist");
            return;
        }
        me.setFollowing(me.getFollowing() - 1);
        who.setFollowers(who.getFollowers() - 1);
        System.out.println("artist unfollowed successfully");
    }

    private static void goToPlaylistMenu(Scanner scanner, Matcher matcher) {
        Playlist playlist = Playlist.getPlaylistByName(matcher.group("playlistName"));
        if (playlist == null) {
            System.out.println("no such playlist");
            return;
        }
        Playlist.setCurrentPlaylist(playlist);
        System.out.println("entered playlist menu successfully");
        PlaylistMenu.run(scanner);
    }

    private static void showTrackInfo(Matcher matcher) {
        Track track = Track.getTrackByName(matcher.group("trackName"));
        if (track == null) {
            System.out.println("no such track");
            return;
        }
        System.out.print(track.getName());
        if (track.getType())
            System.out.print(" song ");
        else
            System.out.print(" podcast ");
        System.out.print(track.getDuration() + " ");
        System.out.print(new SimpleDateFormat("yyyy/MM/dd").format(track.getReleseDate()));
        System.out.print(" " + track.getArtist().getNickname() + "\n");
    }

    private static void showPlaylistInfo(Matcher matcher) {
        Playlist playlist = Playlist.getPlaylistByName(matcher.group("playlistName"));
        if (playlist == null) {
            System.out.println("no such playlist");
            return;
        }
        System.out.print(playlist.getName() + " ");
        System.out.print(playlist.getOwner().getUsername() + " ");
        System.out.print(playlist.durationCalculator() + "\n");
    }

    private static void showArtistInfo(Matcher matcher) {
        Artist artist = Artist.getArtistByUsername(matcher.group("username"));
        if (artist == null) {
            System.out.println("no such artist");
            return;
        }
        System.out.print(artist.getUsername() + " ");
        System.out.print(artist.getNickname() + " ");
        System.out.print(artist.getFollowers() + " ");
        System.out.print(artist.rankCalculator() + "\n");
    }

    private static void showUserInfo(Matcher matcher) {
        User user = User.getUserByUsername(matcher.group("username"));
        if (user == null) {
            System.out.println("no such user");
            return;
        }
        System.out.print(user.getUsername() + " ");
        System.out.print(user.getFollowers() + " ");
        System.out.print(user.getFollowing() + " ");
        System.out.print(user.getPlaylists().size() + "\n");
    }
}
