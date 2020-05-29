package scd.mapper;

import scd.model.TaskParamPO;

public interface TaskParamPOMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(TaskParamPO record);

    int insertSelective(TaskParamPO record);

    TaskParamPO selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(TaskParamPO record);

    int updateByPrimaryKey(TaskParamPO record);
}