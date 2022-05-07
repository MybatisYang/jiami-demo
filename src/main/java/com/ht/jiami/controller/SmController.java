package com.ht.jiami.controller;

import cn.hutool.core.lang.UUID;
import com.alibaba.fastjson.JSON;
import com.ht.jiami.entity.Warn;
import com.ht.jiami.util.MySmUtil;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;

/**
 * @Description: 加密测试
 * @Author: yjs
 * @createTime: 2022年05月05日 10:01:06
 * @version: 1.0
 */
@RestController
public class SmController {

    @RequestMapping("test")
    public String test(){
        return "test";
    }

    @RequestMapping("noEncode")
    public String noEncode(String encode){
        System.out.println("明文传输：" + encode);
        return encode;
    }

    @RequestMapping("sm2Encode")
    public String sm2Encode(String encode){
        return MySmUtil.sm2Encode(encode);
    }

    @RequestMapping("sm2Decode")
    public String sm2Decode(String encode){
        return MySmUtil.sm2Decode(encode);
    }

    @RequestMapping("sm3Encode")
    public String sm3Encode(String encode){
        return MySmUtil.encrypt(encode);
    }

    @RequestMapping("sm3Decode")
    public boolean sm3Decode(String encode, String str){
        boolean s = true;
        try {
            s = MySmUtil.verify(str, encode);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return s;
    }

    @RequestMapping("sm4Encode")
    public String sm4Encode(String encode){
        return MySmUtil.sm4Encode(encode);
    }

    @RequestMapping("sm4Decode")
    public String sm4Decode(String encode){
        return MySmUtil.sm4Decode(encode);
    }



    @RequestMapping("sm2")
    public String sm2(){
        Warn warn = new Warn();
        warn.setId(UUID.randomUUID() + "");
        warn.setAreaCode("100010");
        warn.setCallId(UUID.randomUUID() + "");
        warn.setName("预警信息");
        warn.setPhone("13986555135");
        warn.setStatus("1");
        warn.setCreateTime(new Date());
        warn.setUpdateTime(new Date());
        return MySmUtil.sm2Decode(MySmUtil.sm2Encode(JSON.toJSONString(warn)));
    }

    @RequestMapping("sm3")
    public String sm3(){
        Warn warn = new Warn();
        warn.setId(UUID.randomUUID() + "");
        warn.setAreaCode("100010");
        warn.setCallId(UUID.randomUUID() + "");
        warn.setName("预警信息");
        warn.setPhone("13986555135");
        warn.setStatus("1");
        warn.setCreateTime(new Date());
        warn.setUpdateTime(new Date());
        String en = MySmUtil.encrypt(JSON.toJSONString(warn));
        System.out.println(en);
        try {
            boolean b = MySmUtil.verify(JSON.toJSONString(warn), en);
            System.out.println(b);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return en;
    }


    @RequestMapping("sm4")
    public String sm4(){
        Warn warn = new Warn();
        warn.setId(UUID.randomUUID() + "");
        warn.setAreaCode("100010");
        warn.setCallId(UUID.randomUUID() + "");
        warn.setName("预警信息");
        warn.setPhone("13986555135");
        warn.setStatus("1");
        warn.setCreateTime(new Date());
        warn.setUpdateTime(new Date());
        return MySmUtil.sm4Decode(MySmUtil.sm4Encode(JSON.toJSONString(warn)));
    }
}
