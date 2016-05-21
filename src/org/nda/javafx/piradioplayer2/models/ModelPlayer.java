package org.nda.javafx.piradioplayer2.models;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

import java.util.Objects;

/**
 * Created by NDA on 18.05.2016.
 */
public class ModelPlayer {
    private ModelPlayer(){

    }

    private static ModelPlayer instance;

    public static ModelPlayer getInstance(){
        if (instance == null){
            instance = new ModelPlayer();
        }
        return instance;
    }

    private final StringProperty volume = new SimpleStringProperty(this,"volume","");
    public final StringProperty volumeProperty(){
        return volume;
    }

    public String getVolume() {
        return volume.get();
    }

    public void setVolume(String volume) {
        volume = volume.replaceAll("(\\r|\\n)", "");
        if (Objects.equals(volume, "0")) this.volume.set("ВЫКЛ"); else this.volume.set(volume);
        this.volumeInt.set(Integer.parseInt(volume.replaceAll("\\D", "")));
    }




    private final StringProperty song = new SimpleStringProperty(this,"song","");
    public final StringProperty songProperty(){
        return song;
    }

    public String getSong() {
        return song.get();
    }

    public void setSong(String song) {
        this.song.set(song);
    }



    private final StringProperty songStatus = new SimpleStringProperty(this,"songStatus","");
    public final StringProperty songStatusProperty(){
        return songStatus;
    }

    public String getSongStatus() {
        return songStatus.get();
    }

    public void setSongStatus(String songStatus) {
        this.songStatus.set(songStatus);
    }



    private final StringProperty repeat = new SimpleStringProperty(this,"repeat","");
    public final StringProperty repeatProperty(){
        return repeat;
    }

    public String getRepeat() {
        return repeat.get();
    }

    public void setRepeat(String repeat) {
        this.repeat.set(repeat);
    }



    private final StringProperty random = new SimpleStringProperty(this,"random","");
    public final StringProperty randomProperty(){
        return random;
    }

    public String getRandom() {
        return random.get();
    }

    public void setRandom(String random) {
        this.random.set(random);
    }





    private final IntegerProperty volumeInt = new SimpleIntegerProperty();
    public final IntegerProperty volumeIntProperty(){
        return volumeInt;
    }

    public Integer getVolumeInt() {
        return volumeInt.get();
    }

    public void setVolumeInt(int volume) {
        this.volumeInt.set(volume);
    }
}
