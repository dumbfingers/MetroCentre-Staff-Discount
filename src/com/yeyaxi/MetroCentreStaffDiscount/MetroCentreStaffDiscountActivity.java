package com.yeyaxi.MetroCentreStaffDiscount;

import java.io.IOException;
import java.util.ArrayList;

import android.app.Activity;
import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.os.Parcelable;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.SimpleCursorAdapter;
import android.widget.TextView;

public class MetroCentreStaffDiscountActivity extends Activity {
	private DataBaseHelper mDataBase;
	private Merchant shop;
	private ArrayList<Comparable> result = new ArrayList<Comparable>();
	private ListAdapter adapter;
	private ListView mListView;
	private Cursor cursor;
	

    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mDataBase = new DataBaseHelper(this); 
        try {
			mDataBase.createDataBase();
		} catch (IOException e) {
			throw new Error("Unable to create database");
		}
        setContentView(R.layout.main);
                
//        mShopName = (TextView) findViewById(R.id.shopname);
//        mShopInfo = (TextView) findViewById(R.id.shopinfo);
        mListView = (ListView)findViewById(R.id.resultListView);
        handleIntent(getIntent());

    }
    
    @Override
    protected void onNewIntent(Intent intent) {
        // Because this activity has set launchMode="singleTop", the system calls this method
        // to deliver the intent if this activity is currently the foreground activity when
        // invoked again (when the user executes a search from this activity, we don't create
        // a new instance of this activity, so the system delivers the search intent here)
        handleIntent(intent);
    }
    
    /**
     * handle intent
     * @param intent
     */
    @SuppressWarnings("deprecation")
	private void handleIntent (Intent intent) {
    	if (Intent.ACTION_SEARCH.equals(intent.getAction())) {
    		String query = intent.getStringExtra(SearchManager.QUERY);
    		doQuery(query);
    		// Get Shop Name from result
//    		mShopName.setText((String)result.get(0));
    		
    		// Get Discount Info
    		//mShopInfo.setText(result[1]);
    		
    		// Get Note Info
//    		mShopInfo.setText((String)result.get(2));
    		
            adapter = new SimpleCursorAdapter(this, R.layout.result, cursor, mDataBase.getDBColumnNames(), new int[]{R.id.shopname, R.id.shopinfo} );
            mListView.setAdapter(adapter);
            mListView.setOnItemClickListener(new OnItemClickListener() {
            	
            	public void onItemClick(AdapterView<?> parent, View view,
						int position, long id) {
//					bundle.putInt("position", position);
//					bundle.putLong("id", id);
            		Cursor c = (Cursor) parent.getItemAtPosition(position);
            		Intent shopIntent = new Intent(getApplicationContext(), ShopInfoActivity.class);
					Bundle bundle = new Bundle();
					bundle.putString("shopname", c.getString(1));
					bundle.putString("note", c.getString(3));
            		shopIntent.putExtra("info", bundle);
            		startActivity(shopIntent);
				}
            	
            });
            
    	}
    }
    
    
    
    /**
     * Do database query
     * @param query
     * @return Query Results
     */
    private ArrayList<Comparable> doQuery(String query){
		shop = getShopInfo(query);
		result.add(0, shop.getShopName());
		//String doubleValue = new DecimalFormat().format(shop.getDiscount() * 10);
    	result.add(1, shop.getDiscount());
    	result.add(2, shop.getNote());
//        Log.d("Query Test: ", result);
    	return result;
    }
    
    /**
     * Get Shop Info from Database
     * @param shopname
     * @return packed shop info
     */
    private Merchant getShopInfo(String shopname) {
    	
		SQLiteDatabase db = mDataBase.getReadableDatabase();
    	
		cursor = db.query(mDataBase.getDBTableName(), null, "ShopName MATCH " + "'" + shopname + " OR " + "*" + shopname + " OR " + shopname + "*'", null, null, null, null);
    	
    	if(cursor.moveToFirst()) {
    		
    		Merchant shopQueryInfo = new Merchant(cursor.getString(1), cursor.getDouble(2), cursor.getString(3));
 
    		return shopQueryInfo;
    	
    	}else{
    		
    		// If neither FTS nor MATCH * search return a valid result, return Shop not found.
    		// But this "Shop Not Found" info will not be displayed because the value is passed via cursor,
    		// not merchant anymore.
    		Merchant nullShop = new Merchant("Shop Not Found",0.0,"");
    		return nullShop;
    		
    	}
    	
    }
    
    @Override
    /**
     * Create Action Bar Search Widget
     */
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the options menu from XML
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.options_menu, menu);

        // Get the SearchView and set the searchable configuration
        SearchManager searchManager = (SearchManager) getSystemService(Context.SEARCH_SERVICE);
        SearchView searchView = (SearchView) menu.findItem(R.id.search).getActionView();
        searchView.setSearchableInfo(searchManager.getSearchableInfo(getComponentName()));
        searchView.setIconifiedByDefault(false); // Do not iconify the widget; expand it by default
//        searchView.setSubmitButtonEnabled(true);
        searchView.setQueryRefinementEnabled(true);
        return true;
    }
    
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.search:
                onSearchRequested();
                return true;
            default:
                return false;
        }
    }
    
    
    

 
}