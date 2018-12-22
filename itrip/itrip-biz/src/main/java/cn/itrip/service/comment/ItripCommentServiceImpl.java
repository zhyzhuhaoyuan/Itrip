package cn.itrip.service.comment;

import cn.itrip.beans.pojo.ItripComment;
import cn.itrip.dao.comment.ItripCommentMapper;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

@Service
public class ItripCommentServiceImpl implements ItripCommentService {

    @Resource
    public ItripCommentMapper itripCommentMapper;

    @Override
    public List<ItripComment> getItripComment(Integer hotelId) throws Exception {
        return itripCommentMapper.getItripComment(hotelId);
    }

    @Override
    public int getItripCommentNum(Map<String,Object> hotelId) throws Exception {
        return itripCommentMapper.getItripCommentNum(hotelId);
    }

}
