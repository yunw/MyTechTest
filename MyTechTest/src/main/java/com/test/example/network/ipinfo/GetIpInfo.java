package com.test.example.network.ipinfo;

import java.net.InetAddress;
import java.net.NetworkInterface;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;

public class GetIpInfo {
	
	public static void main(String[] args) {
//		System.out.println(getLocalIP());
		List<String> ipList = getLocalIPs();
		for (String ip : ipList) {
			System.out.println("ip: " + ip);
		}
	}
	
	/**
	 * 取当前系统站点本地地址 linux下 和 window下可用
	 * 
	 * @return
	 */
	public static List<String> getLocalIPs() {
		InetAddress ip = null;
		List<String> ipList= new ArrayList<String>();
		try {
			// 如果是Windows操作系统
			if (isWindowsOS()) {
				ip = InetAddress.getLocalHost();
			}
			// 如果是Linux操作系统
			else {
				Enumeration<NetworkInterface> netInterfaces = (Enumeration<NetworkInterface>) NetworkInterface
						.getNetworkInterfaces();
				while (netInterfaces.hasMoreElements()) {

					NetworkInterface ni = (NetworkInterface) netInterfaces.nextElement();
					// ----------特定情况，可以考虑用ni.getName判断
					// 遍历所有ip
					Enumeration<InetAddress> ips = ni.getInetAddresses();
					while (ips.hasMoreElements()) {
						ip = ips.nextElement();
						if (ip == null) {
							continue;
						}
						if (ip.isSiteLocalAddress()) {
							System.out.println("This ip: " + ip.getHostAddress() + "  is a site local address.");
						}
						if (ip.isLoopbackAddress()) {
							System.out.println("This ip: " + ip.getHostAddress() + "  is a loopback address.");
						}
						if (ip.isLinkLocalAddress()) {
							System.out.println("This ip: " + ip.getHostAddress() + "  is a link local address.");
						}
						if (ip.isAnyLocalAddress()) {
							System.out.println("This ip: " + ip.getHostAddress() + "  is a any local address.");
						}
						if (ip != null)
						ipList.add(ip.getHostAddress());
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return ipList;
	}

	/**
	 * 取当前系统站点本地地址 linux下 和 window下可用
	 * 
	 * @return
	 */
	public static String getLocalIP() {
		String sIP = "";
		InetAddress ip = null;
		try {
			// 如果是Windows操作系统
			if (isWindowsOS()) {
				ip = InetAddress.getLocalHost();
			}
			// 如果是Linux操作系统
			else {
				boolean bFindIP = false;
				Enumeration<NetworkInterface> netInterfaces = (Enumeration<NetworkInterface>) NetworkInterface
						.getNetworkInterfaces();
				while (netInterfaces.hasMoreElements()) {
					if (bFindIP) {
						break;
					}
					NetworkInterface ni = (NetworkInterface) netInterfaces.nextElement();
					// ----------特定情况，可以考虑用ni.getName判断
					// 遍历所有ip
					Enumeration<InetAddress> ips = ni.getInetAddresses();
					while (ips.hasMoreElements()) {
						ip = (InetAddress) ips.nextElement();
						if (ip.isSiteLocalAddress() && !ip.isLoopbackAddress() // 127.开头的都是lookback地址
								&& ip.getHostAddress().indexOf(":") == -1) {
							bFindIP = true;
							break;
						}
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		if (null != ip) {
			sIP = ip.getHostAddress();
		}
		return sIP;
	}

	/**
	 * 判断当前系统是否windows
	 * 
	 * @return
	 */
	public static boolean isWindowsOS() {
		boolean isWindowsOS = false;
		String osName = System.getProperty("os.name");
		if (osName.toLowerCase().indexOf("windows") > -1) {
			isWindowsOS = true;
		}
		return isWindowsOS;
	}

}
