package com.tyn.jasca.common;

import java.io.BufferedOutputStream;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Properties;

import org.apache.commons.io.IOUtils;

/**
 * 
 * @author S.J.H.
 */
public class Utils {
	
	/**
	 * 
	 */
	public static final int EOF = -1;
	
	/**
	 * 
	 * @param str
	 * @return
	 */
    public static boolean isEmpty(String str) {
        return str == null || str.length() == 0;
    }
	
	/**
	 * 
	 * @param path
	 * @return
	 */
	public static String getSlashedPath(String path) {
		return path.replace('\\', '/');
	}
	
	/**
	 * 
	 * @param filepath
	 * @return
	 */
	public static String getDirName(String filepath) {
		final String path = getSlashedPath(filepath);
		final int offset = path.lastIndexOf('/');
		return path.substring(0, offset);
	}
	
	/**
	 * 
	 * @param filepath
	 * @return
	 */
	public static String getFileName(String filepath) {
		final String path = getSlashedPath(filepath);
		final int offset = path.lastIndexOf('/');
		return path.substring(offset + 1);
	}
	
	/**
	 * 
	 * @param filepath
	 * @return
	 */
	public static String getFileNameExcludeExt(String filepath) {
		final String filename = getFileName(filepath);
		final int offset = filename.lastIndexOf('.');
		return filename.substring(0, offset);
	}
	
	/**
	 * 
	 * @return
	 */
	public static String getCurrDatetime() {
		return new SimpleDateFormat("yyyy.MM.dd HH:mm:ss")
			.format(Calendar.getInstance().getTime());
	}
	
	/**
	 * 
	 * @param properties
	 * @return
	 */
	public static String serializeProperties(Properties properties) {
		ByteArrayOutputStream bos = new ByteArrayOutputStream();
		try {
			properties.store(bos, null);
			return bos.toString();
		}
		catch (IOException ioe) {
			return null;
		}
		finally {
			IOUtils.closeQuietly(bos);
		}
	}
	
	/**
	 * 
	 * @param string
	 * @return
	 */
	public static Properties deserializeProperties(String string) {
		ByteArrayInputStream bis = new ByteArrayInputStream(string.getBytes());
		try {
			Properties properties = new Properties();
			properties.load(bis);
			return properties;
		}
		catch (IOException ioe) {
			return null;
		}
		finally {
			IOUtils.closeQuietly(bis);
		}
	}
	
	/**
	 * 
	 * @param resource
	 * @param filepath
	 * @throws IOException
	 */
	public static void resourceToFile(String resource, String filepath) throws IOException {
		final int BUFFER_SIZE = 10240;
		ClassLoader classLoader = Utils.class.getClassLoader();
		
		InputStream resourceInputStream = null;
		BufferedOutputStream fileOutputStream = null;
		
		try {
			resourceInputStream = classLoader.getResourceAsStream(resource);
			fileOutputStream = new BufferedOutputStream(new FileOutputStream(new File(filepath)));
			
			int readSize = 0;
			byte[] buffer = new byte[BUFFER_SIZE];
			while ((readSize = resourceInputStream.read(buffer)) != EOF) {
				fileOutputStream.write(buffer, 0, readSize);
			}
		}
		catch (IOException ioe) {
			throw ioe;
		}
		finally {
			IOUtils.closeQuietly(resourceInputStream);
			IOUtils.closeQuietly(fileOutputStream);
		}
	}
	
	/**
	 * 
	 * @param resources
	 * @param basepath
	 */
	public static void resourcesToFiles(String[] resources, String basepath) throws IOException {
		if (!basepath.endsWith("/")) {
			basepath += "/";
		}
		
		for (String resource : resources) {
			resourceToFile(resource, basepath + getFileName(resource));
		}
	}
	
	/**
	 * 
	 * @param srcBasepath
	 * @param srcFilenames
	 * @param tgtBasepath
	 * @throws IOException
	 */
	public static void resourcesToFiles(String srcBasepath, String[] srcFilenames, String tgtBasepath) throws IOException {
		if (!srcBasepath.endsWith("/")) {
			srcBasepath += "/";
		}
		
		int index = 0;
		String[] resources = new String[srcFilenames.length];
		for (String srcFilename : srcFilenames) {
			resources[index++] = srcBasepath + srcFilename;
		}
		
		resourcesToFiles(resources, tgtBasepath);
	}
}
