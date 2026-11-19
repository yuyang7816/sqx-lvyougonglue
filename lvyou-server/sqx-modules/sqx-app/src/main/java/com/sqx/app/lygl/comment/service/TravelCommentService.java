package com.sqx.app.lygl.comment.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.sqx.app.lygl.comment.domain.bo.*;
import com.sqx.app.lygl.comment.domain.vo.MomentCommentPageVo;
import com.sqx.common.core.domain.Result;


/**
* @author Administrator
* @description 针对表【travel_comment(用户评论)】的数据库操作Service
* @createDate 2024-10-11 13:22:26
*/
public interface TravelCommentService {

    Result<?> saveInfo(CommentSaveBo commentSaveBo);

    Result<?> pageInfo(CommentPageBo commentPageBo);

    Result<?> info(CommentInfoBo commentInfoBo);

    Result<?> updateInfo(CommentUpdateBo commentUpdateBo);

    Result<?> delete(CommentInfoBo commentInfoBo);

    IPage<MomentCommentPageVo> momentCommentPage(MomentCommentPageBo momentCommentPageBo);
}
