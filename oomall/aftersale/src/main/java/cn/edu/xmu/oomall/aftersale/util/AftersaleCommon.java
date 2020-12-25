package cn.edu.xmu.oomall.aftersale.util;

/**
 * @author Qiuyan Qian
 * @date Created in 2020/12/10 23:45
 */
public class AftersaleCommon {

    private static final String timeRegex = "^((([0-9]{3}[1-9]|[0-9]{2}[1-9][0-9]{1}|[0-9]{1}[1-9][0-9]{2}|[1-9][0-9]{3})-(((0[13578]|1[02])-(0[1-9]|[12][0-9]|3[01]))|((0[469]|11)-(0[1-9]|[12][0-9]|30))|(02-(0[1-9]|[1][0-9]|2[0-8]))))|((([0-9]{2})(0[48]|[2468][048]|[13579][26])|((0[48]|[2468][048]|[3579][26])00))-02-29))\\s+([0-1]?[0-9]|2[0-3]):([0-5][0-9]):([0-5][0-9])$";

    /**
     * 判断string类型的日期是否有效
     * @param stringTime
     * @return boolean
     * @author  Qiuyan Qian
     * @date  Created in 2020/12/11 上午12:20
    */
    public static boolean judgeTimeValid(String stringTime){
        if(null == stringTime){
            return true;
        }
        if(stringTime.matches(timeRegex)){
            return true;
        }
        return false;
    }
}
