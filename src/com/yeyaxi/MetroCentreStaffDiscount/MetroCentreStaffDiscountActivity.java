package com.yeyaxi.MetroCentreStaffDiscount;

import android.app.Activity;
import android.os.Bundle;

public class MetroCentreStaffDiscountActivity extends Activity {
	private DataBaseHelper mDataBase;
	private int _id;
	private String _ShopName;
	private double _Discount;
	private String _Note;
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mDataBase = new DataBaseHelper(getBaseContext()); 
        setContentView(R.layout.main);
    }
    
    /**
     * getId
     * @return
     */
    public int getId() {
    	
    	return _id;
    }
    
    /**
     * getShopName
     * @param Id
     * @return
     */
    public String getShopName(int Id) {
    	
    	return _ShopName;
    }
}