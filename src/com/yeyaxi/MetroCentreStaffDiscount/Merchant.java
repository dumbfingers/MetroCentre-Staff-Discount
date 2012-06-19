package com.yeyaxi.MetroCentreStaffDiscount;

/**
 * Merchant
 * 
 * Class for Shop Info
 * 
 * @author Yaxi Ye
 *
 */
public class Merchant {
	private int _id;
	private String _ShopName;
	private double _Discount;
	private String _Note;
	
	/**
	 * Merchant
	 * empty constructor for override
	 */
	public Merchant() {
		
	}
	
	/**
	 * Merchant
	 * @param id
	 * @param name
	 * @param discount
	 * @param note
	 */
	public Merchant(int id, String name, double discount, String note) {
		this._id = id;
		this._ShopName = name;
		this._Discount = discount;
		this._Note = note;
	}
	
	/**
	 * Merchant
	 * @param name
	 * @param discount
	 * @param note
	 */
	public Merchant(String name, double discount, String note) {
		this._ShopName = name;
		this._Discount = discount;
		this._Note = note;
	}

	/**
	 * getId
	 * @return
	 */
	public int getId() {

		return this._id;
	}

	/**
	 * getShopName
	 * @param Id
	 * @return
	 */
	public String getShopName() {

		return this._ShopName;
	}

	/**
	 * getDiscount
	 * @param Id
	 * @return
	 */
	public double getDiscount() {

		return this._Discount;
	}
	
	/**
	 * getNote
	 * @param Id
	 * @return
	 */
	public String getNote() {
		
		return this._Note;
	}

}
