package com.lrm.util;

import java.text.SimpleDateFormat;
import java.util.concurrent.ThreadLocalRandom;
import org.joda.time.DateTime;

/**
 * 订单编号唯一码
 * @author Administrator
 */
public class RandomUtil {

    private static final SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMddHHmmssSS");

    private static final ThreadLocalRandom RANDOM = ThreadLocalRandom.current();

    /**
     * 生成唯一订单号
     * @return
     */
    public static String generaterOrderCode(){
        return formatter.format(DateTime.now().toDate())+generaterOrderNumber(4);
    }


    public static String generaterOrderNumber(final int num){
        StringBuffer sb = new StringBuffer();

        for (int i = 0; i <num ; i++) {
            sb.append(RANDOM.nextInt(9));
        }
        return sb.toString();
    }

    public static void main(String[] args) {
        for (int i = 0; i < 100000; i++) {
            System.out.println(generaterOrderCode());

        }


    }
}
