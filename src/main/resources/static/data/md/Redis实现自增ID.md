---
title: Redis实现自增ID
date: 2024-02-08 22:13:38
index_img: /img/2024年2月8日22点14分.jpg
excerpt: Redis全局唯一 ID 生成策略
tags: 
- 唯一ID
categories:
- Redis
---

### 全局唯一ID生成策略

**·	UUID**

**·	Redis自增**

**·	snowflake算法**

**·	数据库自增**

 

### Redis 自增 ID 策略

<p class="note note-success"> 1. 每天一个key，方便统计订单量</p>

<p class="note note-success"> 2. ID构造是 <b>时间戳</b> + <b>计数器</b></p>

实现代码：

```java
@Component
public class RedisIdWorker {

    @Resource
    private StringRedisTemplate stringRedisTemplate;

    //开始时间戳
    private static final long BEGIN_TIMESTAMP = 1640995200L;

    //序列号的位数
    private static final long COUNT_BITS = 32;

    public long nextId(String keyPrefix) {
        // 1.生成时间戳
        LocalDateTime now = LocalDateTime.now();
        long newSecond = now.toEpochSecond(ZoneOffset.UTC);
        long timestamp = newSecond - BEGIN_TIMESTAMP;
        // 2.生成序列号
        // 2.1.获取当前日期，精确到天
        String date = now.format(DateTimeFormatter.ofPattern("yyyy:MM:dd"));
        // 2.2.自增长
        long count = stringRedisTemplate.opsForValue().increment("icr:" + keyPrefix + ":" + date);
        // 3.拼接并返回
        return timestamp << COUNT_BITS | count;
    }
}
```

创建一个500个线程池，然后创建一个任务，每一个任务来就执行100次，总共提交300次，测试代码：

```java
@Resource
private RedisIdWorker redisIdWorker;

private final ExecutorService es = Executors.newFixedThreadPool(500);

@Test
void testIdWorker() throws InterruptedException {
    CountDownLatch latch = new CountDownLatch(300);
    Runnable task = () -> {
        for (int i = 0; i < 100; i++) {
            long id = redisIdWorker.nextId("order");
            System.out.println("id = " + id);
        }
        latch.countDown();
    };
    long begin = System.currentTimeMillis();
    for (int i = 0; i < 300; i++) {
        es.submit(task);
    }
    latch.await();
    long end = System.currentTimeMillis();

    System.out.println("time = " + (end - begin));
}
```

执行之后查看Redis，ID自增长到30000

{% asset_img s1.jpg %}
