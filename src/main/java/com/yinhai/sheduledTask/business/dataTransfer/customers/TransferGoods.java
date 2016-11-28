package com.yinhai.sheduledTask.business.dataTransfer.customers;


import com.yinhai.sheduledTask.business.dataTransfer.ext.TransferCustomerAdapter;

/**
 * 转库入库同步
 * 
 * @author zhaolin
 * 
 */
public class TransferGoods extends TransferCustomerAdapter {

	@Override
	public String getTableName() {
		return "w_transfer_goods";
	}

	@Override
	public String getStoreSelectSql() {
		return "select tg_id,tg_no,tg_givenno,tg_from,tg_to,tg_money,tg_amount,tg_remark,tg_check1,tg_checktime1,tg_check2,tg_checktime2,tg_status,status,aae036,aae011,orgid,porgid,tg_type,tg_reject,last_update,t_version " +
				" from w_transfer_goods where 1=1 and STATUS=1 and TG_TYPE=1 and (TG_STATUS=1 or TG_STATUS=3) " +
				"and last_update > ( select transfer_date from base_transfer where transfer_status = 1  ORDER BY transfer_date desc limit 1 )  and  (t_version = 1 or t_version = 3 ) "+
				"union all " +
				"select * from w_transfer_goods where 1=1 and STATUS=1 and TG_TYPE=2 and TG_STATUS=0 "+
				"and last_update > ( select transfer_date from base_transfer where transfer_status = 1  ORDER BY transfer_date desc limit 1 )  and  (t_version = 1 or t_version = 3 )";
	}

	@Override
	public String getCenterSelectSql() {
		return "select tg_id,tg_no,tg_givenno,tg_from,tg_to,tg_money,tg_amount,tg_remark,tg_check1,tg_checktime1,tg_check2,tg_checktime2,tg_status,status,aae036,aae011,orgid,porgid,tg_type,tg_reject,last_update,t_version " +
				" from w_transfer_goods where 1=1 and STATUS=1 and TG_TYPE=1 and (TG_STATUS=1 or TG_STATUS=3) " +
				"and last_update > ( select transfer_date from base_transfer where transfer_status = 1  ORDER BY transfer_date desc limit 1 ) "+
				"union all " +
				"select * from w_transfer_goods where 1=1 and STATUS=1 and TG_TYPE=2 and TG_STATUS=0 "+
				"and last_update > ( select transfer_date from base_transfer where transfer_status = 1  ORDER BY transfer_date desc limit 1 ) ";
	}
}
