package DF;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class DataFetcher {
    public static void main(String args[]) {
        Calendar d = Calendar.getInstance();
        String startUrl = "http://www.gov.cn/xinwen/2020-12/17/content_5570183.htm";
        String titleTemp = "Title Get: 截至%m月%d日24时新型冠状病毒肺炎疫情最新情况";

        System.out.println("Fetch Start Url:" + startUrl);
        for (int i = 0; i < 50; i++) {
            d.add(Calendar.DAY_OF_YEAR, -1);
            String output = titleTemp;
            output = output.replace("%m", String.valueOf(d.get(Calendar.MONTH)+1));
            output = output.replace("%d", String.valueOf(d.get(Calendar.DAY_OF_MONTH)));
            System.out.println(output);
        }
    }
}
