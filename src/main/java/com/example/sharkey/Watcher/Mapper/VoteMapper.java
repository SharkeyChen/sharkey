package com.example.sharkey.Watcher.Mapper;

import com.example.sharkey.Entity.VoteConfig;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface VoteMapper {
    List<VoteConfig> getVerifyVote();
}
