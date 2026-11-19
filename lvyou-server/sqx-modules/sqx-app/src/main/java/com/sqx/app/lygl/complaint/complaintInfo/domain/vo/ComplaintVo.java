package com.sqx.app.lygl.complaint.complaintInfo.domain.vo;

import com.sqx.app.lygl.complaint.complaintInfo.entity.TravelComplaint;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.List;

/**
 * @author chengming
 * @since 2024/10/14
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class ComplaintVo extends TravelComplaint {

    /**
     * 图片列表
     */
    @Schema(description = "图片列表")
    private List<String> imgList;

    @Schema(description = "用户头像")
    private String avatar;

    @Schema(description = "被投诉的用户名/店铺名")
    private String name;

    @Schema(description = "用户名")
    private String userName;
}
