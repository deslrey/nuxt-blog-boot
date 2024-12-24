package org.deslre;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.StringRedisTemplate;

import javax.annotation.Resource;
import java.util.concurrent.TimeUnit;

@SpringBootTest
class DeslreBlogApplicationTests {

    @Resource
    private StringRedisTemplate stringRedisTemplate;

    private static final String LOGIN_STATUS_KEY = "user:login:status:";
    private static final long LOGIN_TIMEOUT = 30; // 超时时间（分钟）

    /**
     * 测试存储登录状态并设置过期时间
     */
    @Test
    void testSetLoginStatus() {
        String userId = "admin"; // 假设当前用户ID
        String loginKey = LOGIN_STATUS_KEY + userId;

        // 存储登录状态
        stringRedisTemplate.opsForValue().set(loginKey, "1", LOGIN_TIMEOUT, TimeUnit.MINUTES);
        System.out.println("登录状态已设置，过期时间为半小时。");

        // 验证是否成功设置
        String status = stringRedisTemplate.opsForValue().get(loginKey);
        System.out.println("当前登录状态: " + status);
    }

    /**
     * 测试登录续期
     */
    @Test
    void testRenewLogin() {
        String userId = "123"; // 假设当前用户ID
        String loginKey = LOGIN_STATUS_KEY + userId;

        // 检查用户是否已登录
        Boolean hasKey = stringRedisTemplate.hasKey(loginKey);
        if (Boolean.TRUE.equals(hasKey)) {
            // 续期登录状态
            stringRedisTemplate.expire(loginKey, LOGIN_TIMEOUT, TimeUnit.MINUTES);
            System.out.println("登录状态已续期，过期时间延长为半小时。");
        } else {
            System.out.println("用户未登录或登录已过期，无法续期。");
        }
    }
}
