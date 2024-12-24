package org.deslre.utils;

import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.concurrent.TimeUnit;

@Component
public class RedisStateUtil {

    @Resource
    private StringRedisTemplate stringRedisTemplate;

    private static final String LOGIN_STATUS_KEY = "user:login:status:";
    private static final String USER_SESSION_KEY = "user:session:";
    private static final long LOGIN_TIMEOUT = 30; // 超时时间（分钟）

    /**
     * 设置用户登录状态，确保单一登录
     *
     * @param userId    用户ID
     * @param sessionId 当前会话的唯一标识
     * @return 是否登录成功（false 表示已在其他地方登录）
     */
    public boolean setLoginStatus(String userId, String sessionId) {
        String loginKey = LOGIN_STATUS_KEY + userId;
        String existingSession = stringRedisTemplate.opsForValue().get(loginKey);

        // 如果用户已登录且会话不同，则拒绝登录
        if (existingSession != null && !existingSession.equals(sessionId)) {
            return false;
        }

        // 设置新的会话并更新超时时间
        stringRedisTemplate.opsForValue().set(loginKey, sessionId, LOGIN_TIMEOUT, TimeUnit.MINUTES);
        return true;
    }

    /**
     * 检查用户是否已登录
     *
     * @param userId    用户ID
     * @param sessionId 当前会话的唯一标识
     * @return 是否已登录（并且是当前会话）
     */
    public boolean isUserLoggedIn(String userId, String sessionId) {
        String loginKey = LOGIN_STATUS_KEY + userId;
        String existingSession = stringRedisTemplate.opsForValue().get(loginKey);
        return sessionId.equals(existingSession);
    }

    /**
     * 登录续期
     *
     * @param userId    用户ID
     * @param sessionId 当前会话的唯一标识
     * @return 是否续期成功（用户已登录并且是当前会话）
     */
    public boolean renewLogin(String userId, String sessionId) {
        String loginKey = LOGIN_STATUS_KEY + userId;
        String existingSession = stringRedisTemplate.opsForValue().get(loginKey);

        // 仅在会话匹配时续期
        if (sessionId.equals(existingSession)) {
            stringRedisTemplate.expire(loginKey, LOGIN_TIMEOUT, TimeUnit.MINUTES);
            return true;
        }
        return false;
    }

    /**
     * 清除用户登录状态
     *
     * @param userId 用户ID
     */
    public void clearLoginStatus(String userId) {
        String loginKey = LOGIN_STATUS_KEY + userId;
        stringRedisTemplate.delete(loginKey);
    }
}
