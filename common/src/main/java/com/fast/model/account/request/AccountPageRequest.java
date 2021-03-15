package com.fast.model.account.request;

import lombok.Data;
import java.io.Serializable;

@Data
public class AccountPageRequest implements Serializable {

    private static final long serialVersionUID = 5935996018098628799L;

    private String name;

}
