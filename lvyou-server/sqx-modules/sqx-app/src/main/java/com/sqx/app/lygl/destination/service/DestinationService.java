package com.sqx.app.lygl.destination.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.sqx.app.lygl.destination.domain.bo.*;
import com.sqx.app.lygl.destination.domain.vo.DestinationVo;

/**
 * 目的地配置 服务类
 *
 * @author 西安省钱兄网络科技有限公司
 * @since 2025-08-13 14:18:56
 */
public interface DestinationService {

    void saveInfo(DestinationSaveBo destinationSaveBo);

    IPage<DestinationVo> pageInfo(DestinationPageBo destinationPageBo);

    DestinationVo info(DestinationInfoBo destinationInfoBo);

    void updateInfo(DestinationUpdateBo destinationUpdateBo);

    void delete(DestinationInfoBo destinationInfoBo);

    void addAlbum(AddAlbumBo addAlbumBo);
}
