package com.yeyaxi.MetroCentreStaffDiscount;

import com.google.ads.AdRequest;
import com.google.ads.AdSize;
import com.google.ads.AdView;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.widget.LinearLayout;
import android.widget.TextView;

/**
 * Displays the explicit info
 * @author Yaxi Ye
 *
 */
public class ShopInfoActivity extends Activity{
	
	private TextView mShopName;
	private TextView mShopInfo;
	private AdView adView;
	
	
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
		
		 // Create the adView
        adView = new AdView(this, AdSize.BANNER, Constants.MY_AD_UNIT_ID);

        // Lookup your LinearLayout assuming it’s been given
        // the attribute android:id="@+id/mainLayout"
        LinearLayout layout = (LinearLayout)findViewById(R.id.adLayout);

        // Add the adView to it
        layout.addView(adView);

        // Initiate a generic request to load it with an ad
        adView.loadAd(new AdRequest());
		
	}

}
