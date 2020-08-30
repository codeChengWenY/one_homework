package cn.coder.utils;

import java.io.File;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

/**
 * @ClassName PackageScanUtils
 * @Description:
 * @Author CoderCheng
 * @Date 2020-08-28 16:55
 * @Version V1.0
 **/
public class PackageScanUtils {


    // 用于存放全限定类名的集合
    private static List<String> classNameList = new ArrayList<>();

    /**
     * 扫描包下的所有类文件
     * @param basePackage 扫描包
     * @param excludePackage 排除扫描包
     * @return {@link List<String>}
     */
    public static List<String> doScanner(String basePackage, String excludePackage){
        // 加载资源
        URL resource = PackageScanUtils.class.getClassLoader().getResource(basePackage.replaceAll("\\.", "/"));
        File file = new File(resource.getFile());
        for (File f : file.listFiles()) {
            if (f.isDirectory()){
                doScanner(basePackage + "." + f.getName(), excludePackage);
            } else {
                String className = (basePackage + "." + f.getName()).replace(".class", "");
                // 排除部分包下的类文件
                if (className.contains(excludePackage)){
                    continue;
                }
                classNameList.add(className);
            }
        }
        return classNameList;
    }
}
