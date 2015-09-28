package com.test.example.base.forkjoin.sample3;

/**
 * 要抓取的文件的信息，如文件保存的目录，名字，抓取文件的URL等
 * @author Administrator
 *
 */
public class SiteInfoBean {
	
	private String sSiteURL; // Site's URL
	
	private String sFilePath; // Saved File's Path
	
	private String sFileName; // Saved File's Name

	public SiteInfoBean() {
		this("", "", "");
	}

	public SiteInfoBean(String sURL, String sPath, String sName) {
		sSiteURL = sURL;
		sFilePath = sPath;
		sFileName = sName;
	}

	public String getSSiteURL() {
		return sSiteURL;
	}

	public void setSSiteURL(String value) {
		sSiteURL = value;
	}

	public String getSFilePath() {
		return sFilePath;
	}

	public void setSFilePath(String value) {
		sFilePath = value;
	}

	public String getSFileName() {
		return sFileName;
	}

	public void setSFileName(String value) {
		sFileName = value;
	}
}
