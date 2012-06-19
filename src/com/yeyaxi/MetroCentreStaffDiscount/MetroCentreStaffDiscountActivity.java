package com.yeyaxi.MetroCentreStaffDiscount;

import java.io.IOException;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;

public class MetroCentreStaffDiscountActivity extends Activity {
	private DataBaseHelper mDataBase;

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
        Merchant shop = mDataBase.getShopInfo("cafe");
        String result = shop.getShopName() + shop.getDiscount() + shop.getNote();
        Log.d("Query Test:", result);
    }
    
 
}