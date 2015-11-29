package com.subtitledownloader.utils;

import java.net.Inet4Address;
import java.net.InetSocketAddress;
import java.net.Proxy;
import java.net.UnknownHostException;

public class ProxyUtils {

	public ProxyUtils() {
		// TODO Auto-generated constructor stub
	}

	/* In case of a direct connection.. */
	public static Proxy getProxy() {
		System.out.println("No proxy specified.. Using a Direct connection");
		return Proxy.NO_PROXY;
	}

	/* An overloaded method */
	public static Proxy getProxy(byte[] proxyIpAddrBytes, int portNum) {
		/* Pass the proxy address and port as per your needs */
		System.out.println("Proxy specified.");
		InetSocketAddress address = null;
		// here we are configuring a HTTP proxy.. our Http request is being
		// routed via a proxy with IP address 153.88.253.150 and port 8080
		byte[] b = proxyIpAddrBytes;
		try {
			address = new InetSocketAddress(Inet4Address.getByAddress(b),
					portNum);
		} catch (UnknownHostException e1) {
			// TODO Auto-generated catch block
			System.out.println("Error! Unhandled host exceptions");
			e1.printStackTrace();
		}
		Proxy proxy = new Proxy(Proxy.Type.HTTP, address);
		return proxy;
	}

}
