package com.example.fyp;

import java.util.Comparator;

public class Product {
    public String name;
    public String size;
    public String price;
    public String imageurl;
    public String path;
    public String extension;


    public Product(String name,String size,String imageurl){
        this.name = name;
        this.size = size;
        this.size = price;
        this.imageurl = imageurl;
        this.path = path;
        this.extension = extension;
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

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
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

    public Product(){}

    public String getImageurl() {
        return imageurl;
    }

    public void setImageurl(String imageurl) {
        this.imageurl = imageurl;
    }

    public static final Comparator<Product> BY_TITLE_ASCENDING = new Comparator<Product>() {
        @Override
        public int compare(Product t1, Product t2) {
            return t1.getPrice().compareTo(t2.getPrice());
        }
    };
    public static final Comparator<Product> BY_TITLE_DESCENDING = new Comparator<Product>() {
        @Override
        public int compare(Product t1, Product t2) {
            return t2.getPrice().compareTo(t1.getPrice());
        }
    };
}
