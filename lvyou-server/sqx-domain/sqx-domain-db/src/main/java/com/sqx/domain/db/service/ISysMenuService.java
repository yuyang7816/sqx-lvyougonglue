package com.sqx.domain.db.service;


import com.baomidou.mybatisplus.extension.service.IService;
import com.sqx.domain.db.entity.SysMenuEntity;

import java.util.List;


/**
 * 菜单管理
 *
 */
public interface ISysMenuService extends IService<SysMenuEntity> {

    List<SysMenuEntity> listMenu();

}
