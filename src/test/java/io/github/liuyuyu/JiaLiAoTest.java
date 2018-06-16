package io.github.liuyuyu;

import com.fasterxml.jackson.databind.DeserializationConfig;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.github.liuyuyu.model.UserOrder;
import org.junit.BeforeClass;
import org.junit.Test;

import java.io.*;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static org.junit.Assert.*;

/**
 * @author liuyuyu
 */
public class JiaLiAoTest {
    @BeforeClass
    public static void setUp(){
        ObjectMapper mapper = new ObjectMapper();
        //设置时间格式
        SimpleDateFormat myDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        mapper.setDateFormat(myDateFormat);
        JiaLiAo.renascence(mapper);
    }

    @Test
    public void testWrite() throws IOException {
        //导出
        List<UserOrder> dataList = IntStream.range(0, 100)
                .boxed()
                .map(i -> {
                    UserOrder userOrder = new UserOrder();
                    userOrder.setMobile("1330000" + i);
                    userOrder.setTotalMoney(BigDecimal.valueOf(i));
                    userOrder.setTotalOrder(Long.valueOf(i));
                    userOrder.setUserName("user-" + i);
                    userOrder.setCreatedTime(new Date());
                    return userOrder;
                })
                .collect(Collectors.toList());
        OutputStream os = new FileOutputStream("out/userOrder.xlsx");
        JiaLiAo.r(UserOrder.class, false)
                .e(dataList)
                .q("导出的订单", os);
    }

    @Test
    public void testRead() throws IOException {
        List<UserOrder> list = JiaLiAo.r(UserOrder.class, Boolean.FALSE)
                .w(Boolean.TRUE, new FileInputStream("out/userOrder.xlsx"))
                .q();
        for (UserOrder o : list) {
            System.out.println(o);
        }
    }

}