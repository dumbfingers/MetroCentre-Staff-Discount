package com.yeyaxi.MetroCentreStaffDiscount;

import java.io.IOException;
import java.util.ArrayList;

import android.app.Activity;
import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.SearchView;
import android.widget.TextView;

public class MetroCentreStaffDiscountActivity extends Activity {
	private DataBaseHelper mDataBase;
	private Merchant shop;
	private ArrayList<Comparable> result = new ArrayList<Comparable>();
	private TextView mShopName;
	private TextView mShopInfo;

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
        setContentView(R.layout.result);
        
        mShopName = (TextView) findViewById(R.id.shopname);
        mShopInfo = (TextView) findViewById(R.id.shopinfo);

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
    private void handleIntent (Intent intent) {
    	if (Intent.ACTION_SEARCH.equals(intent.getAction())) {
    		String query = intent.getStringExtra(SearchManager.QUERY);
    		doQuery(query);
    		// Get Shop Name from result
    		mShopName.setText((String)result.get(0));
    		
    		// Get Discount Info
    		//mShopInfo.setText(result[1]);
    		
    		// Get Note Info
    		mShopInfo.setText((String)result.get(2));
    	}
    }
    
    /**
     * Do database query
     * @param query
     * @return Query Results
     */
    private ArrayList<Comparable> doQuery(String query){
		shop = mDataBase.getShopInfo(query);
		result.add(0, shop.getShopName());
		//String doubleValue = new DecimalFormat().format(shop.getDiscount() * 10);
    	result.add(1, shop.getDiscount());
    	result.add(2, shop.getNote());
//        Log.d("Query Test: ", result);
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