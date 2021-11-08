package com.example.lab4;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.util.List;

public class ProductList extends ArrayAdapter<Product> {
    private Activity context;
    List<Product> productList;

    public ProductList(Activity context, List<Product> products){
        super(context, R.layout.layout_product_list,products);
        this.context = context;
        this.productList = products;

    }

    public View getView(int position, View convertView, ViewGroup parent){
        LayoutInflater inflater = context.getLayoutInflater();
        View listViewItem = inflater.inflate(R.layout.layout_product_list,null, true);

        TextView textViewName = (TextView) listViewItem.findViewById(R.id.textviewName);
        TextView textViewPrice = (TextView) listViewItem.findViewById(R.id.textviewPrice);

        Product product = productList.get(position);
        textViewName.setText(product.get_productname());
        textViewPrice.setText(String.valueOf(product.get_price()));
        return listViewItem;
    }
}
