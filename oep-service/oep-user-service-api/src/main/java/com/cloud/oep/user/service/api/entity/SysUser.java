package com.cloud.oep.user.service.api.entity;

import lombok.Data;
import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableLogic;
import java.time.LocalDateTime;

/**
* @author ccjr
*/
@Data
public class SysUser {

	/**
	 * uuid
	 */
	private String uid;

	/**
	 * 登录账号，学生、老师默认为学工号，其它账户可自定义
	 */
	private String account;

	/**
	 * 用户姓名
	 */
	private String name;

	/**
	 * 密码
	 */
	private String password;

	/**
	 * 角色id
	 */
	private String roleId;

	@TableField(fill = FieldFill.INSERT)
	private String createId;

	@TableField(fill = FieldFill.INSERT)
	private LocalDateTime createTime;

	@TableField(fill = FieldFill.INSERT_UPDATE)
	private String updateId;

	@TableField(fill = FieldFill.INSERT_UPDATE)
	private LocalDateTime updateTime;

	@TableLogic
	private Integer delFlag;


}
