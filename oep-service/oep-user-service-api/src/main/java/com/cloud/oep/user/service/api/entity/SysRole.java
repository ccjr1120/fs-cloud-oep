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
public class SysRole {

	/**
	 * 角色id
	 */
	private String rid;

	/**
	 * 角色名
	 */
	private String name;

	@TableField(fill = FieldFill.INSERT)
	private String createId;

	private LocalDateTime createDate;

	@TableField(fill = FieldFill.INSERT_UPDATE)
	private String updateId;

	@TableField(fill = FieldFill.INSERT_UPDATE)
	private LocalDateTime updateTime;

	@TableLogic
	private Integer delFlag;


}
