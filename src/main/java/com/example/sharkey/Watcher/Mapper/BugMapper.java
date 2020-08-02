package com.example.sharkey.Watcher.Mapper;

import com.example.sharkey.Entity.Bug;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface BugMapper {
    public boolean addBug(Bug bug);

    public List<Bug> getAllBugList();
}
