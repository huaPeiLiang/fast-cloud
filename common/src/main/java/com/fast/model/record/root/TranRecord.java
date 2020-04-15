package com.fast.model.record.root;

import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;

@Data
@Entity
@Table(name = "tranRecord")
public class TranRecord implements Serializable {

    private static final long serialVersionUID = 3510465491044226854L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @Column(name = "accountId")
    private Integer accountId;

    @Column(name = "changeAmount")
    private Double changeAmount;

    @Column(name = "changeType")
    private String changeType;
}
