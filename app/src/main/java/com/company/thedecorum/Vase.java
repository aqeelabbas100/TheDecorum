package com.company.thedecorum;

public class Vase {
    public String name;
    public String size;
    public String imageurl;
    public String path;
    public String extension;
    public String price;
    public String url;


    public Vase(String name,String size,String imageurl){
        this.name = name;
        this.size = size;
        this.imageurl = imageurl;
        this.path = path;
        this.extension = extension;
        this.url = url;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSize() {
        return size;
    }

    public void setSize(String size) {
        this.size = size;
    }

    public String getExtension() {
        return extension;
    }

    public void setExtension(String extension) {
        this.extension = extension;
    }

    public String getModel() {
        return path;
    }

    public void setModel(String model) {
        this.path = model;
    }

    public Vase(){}

    public String getImageurl() {
        return imageurl;
    }

    public void setImageurl(String imageurl) {
        this.imageurl = imageurl;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }
    public String geturl() {
        return url;
    }

    public void seturl(String url) {
        this.url = url;
    }
}
