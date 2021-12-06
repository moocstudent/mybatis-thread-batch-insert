package com.example.demos.service;

import com.example.demos.entity.Person;
import com.example.demos.mapper.PersonMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Random;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;

/**
 * @author : huangjie121015
 * @date : 2021/12/2 18:15
 */
@Component
public class PersonService {
    private static final int THREAD_COUNT = 10;
    @Autowired
    private PersonMapper personMapper;
    @Autowired
    private ThreadPoolExecutor executor;
    private AtomicInteger integer = new AtomicInteger();
    private Random random = new Random();
    private String[] names = {"黄某人", "负债程序猿", "谭sir", "郭德纲", "蔡徐鸡", "蔡徐母鸡", "李狗蛋", "铁蛋", "赵铁柱"};
    private String[] addrs = {"二仙桥", "成华大道", "春熙路", "锦里", "宽窄巷子", "双子塔", "天府大道", "软件园", "熊猫大道", "交子大道"};
    private String[] companys = {"京东", "腾讯", "百度", "小米", "米哈游", "网易", "字节跳动", "美团", "蚂蚁", "完美世界"};

    @Scheduled(cron = "0/15 * * * * ?")
    public void insertList() {
        System.out.println("本轮任务开始，总任务数：" + THREAD_COUNT);
        long start = System.currentTimeMillis();
        AtomicLong end = new AtomicLong();
        for (int i = 0; i < THREAD_COUNT; i++) {
            Thread thread = new Thread(() -> {
                try {
                    for (int j = 0; j < 20; j++) {
                        personMapper.insertList(getPersonList(5000));
                    }
                    end.set(System.currentTimeMillis());
                    System.out.println("本轮任务耗时：" + (end.get() - start) + "____已执行" + integer.addAndGet(1) + "个任务" + "____当前队列任务数" + executor.getQueue().size());
                } catch (Exception e) {
                    e.printStackTrace();
                }
            });
            try {
                executor.execute(thread);
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
        }
    }

    private ArrayList<Person> getPersonList(int count) {
        ArrayList<Person> persons = new ArrayList<>(count);
        for (int i = 0; i < count; i++) {
            persons.add(getPerson());
        }
        return persons;
    }

    private Person getPerson() {
        Person person = Person.builder()
                .name(names[random.nextInt(names.length)])
                .phone(18800000000L + random.nextInt(88888888))
                .salary(new BigDecimal(random.nextInt(99999)))
                .company(companys[random.nextInt(companys.length)])
                .ifSingle(random.nextInt(2))
                .sex(random.nextInt(2))
                .address("四川省成都市" + addrs[random.nextInt(addrs.length)])
                .createUser(names[random.nextInt(names.length)]).build();
        return person;
    }
}
