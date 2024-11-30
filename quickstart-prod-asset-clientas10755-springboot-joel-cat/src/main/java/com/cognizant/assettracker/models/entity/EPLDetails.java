package com.cognizant.assettracker.models.entity;


import jakarta.persistence.Entity;
import lombok.*;

import jakarta.persistence.Column;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "TBL_AMEX_EPL_DTL")
public class EPLDetails {

    @Id
    @Column(name = "EPL_ID")
    private String eplID;

    @Column(name = "EPL_NAME")
    private String eplName;

}
