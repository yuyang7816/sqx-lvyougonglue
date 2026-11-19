package com.sqx.app.lygl.comment.db.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.sqx.app.lygl.comment.db.mapper.TravelCommentMapper;
import com.sqx.app.lygl.comment.db.service.ITravelCommentService;
import com.sqx.app.lygl.comment.domain.bo.MomentCommentPageBo;
import com.sqx.app.lygl.comment.domain.vo.MomentCommentPageVo;
import com.sqx.app.lygl.comment.entity.TravelComment;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
* @author Administrator
* @description 针对表【travel_comment(用户评论)】的数据库操作Service实现
* @createDate 2024-10-11 13:22:26
*/
@Service
public class ITravelCommentServiceImpl extends ServiceImpl<TravelCommentMapper, TravelComment>
    implements ITravelCommentService {

    @Resource
    private TravelCommentMapper travelCommentMapper;

    @Override
    public IPage<MomentCommentPageVo> momentCommentPage(Page<TravelComment> initPage, MomentCommentPageBo momentCommentPageBo) {
        return travelCommentMapper.momentCommentPage(initPage, momentCommentPageBo);
    }
}




