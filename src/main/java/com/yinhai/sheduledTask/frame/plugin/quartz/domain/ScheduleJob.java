package com.yinhai.sheduledTask.frame.plugin.quartz.domain;

import java.util.Date;

public class ScheduleJob {
	
	public static final String STATUS_RUNNING = "1";
	public static final String STATUS_NOT_RUNNING = "0";
	public static final String CONCURRENT_IS = "1";
	public static final String CONCURRENT_NOT = "0";
	
	
	private String job_id;

	private Date create_time;

	private Date update_time;
	/**
	 * 任务名称
	 */
	private String job_name;
	
	/**
	 * 任务分组
	 */
	private String job_group;
	/**
	 * 任务状态 是否启动任务
	 */
	private String status;
	/**
	 * cron表达式
	 */
	private String cron_expression;
	/**
	 * 描述
	 */
	private String description;
	/**
	 * 任务执行时调用哪个类的方法 包名+类名
	 */
	private String beanClass;
	/**
	 * 任务是否有状态
	 */
	private String isConcurrent;
	/**
	 * spring bean
	 */
	private String spring_id;
	/**
	 * 任务调用的方法名
	 */
	private String method_name;
	/**
	 * 父组织id(区别公司)
	 */
	private int porgid;
	
	public String getJob_id() {
		return job_id;
	}
	public void setJob_id(String job_id) {
		this.job_id = job_id;
	}
	public Date getCreate_time() {
		return create_time;
	}
	public void setCreate_time(Date create_time) {
		this.create_time = create_time;
	}
	public Date getUpdate_time() {
		return update_time;
	}
	public void setUpdate_time(Date update_time) {
		this.update_time = update_time;
	}
	public String getJob_name() {
		return job_name;
	}
	public void setJob_name(String job_name) {
		this.job_name = job_name;
	}
	public String getJob_group() {
		return job_group;
	}
	public void setJob_group(String job_group) {
		this.job_group = job_group;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getCron_expression() {
		return cron_expression;
	}
	public void setCron_expression(String cron_expression) {
		this.cron_expression = cron_expression;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public String getBeanClass() {
		return beanClass;
	}
	public void setBeanClass(String beanClass) {
		this.beanClass = beanClass;
	}
	public String getIsConcurrent() {
		return isConcurrent;
	}
	public void setIsConcurrent(String isConcurrent) {
		this.isConcurrent = isConcurrent;
	}
	public String getSpring_id() {
		return spring_id;
	}
	public void setSpring_id(String spring_id) {
		this.spring_id = spring_id;
	}
	public String getMethod_name() {
		return method_name;
	}
	public void setMethod_name(String method_name) {
		this.method_name = method_name;
	}
	public int getPorgid() {
		return porgid;
	}
	public void setPorgid(int porgid) {
		this.porgid = porgid;
	}
}
