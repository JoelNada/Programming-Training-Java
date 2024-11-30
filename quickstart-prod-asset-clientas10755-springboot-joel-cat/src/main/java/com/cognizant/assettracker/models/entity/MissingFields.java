package com.cognizant.assettracker.models.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


import java.util.List;
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "TBL_AMEX_EXCEL_MISSING_FIELDS")
public class MissingFields {
    @Id
    @Column(name = "EXCEL_NAME")
    @JsonIgnore
    String excelName;

    @Column
    Long id;

    @Column
    @ElementCollection
    List<String> fields;

    @JsonIgnore
    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "NOTIFICATION_ID")
    private Notifications notifications;

    @Override
    public String toString() {
        return
                "id= "+ id +", fields missing= " + fields;
    }
}

