package com.yeyaxi.MetroCentreStaffDiscount;

import android.app.Activity;
import android.os.Bundle;
import android.widget.TextView;

public class ShopInfoActivity extends Activity{
	
	private TextView mShopName;
	private TextView mShopInfo;
	
	@Override
	public void onCreate(Bundle icicle) {
		super.onCreate(icicle);
		setContentView(R.layout.info);
		
		mShopName = (TextView)findViewById(R.id.shopname);
		mShopInfo = (TextView)findViewById(R.id.shopinfo);
	}

}
