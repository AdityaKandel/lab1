package com.example.lab4;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    TextView textName, textPrice;
    ListView listViewProducts;
    Button ButtonAddProduct;
    List<Product> productList;
    DatabaseReference databaseReference;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        textName = (TextView) findViewById(R.id.textName);
        textPrice = (TextView) findViewById(R.id.textPrice);
        listViewProducts = (ListView) findViewById(R.id.ListViewProducts);
        ButtonAddProduct = (Button) findViewById(R.id.addButton);

        productList = new ArrayList<>();
        databaseReference = FirebaseDatabase.getInstance().getReference("products");

        ButtonAddProduct.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addProduct();
            }
        });

    }

    private void addProduct() {
        String name = textName.getText().toString().trim();
        double price = Double.parseDouble(String.valueOf(textPrice.getText().toString()));
        if(!TextUtils.isEmpty(name)){
            String id = databaseReference.getKey();

            Product product = new Product(id, name, price);
            databaseReference.child(id).setValue(product);

            textName.setText("");
            textName.setText("");

            Toast.makeText(this, "product added", Toast.LENGTH_LONG).show();
        }else{
            Toast.makeText(this, "please enter a name", Toast.LENGTH_LONG).show();
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                productList.clear();
                for (DataSnapshot postSnapshot : snapshot.getChildren()){
                    Product product = postSnapshot.getValue(Product.class);
                    productList.add(product);
                }
                ProductList productAdapter = new ProductList(MainActivity.this, productList);
                listViewProducts.setAdapter(productAdapter);
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
}