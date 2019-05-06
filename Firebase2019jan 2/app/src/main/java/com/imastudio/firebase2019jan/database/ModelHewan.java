package com.imastudio.firebase2019jan.database;

public class ModelHewan {
    String id,nama,info,url;

    public ModelHewan() {
    }

    public ModelHewan(String id, String nama, String info, String url) {
        this.id = id;
        this.nama = nama;
        this.info = info;
        this.url = url;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getNama() {
        return nama;
    }

    public void setNama(String nama) {
        this.nama = nama;
    }

    public String getInfo() {
        return info;
    }

    public void setInfo(String info) {
        this.info = info;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
