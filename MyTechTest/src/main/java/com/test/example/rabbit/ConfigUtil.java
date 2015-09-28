package com.test.example.rabbit;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;
import java.util.StringTokenizer;

import com.rabbitmq.client.Address;

public class ConfigUtil {

	private static Properties p = new Properties();

	private static Address[] addresses = null;

	private static final String CONFIG_FILE = "/com/test/example/rabbit/rabbitmq.properties";

	static {

		try (InputStream is = ConfigUtil.class.getResourceAsStream(CONFIG_FILE);) {
			p.load(is);
			String hostandport = p.getProperty("hostandport");
			StringTokenizer st = new StringTokenizer(hostandport, ",", false);
			addresses = new Address[st.countTokens()];
			int i = 0;
			while (st.hasMoreElements()) {
				String hp = st.nextToken();
				int position = hp.indexOf(":");
				addresses[i++] = new Address(hp.substring(0, position), Integer.parseInt(hp.substring(position + 1)));
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static Address[] getAddresses() {
		return addresses;
	}

}
