package com.example.sharkey.Test;

import com.example.sharkey.Entity.Memo;
import com.example.sharkey.Utils.MyLogger;
import com.example.sharkey.Watcher.Mapper.UserMapper;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Date;
import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class MemoTest {
    @Autowired
    private UserMapper userMapper;

    @Test
    public void test(){
        List<Memo> list = userMapper.getMemoToday();
        for(int i = 0;i < list.size();i ++){
            MyLogger.logger(list.get(i).toString());
        }
    }
}
