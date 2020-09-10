package com.morrisz.tools;

import org.apache.maven.plugin.MojoExecutionException;

import java.io.File;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.List;

/**
 * @author zhangyoumao
 */
public class ClassLoaderHelper {

    private ClassLoader classLoader;

    public ClassLoaderHelper(List<String> runtimeClassPathElements) throws MojoExecutionException {
        classLoader = buildClassLoader(runtimeClassPathElements);
    }

    private ClassLoader buildClassLoader(List<String> runtimeClassPathElements) throws MojoExecutionException {
        URLClassLoader classLoader = null;
        try {
            List<String> list = runtimeClassPathElements;
            URL[] urls = new URL[list.size()];
            for (int i = 0; i < list.size(); i++) {
                String path = list.get(i);
                urls[i] = new File(path).toURI().toURL();
            }
            classLoader = new URLClassLoader(urls, Thread.currentThread().getContextClassLoader());
        } catch (Exception e) {
            throw new MojoExecutionException(e.toString());
        }
        return classLoader;
    }

    public Class<?> loadClass(String className) throws ClassNotFoundException {
        return classLoader.loadClass(className);
    }
}
