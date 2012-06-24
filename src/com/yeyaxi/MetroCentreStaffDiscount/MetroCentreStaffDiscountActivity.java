package com.yeyaxi.MetroCentreStaffDiscount;

import java.io.IOException;

import android.app.Activity;
import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.widget.SearchView;

public class MetroCentreStaffDiscountActivity extends Activity {
	private DataBaseHelper mDataBase;
	private Merchant shop;
	private String result;

    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mDataBase = new DataBaseHelper(this); 
        try {
			mDataBase.createDataBase();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			throw new Error("Unable to create database");
		}
        setContentView(R.layout.main);
//        Merchant shop = mDataBase.getShopInfo("cafe");
//        String result = shop.getShopName() + shop.getDiscount() + shop.getNote();
//        Log.d("Query Test:", result);
        handleIntent(getIntent());
        Log.d("Query Test: ", result);
    }
    
    /**
     * handleIntent
     * @param intent
     */
    private void handleIntent (Intent intent) {
    	if (Intent.ACTION_SEARCH.equals(intent.getAction())) {
    		String query = intent.getStringExtra(SearchManager.QUERY);
    		doQuery(query);
    	}
    }
    
    /**
     * doQuery
     * @param query
     * @return Query Results
     */
    private String doQuery(String query){
		shop = mDataBase.getShopInfo(query);
    	result = shop.getShopName() + shop.getDiscount() + shop.getNote();    	
    	return result;
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

        return true;
    }
 
}