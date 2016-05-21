package org.nda.javafx.piradioplayer2.models;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.scene.control.Button;

/**
 * Created by NDA on 19.05.2016.
 */
public class ModelPlaylist {

    private final IntegerProperty id;
    private final StringProperty song;
    private final StringProperty btnDelete;

    public ModelPlaylist(int id, String song) {
        this.id = new SimpleIntegerProperty(id);
        this.song = new SimpleStringProperty(song);
        this.btnDelete = new SimpleStringProperty("удалить");
    }

    public String getSong() {
        return song.get();
    }

    public void setSong(String song) {
        this.song.set(song);
    }

    public int getId() {
        return id.get();
    }

    public void setId(int id) {
        this.id.set(id);
    }

    public IntegerProperty getIdProperty(){
        return id;
    }

    public StringProperty getSongProperty(){
        return song;
    }

    public StringProperty getDeleteProperty(){
        return btnDelete;
    }

}
