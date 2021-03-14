package com.cloud.oep.user.service.api;

import com.cloud.oep.user.service.api.entity.SysUser;

/**
 * @author ccjr
 */
public interface SysUserService {

    public SysUser getLoginUserDetailByUsername();

}
