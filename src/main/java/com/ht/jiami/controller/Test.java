package com.ht.jiami.controller;

import cn.hutool.core.lang.UUID;
import com.alibaba.fastjson.JSON;
import com.ht.jiami.entity.Warn;
import com.ht.jiami.util.MySmUtil;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @Description: test
 * @Author: yjs
 * @createTime: 2022年05月05日 12:39:55
 * @version: 1.0
 */
public class Test {
    public static void main(String[] args) throws Exception {
        List<String> list = new ArrayList();
        Warn warn = new Warn();
//        warn.setId(UUID.randomUUID() + "");
        warn.setAreaCode("100010");
        warn.setCallId(UUID.randomUUID() + "");
        warn.setName("预警信息");
//        warn.setPhone("13986555135");
        warn.setStatus("1");
        warn.setCreateTime(new Date());
        warn.setUpdateTime(new Date());

        System.out.println("元数据：" + warn.toString());

        String sm2 = MySmUtil.sm2Encode(JSON.toJSONString(warn));
        System.out.println("sm2加密后：" + sm2);
        System.out.println("sm2解密后：" + MySmUtil.sm2Decode(sm2));

        String sm3 = MySmUtil.encrypt(JSON.toJSONString(warn));
        System.out.println("sm3加密后：" + sm3);
        System.out.println("sm3解密后：" + MySmUtil.verify(JSON.toJSONString(warn), sm3));

        String sm4 = MySmUtil.sm4Encode(JSON.toJSONString(warn));
        System.out.println("sm4加密后：" + sm4);
        System.out.println("sm4解密后：" + MySmUtil.sm4Decode(sm4));

        list.add(MySmUtil.sm4Encode(JSON.toJSONString(warn)));


        System.out.println(list.size());


//        int a = Runtime.getRuntime().availableProcessors();//当前设备的CPU个数
//        System.out.println(a);//4
//        long start = System.currentTimeMillis();
//        ExecutorService threadPool = Executors.newFixedThreadPool(8);
//        list.stream().forEach(s -> {
//            threadPool.execute(()->{
//                //因为线程池大小为a，每个任务输出temp后sleep 2秒，所以每两秒打印a个数字
//                MySmUtil.sm4Decode(s);
//                try {
//                    Thread.sleep(2000);
//                } catch (InterruptedException e) {
//                    e.printStackTrace();
//                }
//            });
//        });
//        long end = System.currentTimeMillis();
//        System.out.println("解密共计用时：" + (end - start));
    }

}
