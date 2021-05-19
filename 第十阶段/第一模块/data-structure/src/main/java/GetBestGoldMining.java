import java.util.Arrays;

/**
 * @ClassName GetBestGoldMining
 * @Description: 递归实现
 * @Author CoderCheng
 * @Date 2021-04-08 10:06
 * @Version V1.0
 **/
public class GetBestGoldMining {


    /**
     * @Description:
     * @Param: w 工人数量
     * @Param: n 可选金矿数量
     * @Param: p 金矿开采所需的工人数量
     * @Param: g 金矿储量
     * @Return: int
     * @Author: CoderCheng
     * @Date: 2021/4/8
     * @Time: 10:08
    **/
    public static  int  getBestGoldMining(int w,int n,int[] p ,int[] g){

        System.out.println(" w: "+w+" n "+n +" p "+ Arrays.toString(p) +" g "+ Arrays.toString(g) );

        if(w==0 ||n==0){
            return  0;
        }
        if(w<p[n-1]){
            System.out.println(" w="+w +" p[n-1]="+p[n-1]+" n "+ n );
            return  getBestGoldMining(w,n-1,p,g);
        }
        int max = Math.max(getBestGoldMining(w, n - 1, p, g), getBestGoldMining(w - p[n - 1], n - 1, p, g) + g[n - 1]);

        System.out.println("价格最大是 "+max);
        return  max;
    }

    /**
     * @Description:
     * @Param: w 工人数量
     * @Param: p 金矿开采所需的工人数量
     * @Param: g 金矿储量
     * @Return: int
     * @Author: CoderCheng
     * @Date: 2021/4/8
     * @Time: 10:08
     **/
    public static  int  getBestGoldMiningV2(int w,int[] p ,int[] g){
        // 创建表格
        int[][] resultTable= new int[g.length+1][w+1];
        for (int i = 1; i <=g.length; i++) {
            for (int j = 1; j <= w; j++) {
                if(j<p[i-1]){
                    resultTable[i][j]=resultTable[i-1][j];
                }else {
                    resultTable[i][j]=Math.max(resultTable[i-1][j],resultTable[i-1][j-p[i-1]]+ g[i-1]);
                }
            }
        }
        //返回最后一个格子的值
        return  resultTable[g.length][w];
    }

    public static void main(String[] args) {
        int w=5;
        int[] p={5,10};
        int[] g={400,1350};
        //System.out.println("最优效益 "+getBestGoldMining(w, g.length, p,g));
        System.out.println("最优效益 "+getBestGoldMiningV2(w, p,g));

    }
}
