package model;

import java.util.ArrayList;
import java.util.Objects;

public class User {
    private final String username;
    private final String password;
    private int followers;
    private int following;
    private ArrayList<Track> queue;
    private final ArrayList<Track> likedTracks;
    private final ArrayList<Playlist> playlists;
    private static User loggedInUser;
    private static final ArrayList<User> users = new ArrayList<>();

    public User(String username, String password) {
        this.username = username;
        this.password = password;
        this.queue = new ArrayList<>();
        this.likedTracks = new ArrayList<>();
        this.playlists = new ArrayList<>();

    }

    public void addPlaylist(Playlist playlist) {
        this.playlists.add(playlist);
    }

    public void removePlaylist(Playlist playlist) {
        this.playlists.remove(playlist);
    }

    public void addToQueue(Track track) {
        this.queue.add(track);
    }

    public void removeFromQueue(Track track) {
        for (int i = 0; i < queue.size(); i++) {
            if (Objects.equals(queue.get(i), track)) {
                i--;
                this.queue.remove(track);
            }
        }
    }

    public void addLikedTrack(Track track) {
        this.likedTracks.add(track);
    }

    public void removeLikedTrack(Track track) {
        for (int i = 0; i < likedTracks.size(); i++) {
            if (Objects.equals(likedTracks.get(i), track)) {
                i--;
                likedTracks.remove(track);
            }
        }
    }

    public static User getUserByUsername(String username) {
        for (User user : users)
            if (Objects.equals(user.username, username))
                return user;
        return null;
    }

    public static void addUser(User user) {
        User.users.add(user);
    }

    public ArrayList<Track> getQueue() {
        return this.queue;
    }

    public void setQueue(ArrayList<Track> queue) {
        this.queue = queue;
    }

    public String getUsername() {
        return username;
    }

    public ArrayList<Playlist> getPlaylists() {
        return playlists;
    }

    public int getFollowers() {
        return followers;
    }

    public int getFollowing() {
        return following;
    }

    public void setFollowers(int followers) {
        this.followers = followers;
    }

    public void setFollowing(int following) {
        this.following = following;
    }

    public ArrayList<Track> getLikedTracks() {
        return likedTracks;
    }

    public String getPassword() {
        return password;
    }

    public static User getLoggedInUser() {
        return loggedInUser;
    }

    public static void setLoggedInUser(User user) {
        User.loggedInUser = user;
    }
}