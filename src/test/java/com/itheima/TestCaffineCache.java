package com.itheima;

import com.github.benmanes.caffeine.cache.Cache;
import com.github.benmanes.caffeine.cache.stats.CacheStats;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Arrays;
import java.util.List;

/**
 * @author : itheima
 * @description :
 * 引入依赖：
 *         <dependency>
 *             <groupId>com.github.ben-manes.caffeine</groupId>
 *             <artifactId>caffeine</artifactId>
 *         </dependency>
 */
@SpringBootTest
public class TestCaffineCache {

    @Autowired
    private Cache<String,Object> caffeineCache;

    /**
     * @desc  添加缓存 获取缓存
     */
    @Test
    public void test01() {
        List<Integer> datas= Arrays.asList(100,200,300,400);
        //缓存数据
        caffeineCache.put("innerInfos",datas);
        //获取缓存数据
        Object datas2 = caffeineCache.getIfPresent("innerInfos");
        System.out.println(datas2==datas);
        //刷新缓存
        List<Integer> datas3= Arrays.asList(500,600,700,800);
        caffeineCache.put("innerInfos",datas3);
        Object data3 = caffeineCache.getIfPresent("innerInfos");
        System.out.println(data3);
    }
    /**
     * @desc  无缓存数据补救方式
     */
    @Test
    public void test02() {
//        List<Integer> datas= Arrays.asList(100,200,300,400);
//        //缓存数据
//        caffeineCache.put("innerInfos",datas);
        //获取缓存数据
        Object data1 = caffeineCache.get("innerInfos", key -> {
            System.out.println(key);
            //可以去数据库动态查询数据 redis mysql 第三方
            return Arrays.asList(500, 600, 700, 800);
        });
        Object data2 = caffeineCache.getIfPresent("innerInfos");

        System.out.println(data1==data2);
    }


    /**
     * @desc 缓存统计
     */
    @Test
    public void test03() {
        List<Integer> datas= Arrays.asList(100,200,300,400);
        //缓存数据
        caffeineCache.put("innerInfos",datas);
        caffeineCache.put("obj",new Object());
        caffeineCache.put("user","666");
        Object data = caffeineCache.getIfPresent("user");
        System.out.println(data);
        CacheStats stats = caffeineCache.stats();//返回统计数据
        double hitRate = stats.hitRate();//命中率
        long evictionCount = stats.evictionCount();//缓存回收数量
        double takeTime = stats.averageLoadPenalty();//加载新值花费的平均时间
    }

}
