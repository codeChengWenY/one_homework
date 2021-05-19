package util;

import java.io.*;
import java.net.URI;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class MyClassLoader extends ClassLoader {
    private String classPath;
    private String classLoaderName;

    public MyClassLoader(String classPath, String classLoaderName) {
        this.classPath = classPath.replace("\\", "/");
        this.classLoaderName = classLoaderName;
    }

    @Override
    public Class<?> findClass(String name) throws ClassNotFoundException {
        byte[] cLassBytes = null;
        Path path=null;
        try {
            //这种方式读取路径只能用/
            path = Paths.get(new URI("file:///"+classPath+".class"));
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }
        try {
            cLassBytes = Files.readAllBytes(path);
        } catch (IOException e) {
            e.printStackTrace();
        }

        Class cLass = defineClass(cLassBytes, 0, cLassBytes.length);

        return  cLass;
    }

}