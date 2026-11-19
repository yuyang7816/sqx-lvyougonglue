package com.sqx.app.lygl.destination.domain.bo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.util.List;

/**
 * @author chengming
 * @since 2025/8/12
 */
@Data
public class AddAlbumBo {
    private Long id;

    @Schema(description = "图片列表")
    private List<String> imageList;
}
