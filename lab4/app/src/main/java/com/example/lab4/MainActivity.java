package com.example.lab4;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    TextView textName, textPrice;
    ListView listViewProducts;
    Button ButtonAddProduct, buttonFindProduct, buttonDelProduct;
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
        buttonFindProduct = (Button) findViewById(R.id.findButton);
        buttonDelProduct = (Button) findViewById(R.id.delButton);

        productList = new ArrayList<>();
        databaseReference = FirebaseDatabase.getInstance().getReference("products");

        ButtonAddProduct.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addProduct();
            }
        });
        buttonFindProduct.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                find(textName.getText().toString().trim());
                System.out.println("finding data");
            }
        });
        buttonDelProduct.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                deleteProduct(textName.getText().toString());
            }
        });


    }

    private void addProduct() {
        String name = textName.getText().toString().trim();
        double price = Double.parseDouble(String.valueOf(textPrice.getText().toString()));
        if(!TextUtils.isEmpty(name)){
            String id = databaseReference.push().getKey();

            Product product = new Product(id, name, price);
            databaseReference.child(id).setValue(product);
            textName.setText("");
            textPrice.setText("");

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

    private void find(String product){

        Query searchedProduct = databaseReference;
        if(!product.equals("")) {
             searchedProduct = databaseReference.orderByChild("_productname").startAt(product).endAt(product + "\uf8ff");
            DatabaseReference reference = searchedProduct.getRef();
        }
        searchedProduct.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                productList.clear();
                for (DataSnapshot postSnapshot : snapshot.getChildren()) {

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

    private void deleteProduct(String productName) {
        DatabaseReference ref = FirebaseDatabase.getInstance().getReference();

        //Query queryForDelete = ref.child("databaseReference").orderByChild("_productname").equalTo("test2"); //I dont know why this doesnt work
       Query queryForDelete = databaseReference.orderByChild("_productname").startAt(productName).endAt(productName+"\uf8ff"); //Query the database product with specified -productname

        System.out.println("deleting " + productName);

        if(!TextUtils.isEmpty(productName)) {
            queryForDelete.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    for (DataSnapshot dataSnap : dataSnapshot.getChildren()) {
                        dataSnap.getRef().removeValue(); //Remove the item
                        System.out.println(dataSnap.getValue());
                    }
                }
                @Override
                public void onCancelled(@NonNull DatabaseError error) {
                }
            });
            Toast.makeText(this,productName + " deleted",Toast.LENGTH_LONG).show();
        }
        else{
            Toast.makeText(this,"\"Product Name\" field is blank",Toast.LENGTH_LONG).show();
        }
    }
}