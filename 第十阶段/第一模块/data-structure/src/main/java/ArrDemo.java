import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

/**
 * @ClassName ArrDemo
 * @Description:
 * @Author CoderCheng
 * @Date 2021-04-07 16:30
 * @Version V1.0
 **/
public class ArrDemo {


    public static void main(String[] args) {
        int[] nums={1,2,2};
        System.out.println(isOne(nums)?"YES":"NO");

    }



     /**
      * @description:
      * @param: nums
      * @param: b
      * @return: boolean
      * @author: CoderCheng
      * @date: 2021/4/13
      * @time: 15:14
      */
    public  static boolean isOne(int[] nums){
        if(nums.length==0)return  true;
        Map<Integer,String> map=new HashMap();
        for (int i = 0; i < nums.length; i++) {
            int num = nums[i];
            if(Objects.isNull(map.get(num))){
                map.put(num,"");
            }else{
                return  false;
            }
        }
        return true;
    }

}
