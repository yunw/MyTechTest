package com.test.example.base.tcpip;

import java.net.Inet4Address;
import java.net.Inet6Address;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.util.Enumeration;

/**
 * InetAddress类代表了一个网络地址(ip
 * address)，包括主机名和数字类型的地址信息。该类有两个子类，Inet4Address和Inet6Address，
 * 分别对应了目前IP地址的两个版本。InetAddress实例是不可变的，一旦创建，每个实例就始终指向同一个地址
 * 
 * @author Administrator
 *
 */
public class InetAddressExample {

	public static void main(String[] args) {
		// Get the network interfaces and associated addresses for this host
		try {
			// 获取主机的网络接口列表
			Enumeration<NetworkInterface> interfaceList = NetworkInterface
					.getNetworkInterfaces();
			if (interfaceList == null) {
				System.out.println("--No interfaces found--");
			} else {
				while (interfaceList.hasMoreElements()) {
					NetworkInterface iface = interfaceList.nextElement();
					// getName()方法为接口返回一个本地名称。接口的本地名称通常由字母与数字的联合组成，
					// 代表了接口的类型和具体实例，如"lo0"或"eth0"。
					System.out.println("Interface " + iface.getName() + ":");
					// getInetAddresses()方法返回了另一个Enumeration类对象，其中包含了InetAddress类的实例，即该接口所关联的每一个地址。
					//根据主机的不同配置，这个地址列表可能只包含IPv4或IPv6地址，或者是包含了两种类型地址的混合列表。
					Enumeration<InetAddress> addrList = iface
							.getInetAddresses();
					if (!addrList.hasMoreElements()) {
						System.out
								.println("\t(No addresses for this interface)");
					}
					while (addrList.hasMoreElements()) {
						InetAddress address = addrList.nextElement();
						System.out
								.print("\tAddress "
										+ ((address instanceof Inet4Address ? "(v4)"
												: (address instanceof Inet6Address ? "(v6)"
														: "(?)"))));
						System.out.println(": " + address.getHostAddress());
					}
				}
			}
		} catch (SocketException se) {
			System.out.println("Error getting network interfaces:"
					+ se.getMessage());
		}
		// Get name(s)/address(es) of hosts given on command line
		for (String host : args) {
			try {
				System.out.println(host + ":");
				InetAddress[] addressList = InetAddress.getAllByName(host);
				for (InetAddress address : addressList) {
					System.out.println("\t" + address.getHostName() + "/"
							+ address.getHostAddress());
				}
			} catch (UnknownHostException e) {
				System.out.println("\tUnable to find address for " + host);
			}
		}
	}
}