package com.example.demos.mapper;

import com.example.demos.entity.Person;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * @author : huangjie121015
 * @date : 2021/12/2 18:16
 */
@Mapper
public interface PersonMapper {
    int insertList(List<Person> persons);
}
