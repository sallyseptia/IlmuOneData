package com.example.asus.ilmuonedata;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.asus.ilmuonedata.Model.Order;
import com.example.asus.ilmuonedata.Model.Product;
import com.example.asus.ilmuonedata.Model.User;
import com.google.android.gms.tagmanager.TagManager;
import com.google.firebase.analytics.FirebaseAnalytics;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;
import com.google.firebase.analytics.FirebaseAnalytics.Event;
import com.google.firebase.analytics.FirebaseAnalytics.Param;

public class ProductDetail extends AppCompatActivity {
    TextView product_name,product_description,product_price;
    Button btn_buy;
    ImageView img_product;
    CollapsingToolbarLayout collapsingToolbarLayout;

    String productId = "";

    FirebaseAnalytics mFirebaseAnalytics;

    DatabaseReference product;
    Product currentproduct;
    FirebaseDatabase database = FirebaseDatabase.getInstance();
    final DatabaseReference table_order = database.getReference("Order");
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_detail);

        //Init View
        product_name = (TextView) findViewById(R.id.product_name);
        product_description = (TextView) findViewById(R.id.product_description);
        product_price = (TextView) findViewById(R.id.product_price);
        img_product = (ImageView) findViewById(R.id.img_product);
        btn_buy = (Button) findViewById(R.id.btnBuy);
        TagManager tagManager = TagManager.getInstance(this);
        mFirebaseAnalytics = FirebaseAnalytics.getInstance(this);

        collapsingToolbarLayout = (CollapsingToolbarLayout)findViewById(R.id.collapsing);
        collapsingToolbarLayout.setExpandedTitleTextAppearance(R.style.ExpandedAppbar);
        collapsingToolbarLayout.setCollapsedTitleTextAppearance(R.style.CollapsedAppbar);

        if(getIntent() !=null)
        {
            productId = getIntent().getStringExtra("productId");
        }
            getDetailProduct(productId);

        Bundle screenObject = new Bundle();
        screenObject.putString("screenName","PDP");
        mFirebaseAnalytics.logEvent("screenView",screenObject);

        // Define product with relevant parameters

        Bundle product1 = new Bundle();
        product1.putString( Param.ITEM_ID, "sku1234"); // ITEM_ID or ITEM_NAME is required
        product1.putString( Param.ITEM_NAME, "Donut Friday Scented T-Shirt");
        product1.putString( Param.ITEM_CATEGORY, "Apparel/Men/Shirts");
        product1.putString( Param.ITEM_VARIANT, "Blue");
        product1.putString( Param.ITEM_BRAND, "Google");
        product1.putDouble( Param.PRICE, 29.99 );
        product1.putString( Param.CURRENCY, "USD" ); // Item-level currency unused today

// Prepare ecommerce bundle

        Bundle ecommerceBundle = new Bundle();
        ecommerceBundle.putBundle( "items", product1 );

// Log view_item event with ecommerce bundle

        mFirebaseAnalytics.logEvent( Event.VIEW_ITEM, ecommerceBundle );


        btn_buy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                table_order.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        String name = product_name.getText().toString();
                        String description = product_description.getText().toString();
                        String price = product_price.getText().toString();
                        String id = String.valueOf(System.currentTimeMillis());

                        //Input to firebase
                        Order order = new Order(id, price, description, name);
                        table_order.child(id).setValue(order);

                        Intent home = new Intent(getApplicationContext(), Home.class);
                        Toast.makeText(ProductDetail.this, "Success order!", Toast.LENGTH_SHORT).show();

                        startActivity(home);
                    }
                    @Override
                    public void onCancelled(DatabaseError databaseError) {
                    }
                });
            }
        });
    }

    private void getDetailProduct(final String productId) {
        product = FirebaseDatabase.getInstance().getReference("Product");
        product.child(productId).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                currentproduct = new Product();
                currentproduct = dataSnapshot.getValue(Product.class);

                for(DataSnapshot productChild : dataSnapshot.getChildren())
                {
                    Picasso.with(getBaseContext()).load(currentproduct.getImage())
                            .into(img_product);
                    collapsingToolbarLayout.setTitle(currentproduct.getName());
                    product_name.setText(currentproduct.getName());
                    product_description.setText(currentproduct.getDescription());
                    product_price.setText("Rp. "+currentproduct.getPrice());
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }
}
