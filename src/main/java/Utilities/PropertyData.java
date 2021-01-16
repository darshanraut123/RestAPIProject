package Utilities;

import java.io.FileInputStream;
import java.util.Properties;

public class PropertyData {
	private static String fileLocation = "properties.txt";

	public String getValue(String key) throws Exception {
		Properties prop = new Properties();
		FileInputStream fis = new FileInputStream(fileLocation);
		prop.load(fis);
		return prop.getProperty(key);
	}
}