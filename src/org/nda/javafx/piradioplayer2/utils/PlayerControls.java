package org.nda.javafx.piradioplayer2.utils;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import org.nda.javafx.piradioplayer2.models.ModelPlayer;
import org.nda.javafx.piradioplayer2.models.ModelPlaylist;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

/**
 * Created by NDA on 17.05.2016.
 */
public class PlayerControls {

    private ObservableList<ModelPlaylist> playlist;
    private SshClient ssh = SshClient.getInstance();
   // private PlayerControls player = PlayerControls.getInstance();

    private static PlayerControls instance;

    private PlayerControls(){

    }

    public static PlayerControls getInstance(){
        if (instance == null){
            instance = new PlayerControls();
        }
        return instance;
    }

    public boolean getAmixerMute(){
        String command = "amixer sget PCM | awk -F '[][]' '/dB/ {print $6}' | head -n 1";
        return (Objects.equals(ssh.sendCommand(command), "off\n"));
    }

    public String getAmixerVolume(){
        String command = "amixer sget PCM | awk -F '[][]' '/dB/ {print $2}' | sed 's/%//' | head -n 1";
        return ssh.sendCommand(command);
    }

    public void setVolume(String x){
        String command = "amixer sset PCM " + x.replaceAll("(\\r|\\n)","") + "%";
        ssh.sendCommand(command);
        ModelPlayer.getInstance().setVolume(getAmixerVolume());
    }

    public void toggleMute(){
        String command = "amixer set PCM toggle";
        ssh.sendCommand(command);
        if (getAmixerMute()) {ModelPlayer.getInstance().setVolume("0");}
        else{ModelPlayer.getInstance().setVolume(getAmixerVolume());}
    }

    public void play(){
        ssh.sendCommand("mpc toggle");
    }

    public void stop(){
        ssh.sendCommand("mpc stop");
    }

    public void next(){
        ssh.sendCommand("mpc next");
    }

    public void prev(){
        ssh.sendCommand("mpc prev");
    }

    public int getNumberSong(){
        String command = "mpc status | sed -n 2p | awk '{print $2}' | awk -F '#' '{print $2}' | awk -F '/' '{print $1}'";
        return Integer.parseInt(ssh.sendCommand(command).replaceAll("(\\r|\\n)",""));
    }

    public void getSongTitle(){
       ModelPlayer.getInstance().setSong(ssh.sendCommand("mpc current"));
    }

    public void getSongStatus(){
        ModelPlayer.getInstance().setSongStatus(ssh.sendCommand("mpc status | sed -n 2p"));
    }

    public void getRandomStatus(){
        String command = "mpc status | egrep -o 'random:.{,4}' | awk -F ':' '{print $2}' | sed 's/ //g' | sed 's/[[:blank:]]//g' | sed 's/[[:space:]]//g'";
        ModelPlayer.getInstance().setRandom(ssh.sendCommand(command).replaceAll("(\\r|\\n)",""));
    }


    public void getRepeatStatus(){
        String command = "mpc status | egrep -o 'repeat:.{,4}' | awk -F ':' '{print $2}' | sed 's/ //g' | sed 's/[[:blank:]]//g' | sed 's/[[:space:]]//g'";
        ModelPlayer.getInstance().setRepeat(ssh.sendCommand(command).replaceAll("(\\r|\\n)",""));
    }

    public void toggleRepeat(){
        ssh.sendCommand("mpc repeat");
    }

    public void toggleRandom(){
        ssh.sendCommand("mpc random");
    }

    public ArrayList<ModelPlaylist> getPlaylist2(){
        ArrayList<ModelPlaylist> songs = new ArrayList<ModelPlaylist>();

        return songs;
    }


    public ObservableList<ModelPlaylist> getPlaylist(){
        playlist = FXCollections.observableArrayList();

        String[] out = ssh.sendCommand("mpc playlist").split("\\n");
        playlist.clear();
        for (int i = 0; i < out.length; i++){
            ModelPlaylist song = new ModelPlaylist(i+1, out[i]);
            playlist.add(song);
        }
        return playlist;
    }

    public void playTo(int x){
        String command = "mpc play " + x;
        ssh.sendCommand(command);
    }

    public void deleteSong(int idSong){
        ssh.sendCommand("mpc del " + idSong);
        playlist.remove(idSong);
    }



}
