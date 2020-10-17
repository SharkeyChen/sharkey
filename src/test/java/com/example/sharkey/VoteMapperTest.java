package com.example.sharkey;

import com.example.sharkey.Entity.VoteConfig;
import com.example.sharkey.Utils.MyLogger;
import com.example.sharkey.Watcher.Mapper.VoteMapper;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest
public class VoteMapperTest {
    @Autowired
    VoteMapper voteMapper;

    @Test
    public void test(){
        MyLogger.logger("yes");
        List<VoteConfig> list = voteMapper.getVerifyVote();
        MyLogger.logger(list.size() + "");
    }
}
