package Csla;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.InvalidPropertiesFormatException;
import java.util.Properties;

import Csla.Properties.Resources;

public class Configuration {
	public static String getSetting(String key) {
		Properties properties = new Properties();
		try {
			FileInputStream	file = new FileInputStream(Resources.getConfigFileName());
			properties.loadFromXML(file);
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InvalidPropertiesFormatException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return properties.getProperty(key);
	}
}
