package com.test.example.googlemap;

/**
 * 经纬度
 * @author Administrator
 *
 */
public class Coordinates {
	
	public Coordinates() {
		
	}
	
	public Coordinates(String longitude, String latitude) {
		this.longitude = longitude;
		this.latitude = latitude;
	}

	/**
	 *  纬度
	 */
	private String latitude;
	
	/**
	 *  经度
	 */
	private String longitude;
	
	public String getLatitude() {
		return latitude;
	}
	
	public void setLatitude(String latitude) {
		this.latitude = latitude;
	}
	
	public String getLongitude() {
		return longitude;
	}
	
	public void setLongitude(String longitude) {
		this.longitude = longitude;
	}
	
	

}
