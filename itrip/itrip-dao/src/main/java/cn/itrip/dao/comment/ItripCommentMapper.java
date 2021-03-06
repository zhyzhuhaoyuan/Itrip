package cn.itrip.dao.comment;

import cn.itrip.beans.pojo.ItripComment;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

public interface ItripCommentMapper {
    public List<ItripComment> getItripComment(@Param(value = "hotelId") Integer hotelId) throws Exception;

    public int getItripCommentNum(Map<String, Object>  hotelId) throws Exception;
}
