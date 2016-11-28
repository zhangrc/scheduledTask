package com.yinhai.sheduledTask.business.dataTransfer.bean;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

/**
 * 数据传输中，一次传输的内容
 * 包括了所更新的所有表的所有数据
 * Created by zrc on 2016/9/28.
 */
public class TransferDTO implements Serializable {

    /**
     * 店的orgid
     */
    private String orgId;
    /**
     * 传输的id
     */
    private String transferId;

    /**
     * 一次传输的所有数据
     * map 里面的key 有 tableName 表明 tableData 表的数据
     */
    private List<Map> allTableData;

    public String getOrgId() {
        return orgId;
    }

    public TransferDTO setOrgId(String orgId) {
        this.orgId = orgId;
        return this;
    }

    public String getTransferId() {
        return transferId;
    }

    public TransferDTO setTransferId(String transferId) {
        this.transferId = transferId;
        return this;
    }

    /**
     * @return 每个Map包含两个key，分别为 tableName 和tableData
     */
    public List<Map> getAllTableData() {
        return allTableData;
    }

    public TransferDTO setAllTableData(List<Map> allTableData) {
        this.allTableData = allTableData;
        return this;
    }

    /**
     * 把传输过来的数据格式化为StoreTransferDTO
     *
     * @param object mq 传输过来的数据
     */
    public static TransferDTO fromateData(Map object) {
        TransferDTO dto = new TransferDTO();
        dto.setOrgId((String) object.get("orgid"))
                .setTransferId((String) object.get("transferId"))
                .setAllTableData((List<Map>) object.get("allData"));
        return dto;
    }


    @Override
    public String toString() {
        return "TransferDTO{" +
                "orgId='" + orgId + '\'' +
                ", transferId='" + transferId + '\'' +
                ", allTableData=" + allTableData +
                '}';
    }
}
