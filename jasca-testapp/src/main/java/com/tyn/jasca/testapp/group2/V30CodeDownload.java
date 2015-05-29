package com.tyn.jasca.testapp.group2;

import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLClassLoader;

public class V30CodeDownload {

	public Object getObject() throws MalformedURLException,
			ClassNotFoundException, IllegalAccessException,
			InstantiationException {

		URL[] classURLs = new URL[] { new URL("file:subdir/") };
		URLClassLoader loader = new URLClassLoader(classURLs);
		Class<?> loadedClass = Class.forName("loadMe", true, loader);
		Class.forName("helloWorld");
		return loadedClass.newInstance();
	}
}
