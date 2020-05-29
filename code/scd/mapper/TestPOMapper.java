package scd.mapper;

import scd.model.TestPO;

public interface TestPOMapper {
    int deleteByPrimaryKey(Long id);

    int insert(TestPO record);

    int insertSelective(TestPO record);

    TestPO selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(TestPO record);

    int updateByPrimaryKey(TestPO record);
}