package com.sqx.app.lygl.comment.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.sqx.app.lygl.comment.domain.bo.CommentInfoBo;
import com.sqx.app.lygl.comment.domain.bo.CommentPageBo;
import com.sqx.app.lygl.comment.domain.bo.CommentSaveBo;
import com.sqx.app.lygl.comment.domain.bo.CommentUpdateBo;
import com.sqx.app.lygl.comment.service.TravelCommentService;
import com.sqx.app.lygl.comment.domain.bo.MomentCommentPageBo;
import com.sqx.app.lygl.comment.domain.vo.MomentCommentPageVo;
import com.sqx.common.core.domain.Result;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * @author chengming
 * @since 2024/10/11
 */
@Tag(name = "管理端-评论")
@RestController
@RequestMapping("admin/comment")
public class AdminCommentController {

    @Resource
    private TravelCommentService commentService;

    /**
     * 保存
     */
    @Operation(summary = "保存")
    @PostMapping("save")
    public Result<?> save(@RequestBody CommentSaveBo commentSaveBo) {
        return commentService.saveInfo(commentSaveBo);
    }

    /**
     * 列表
     */
    @Operation(summary = "列表")
    @PostMapping("page")
    public Result<?> page(@RequestBody CommentPageBo commentPageBo) {
        return commentService.pageInfo(commentPageBo);
    }

    /**
     * 信息
     */
    @Operation(summary = "信息")
    @PostMapping("info")
    public Result<?> info(@RequestBody @Validated CommentInfoBo commentInfoBo) {
        return commentService.info(commentInfoBo);
    }

    /**
     * 更新
     */
    @Operation(summary = "更新")
    @PostMapping("update")
    public Result<?> update(@RequestBody @Validated CommentUpdateBo commentUpdateBo) {
        return commentService.updateInfo(commentUpdateBo);
    }

    /**
     * 删除
     */
    @Operation(summary = "删除")
    @PostMapping("delete")
    public Result<?> delete(@RequestBody @Validated CommentInfoBo commentInfoBo) {
        return commentService.delete(commentInfoBo);
    }

    @Operation(summary = "动态评论列表")
    @PostMapping("momentCommentPage")
    public Result<IPage<MomentCommentPageVo>> momentCommentPage(@RequestBody @Validated MomentCommentPageBo momentCommentPageBo) {
        IPage<MomentCommentPageVo> voIPage = commentService.momentCommentPage(momentCommentPageBo);
        return Result.success(voIPage);
    }
}
