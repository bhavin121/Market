package com.bhavin.market.classes;

public class Color {
    private String color;
    private String name;

    public Color(){
    }

    public Color(String color , String name){
        this.color = color;
        this.name = name;
    }

    public String getColor( ){
        return color;
    }

    public void setColor(String color){
        this.color = color;
    }

    public String getName( ){
        return name;
    }

    public void setName(String name){
        this.name = name;
    }

    @Override
    public String toString( ){
        return "Color{" +
                "color='" + color + '\'' +
                ", name='" + name + '\'' +
                '}';
    }
}
