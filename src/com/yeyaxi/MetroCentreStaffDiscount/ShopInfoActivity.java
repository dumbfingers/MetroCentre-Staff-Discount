package com.yeyaxi.MetroCentreStaffDiscount;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
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
		
		Intent intent = getIntent();
		Bundle bundle = intent.getBundleExtra("info");
//		Cursor c = (Cursor)bundle.getParcelable("cursor");
		
		mShopName.setText(bundle.getString("shopname"));
		mShopInfo.setText(bundle.getString("note"));
		
	}

}
