/**
 * @ClassName RKalth
 * @Description:
 * @Author CoderCheng
 * @Date 2021-04-06 14:42
 * @Version V1.0
 **/
public class RKalth {


    public static void main(String[] args) {


        System.out.println(isMatch("abc", "bc"));
    }


    public static boolean isMatch(String main, String sub) {


        int hash_sub = strToHash(sub);
        for (int i = 0; i < main.length() - sub.length(); i++) {
            if (hash_sub == strToHash(main.substring(i, i + sub.length()))) {

                return true;

            }

        }


        return false;


    }

    public static int strToHash(String src) {


        int hash = 0;


        for (int i = 0; i < src.length(); i++) {
            hash *= 26;
            hash += src.charAt(i) - 97;


        }

        return hash;
    }


}
