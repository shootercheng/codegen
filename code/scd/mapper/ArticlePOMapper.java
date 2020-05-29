package scd.mapper;

import scd.model.ArticlePO;

public interface ArticlePOMapper {
    int deleteByPrimaryKey(Long id);

    int insert(ArticlePO record);

    int insertSelective(ArticlePO record);

    ArticlePO selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(ArticlePO record);

    int updateByPrimaryKey(ArticlePO record);
}