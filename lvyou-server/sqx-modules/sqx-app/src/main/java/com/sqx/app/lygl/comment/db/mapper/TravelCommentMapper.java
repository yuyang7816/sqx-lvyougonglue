package com.sqx.app.lygl.comment.db.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.sqx.app.lygl.comment.domain.bo.CommentPageBo;
import com.sqx.app.lygl.comment.domain.bo.MomentCommentPageBo;
import com.sqx.app.lygl.comment.domain.vo.CommentVo;
import com.sqx.app.lygl.comment.domain.vo.MomentCommentPageVo;
import com.sqx.app.lygl.comment.entity.TravelComment;
import org.apache.ibatis.annotations.Param;

/**
* @author Administrator
* @description 针对表【travel_comment(用户评论)】的数据库操作Mapper
* @createDate 2024-10-11 13:22:26
* @Entity com.sqx.lvyougonglve.comment.entity.TravelComment
*/
public interface TravelCommentMapper extends BaseMapper<TravelComment> {

    IPage<CommentVo> pageInfo(Page<TravelComment> initPage, @Param("param") CommentPageBo commentPageBo);

    IPage<MomentCommentPageVo> momentCommentPage(Page<TravelComment> initPage, @Param("param") MomentCommentPageBo momentCommentPageBo);
}




