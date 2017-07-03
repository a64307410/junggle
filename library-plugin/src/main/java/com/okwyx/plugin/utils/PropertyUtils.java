package com.okwyx.plugin.utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Properties;

/**
 * Property 配置项 ，功能同SharedPreferences 路径可自己指定任意可写目录
 */
public class PropertyUtils {

	public static String loadPropString(String path, String key, String defaultValue) {
		Properties p = createFromFile(path);
		return p.getProperty(key, defaultValue);
	}

	public static void savePropString(String path, String key, String value) {
		Properties prop = createFromFile(path);
		prop.put(key, value);
		writeToFile(prop, path);
	}

	private static Properties createFromFile(String fileName) {
		return createFromFile(new File(fileName));
	}

	private static Properties createFromFile(File file) {
		Properties prop = new Properties();
		try {
			if (!file.exists()) {
				if (!file.getParentFile().exists()) {
					file.getParentFile().mkdirs();
				}
				file.createNewFile();
			}
			loadFromFile(prop, file);
		} catch (Throwable t) {
			t.printStackTrace();
		}
		return prop;
	}

	public static Properties createFromInputstream(InputStream in) {
		Properties prop = new Properties();
		if (in != null) {
			loadFromInputstreamWithUtf8(prop, in);
		}
		return prop;
	}

	/**
	 * 指定utf-8编码方式读取properties文件
	 * @param in
	 * @return
	 */
	public static Properties createFromInputstreamWithUtf8(InputStream in) {
		Properties prop = new Properties();
		if (in != null) {
			loadFromInputstreamWithUtf8(prop, in);
		}
		return prop;
	}

	private static void loadFromFile(Properties p, File file) {
		FileInputStream fis = null;
		try {
			fis = new FileInputStream(file);
			p.load(fis);
		} catch (Throwable t) {
			t.printStackTrace();
		} finally {
			if (fis != null) {
				try {
					fis.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}

	public static void loadFromInputstream(Properties p, InputStream in) {
		try {
			p.load(in);
		} catch (Throwable t) {
			t.printStackTrace();
		} finally {
			if (in != null) {
				try {
					in.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}

	private static void loadFromInputstreamWithUtf8(Properties p, InputStream in) {
		try {
			InputStreamReader ir = new InputStreamReader(in, "UTF-8");
			p.load(ir);
		} catch (Throwable t) {
			t.printStackTrace();
		} finally {
			if (in != null) {
				try {
					in.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}

	private static void writeToFile(Properties p, String fileName) {
		writeToFile(p, new File(fileName), null);
	}

	private static void writeToFile(Properties p, File file, String header) {
		FileOutputStream fos = null;
		try {
			fos = new FileOutputStream(file);
			p.store(fos, header);
		} catch (Exception e) {

		} finally {
			if (fos != null) {
				try {
					fos.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}

}
