package com.example.lab4;

public class Product {
    private String _id;
    private String _productname;

    public String get_id() {
        return _id;
    }

    public void set_id(String _id) {
        this._id = _id;
    }

    public String get_productname() {
        return _productname;
    }

    public void set_productname(String _productname) {
        this._productname = _productname;
    }

    public double get_price() {
        return _price;
    }

    public void set_price(double _price) {
        this._price = _price;
    }

    private double _price;


    public Product(){

    }

    public Product(String id, String productname, double price){
        _id =id;
        _price = price;
        _productname = productname;
    }
    public Product(String productname, double price){
        _productname = productname;
        _price= price;
    }

}
