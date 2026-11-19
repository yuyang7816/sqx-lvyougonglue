package com.sqx.app.lygl.destination.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.sqx.app.lygl.destination.db.service.IDestinationService;
import com.sqx.app.lygl.destination.domain.bo.*;
import com.sqx.app.lygl.destination.domain.entity.Destination;
import com.sqx.app.lygl.destination.domain.vo.DestinationVo;
import com.sqx.app.lygl.destination.service.DestinationService;
import com.sqx.common.core.exception.BizException;
import com.sqx.common.mybatis.utils.PageUtil;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * 目的地配置 服务实现类
 *
 * @author 西安省钱兄网络科技有限公司
 * @since 2025-08-13 14:18:56
 */
@Service
public class DestinationServiceImpl implements DestinationService {

    @Resource
    private IDestinationService iDestinationService;

    @Override
    public void saveInfo(DestinationSaveBo destinationSaveBo) {
        Destination destination = new Destination();
        BeanUtils.copyProperties(destinationSaveBo, destination);

        iDestinationService.save(destination);
    }

    @Override
    public IPage<DestinationVo> pageInfo(DestinationPageBo destinationPageBo) {
        Page<Destination> initPage = PageUtil.initPage(destinationPageBo.getPage(), destinationPageBo.getLimit());

        QueryWrapper<Destination> queryWrapper = new QueryWrapper<>();
        queryWrapper.lambda()
                .eq(StringUtils.isNotBlank(destinationPageBo.getCity()), Destination::getCity, destinationPageBo.getCity())
                .eq(Objects.nonNull(destinationPageBo.getPopular()), Destination::getPopular, destinationPageBo.getPopular())
                .eq(Objects.nonNull(destinationPageBo.getChoiceness()), Destination::getChoiceness, destinationPageBo.getChoiceness())
                .eq(Objects.nonNull(destinationPageBo.getOverseas()), Destination::getOverseas, destinationPageBo.getOverseas())
                .orderByDesc(Destination::getSort, Destination::getCreateTime);

        IPage<Destination> iPage = iDestinationService.page(initPage, queryWrapper);
        List<DestinationVo> voList = new ArrayList<>();
        if (CollectionUtils.isNotEmpty(iPage.getRecords())) {
            for (Destination record : iPage.getRecords()) {
                DestinationVo vo = new DestinationVo();
                BeanUtils.copyProperties(record, vo);

                voList.add(vo);
            }
        }

        IPage<DestinationVo> voIPage = PageUtil.copyPage(iPage, voList);
        return voIPage;
    }

    @Override
    public DestinationVo info(DestinationInfoBo destinationInfoBo) {
        Destination destination = iDestinationService.getById(destinationInfoBo.getId());
        if (Objects.isNull(destination)) {
            throw new BizException("信息不存在");
        }

        DestinationVo vo = new DestinationVo();
        BeanUtils.copyProperties(destination, vo);
        return vo;
    }

    @Override
    public void updateInfo(DestinationUpdateBo destinationUpdateBo) {
        Destination destination = new Destination();
        BeanUtils.copyProperties(destinationUpdateBo, destination);

        iDestinationService.updateById(destination);
    }

    @Override
    public void delete(DestinationInfoBo destinationInfoBo) {
        iDestinationService.removeById(destinationInfoBo.getId());
    }

    @Override
    public void addAlbum(AddAlbumBo addAlbumBo) {
        Destination destination = iDestinationService.getById(addAlbumBo.getId());
        if (Objects.isNull(destination)) {
            throw new BizException("目的地信息不存在");
        }

        String imageStr = String.join(",", addAlbumBo.getImageList());

        if (StringUtils.isBlank(destination.getAlbum())) {
            // 首次添加
            destination.setAlbum(imageStr);
        } else {
            destination.setAlbum(destination.getAlbum() + "," + imageStr);
        }

        iDestinationService.updateById(destination);
    }
}
