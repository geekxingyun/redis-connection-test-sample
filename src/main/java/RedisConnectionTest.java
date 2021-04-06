import io.lettuce.core.RedisClient;
import io.lettuce.core.api.StatefulRedisConnection;
import io.lettuce.core.api.sync.RedisCommands;
import lombok.extern.slf4j.Slf4j;

/**
 * @author qing-feng.zhao
 */
@Slf4j
public class RedisConnectionTest {
    private static final String REDIS_HOST="192.168.159.9";
    private static final String REDIS_PASSWORD="123456";
    private static final Integer REDIS_PORT=6379;

    public static void main(String[] args) {
       redisConnectionTest(REDIS_HOST,REDIS_PASSWORD,REDIS_PORT,true);
//       redisConnectionTest(REDIS_HOST,REDIS_PASSWORD,REDIS_PORT,false);
    }

    private static void redisConnectionTest(String host,String password,Integer port,Boolean usePassword){
        RedisClient redisClient;
        StringBuilder stringBuilder=new StringBuilder("redis://");
        if(usePassword){
            stringBuilder.append(password);
            stringBuilder.append("@");
        }
        stringBuilder.append(host);
        stringBuilder.append(":");
        stringBuilder.append(port);
        stringBuilder.append("/");
        stringBuilder.append(0);
        // redis://123456@192.168.159.9:6379/0
        log.info("redis链接URL:{}",stringBuilder.toString());
        redisClient = RedisClient.create(stringBuilder.toString());
        StatefulRedisConnection<String, String> connection = redisClient.connect();
        RedisCommands<String, String> syncCommands = connection.sync();

        syncCommands.set("key", "Hello, Redis!");

        String message=syncCommands.get("key");

        log.info("get message={}",message);

        connection.close();

        redisClient.shutdown();
    }
}
