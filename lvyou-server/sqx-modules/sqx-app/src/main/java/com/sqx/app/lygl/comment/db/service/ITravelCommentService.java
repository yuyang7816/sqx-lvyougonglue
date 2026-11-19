package com.sqx.app.lygl.comment.db.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.sqx.app.lygl.comment.domain.bo.MomentCommentPageBo;
import com.sqx.app.lygl.comment.domain.vo.MomentCommentPageVo;
import com.sqx.app.lygl.comment.entity.TravelComment;

/**
* @author Administrator
* @description 针对表【travel_comment(用户评论)】的数据库操作Service
* @createDate 2024-10-11 13:22:26
*/
public interface ITravelCommentService extends IService<TravelComment> {
    IPage<MomentCommentPageVo> momentCommentPage(Page<TravelComment> initPage, MomentCommentPageBo momentCommentPageBo);
}
