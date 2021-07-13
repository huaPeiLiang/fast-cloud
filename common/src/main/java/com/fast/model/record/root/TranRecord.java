package com.fast.model.record.root;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import java.io.Serializable;

@Data
@TableName(value = "tranRecord")
public class TranRecord implements Serializable {

    private static final long serialVersionUID = 3510465491044226854L;

    @TableId(value = "id",type = IdType.AUTO)
    private Integer id;

    @TableField(value = "accountId",exist = true)
    private Integer accountId;

    @TableField(value = "changeAmount",exist = true)
    private Double changeAmount;

    @TableField(value = "changeType",exist = true)
    private String changeType;

    @TableField(value = "tenant_id",exist = true)
    private Long tenantId;
}
