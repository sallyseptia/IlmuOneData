package com.example.asus.ilmuonedata;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.example.asus.ilmuonedata.Interface.ItemClickListener;
import com.example.asus.ilmuonedata.Model.Product;
import com.google.android.gms.tagmanager.TagManager;
import com.google.firebase.analytics.FirebaseAnalytics;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.analytics.FirebaseAnalytics.Event;
import com.google.firebase.analytics.FirebaseAnalytics.Param;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class Home extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    List<Product> listProduct = new ArrayList<>();

    TextView txtFullName;
    String name2;
    ProductAdapter adapter;
    FirebaseAnalytics mFirebaseAnalytics;

    UserSessionManager session;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        session = new UserSessionManager(getApplicationContext());

        TagManager tagManager = TagManager.getInstance(this);
        mFirebaseAnalytics = FirebaseAnalytics.getInstance(this);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        HashMap<String, String> user = session.getUserDetails();
        name2 = user.get(UserSessionManager.KEY_NAME);

        View headerView = navigationView.getHeaderView(0);
        txtFullName = (TextView) headerView.findViewById(R.id.txtFullName);

        String hallo = "Hello, ";
        txtFullName.setText(hallo + name2);

        Bundle screenObject = new Bundle();
        screenObject.putString("screenName","Product Impression");
        mFirebaseAnalytics.logEvent("screenView",screenObject);


        // Define products with relevant parameters

        Bundle product1 = new Bundle();
        product1.putString( Param.ITEM_ID, "sku1234");  // ITEM_ID or ITEM_NAME is required
        product1.putString( Param.ITEM_NAME, "Donut Friday Scented T-Shirt");
        product1.putString( Param.ITEM_CATEGORY, "Apparel/Men/Shirts");
        product1.putString( Param.ITEM_VARIANT, "Blue");
        product1.putString( Param.ITEM_BRAND, "Google");
        product1.putDouble( Param.PRICE, 29.99 );
        product1.putString( Param.CURRENCY, "USD" );
        product1.putLong( Param.INDEX, 1 );     // Position of the item in the list

        Bundle product2 = new Bundle();
        product2.putString( Param.ITEM_ID, "sku5678");
        product2.putString( Param.ITEM_NAME, "Android Workout Capris");
        product2.putString( Param.ITEM_CATEGORY, "Apparel/Women/Pants");
        product2.putString( Param.ITEM_VARIANT, "Black");
        product2.putString( Param.ITEM_BRAND, "Google");
        product2.putDouble( Param.PRICE, 39.99 );
        product2.putString( Param.CURRENCY, "USD" );
        product2.putLong( Param.INDEX, 2 );

// Prepare ecommerce bundle

        ArrayList items = new ArrayList();
        items.add(product1);
        items.add(product2);

        Bundle ecommerceBundle = new Bundle();
        ecommerceBundle.putParcelableArrayList( "items", items );

// Set relevant bundle-level parameters

        ecommerceBundle.putString( Param.ITEM_LIST, "Search Results" ); // List name

// Log view_search_results or view_item_list event with ecommerce bundle

        mFirebaseAnalytics.logEvent( Event.VIEW_SEARCH_RESULTS, ecommerceBundle );





        final DatabaseReference ProductRef = FirebaseDatabase.getInstance().getReference();
        ProductRef.child("Product").orderByKey().addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for(final DataSnapshot ProductChild : dataSnapshot.getChildren())
                {
                    final String productKey = ProductChild.getKey().toString();
                    final String name = ProductChild.child("name").getValue(String.class);
                    final String image = ProductChild.child("image").getValue(String.class);
                    final String price = ProductChild.child("price").getValue(String.class);
                    final String quantity = ProductChild.child("quantity").getValue(String.class);

                    Product product = new Product();
                    product.setProductId(productKey);
                    product.setName(name);
                    product.setImage(image);
                    product.setPrice(price);
                    product.setQuantity(quantity);
                    listProduct.add(product);
                }
                RecyclerView recyclerView = findViewById(R.id.rvNumbers);

                adapter = new ProductAdapter(getApplicationContext(),listProduct, new ItemClickListener() {

                    @Override
                    public void onClick(View view, int position, boolean isLongClick) {
                        // Define product with relevant parameters

                        Bundle product1 = new Bundle();
                        product1.putString( Param.ITEM_ID, "sku1234"); // ITEM_ID or ITEM_NAME is required
                        product1.putString( Param.ITEM_NAME, "Donut Friday Scented T-Shirt");
                        product1.putString( Param.ITEM_CATEGORY, "Apparel/Men/Shirts");
                        product1.putString( Param.ITEM_VARIANT, "Blue");
                        product1.putString( Param.ITEM_BRAND, "Google");
                        product1.putDouble( Param.PRICE, 29.99 );
                        product1.putString( Param.CURRENCY, "USD" ); // Item-level currency unused today
                        product1.putLong( Param.INDEX, 1 ); // Position of the item in the list

                        // Prepare ecommerce bundle

                        Bundle ecommerceBundle = new Bundle();
                        ecommerceBundle.putBundle( "items", product1 );

// Set relevant action-level parameters

                        ecommerceBundle.putString( Param.ITEM_LIST, "Search Results" ); // Optional list name

// Log select_content event with ecommerce bundle

                        mFirebaseAnalytics.logEvent( Event.SELECT_CONTENT, ecommerceBundle );


                        Intent productDetail = new Intent(getApplicationContext(), ProductDetail.class);
                        productDetail.putExtra("productId", adapter.listProduct.get(position).getProductId());
                        startActivity(productDetail);
                    }
                });
                recyclerView.setLayoutManager(new GridLayoutManager(getApplicationContext(), 2));
                recyclerView.setAdapter(adapter);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.home, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.about) {
        } else if (id == R.id.logout) {
            Intent MainActivity = new Intent(Home.this,LogInActivity.class);
            MainActivity.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(MainActivity);
        }
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
