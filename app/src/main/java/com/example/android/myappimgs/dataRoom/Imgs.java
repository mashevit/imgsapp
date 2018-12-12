package com.example.android.myappimgs.dataRoom;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import java.util.ArrayList;
import java.util.List;

@Entity(tableName = "imgs_table")
public class Imgs {



        @PrimaryKey(autoGenerate = true)
        @NonNull
        @ColumnInfo(name = "id")
        private int id;

        @Nullable
        @ColumnInfo(name = "trip")
        private String trip;

        @Nullable
        @ColumnInfo(name = "imgaddr")
        private String imgaddr;

        @Nullable
        @ColumnInfo(name = "sight")
        private String sight;




    @Nullable
    @ColumnInfo(name = "listaaddr")
    private ArrayList<String> listaaddr;



        public Imgs() {
            this.id = 0;
            this.trip = "";
            this.imgaddr = "";
            this.sight = "";

    listaaddr=null;

        }

        public Imgs( @Nullable String trip, @Nullable String imgaddr, @Nullable String sight,ArrayList<String>  aa) {
            this.id = id;
            this.trip = trip;
            this.imgaddr = imgaddr;
            this.sight = sight;
            this.listaaddr=aa;
        }

        @NonNull
        public int getId() {
            return id;
        }

        public void setId(@NonNull int id) {
            this.id = id;
        }

        @Nullable
        public String getTrip() {
            return trip;
        }

        public void setTrip(@Nullable String trip) {
            this.trip = trip;
        }

        @Nullable
        public String getImgaddr() {
            return imgaddr;
        }

        public void setImgaddr(@Nullable String imgaddr) {
            this.imgaddr = imgaddr;
        }

        @Nullable
        public String getSight() {
            return sight;
        }

        public void setSight(@Nullable String sight) {
            this.sight = sight;
        }


    @Nullable
    public ArrayList<String> getListaaddr() {
        return listaaddr;
    }

    public void setListaaddr(@Nullable ArrayList<String> listaaddr) {
        this.listaaddr = listaaddr;
    }
    }
