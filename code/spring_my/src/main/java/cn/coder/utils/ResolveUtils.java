package cn.coder.utils;

/**
 * @ClassName ResolveUtils
 * @Description:
 * @Author CoderCheng
 * @Date 2020-08-28 17:32
 * @Version V1.0
 **/
public class ResolveUtils {

    /**
     * 字符串首字母小写
     * 如果类名首字母包含I，做特殊处理
     * 例如：IAccountDao—>accountDao
     * @param str str
     * @return {@link String}
     */
    public static String lowerFirstCase(String str){
        String newStr = str.substring(str.lastIndexOf(".") + 1, str.length());
        if (newStr.contains("Impl")) {
            newStr = newStr.replaceFirst("Impl", "");
        }
        char[] chars = newStr.toCharArray();
        // 大写转小写
        chars[0] += 32;
        return String.valueOf(chars);
    }
}
