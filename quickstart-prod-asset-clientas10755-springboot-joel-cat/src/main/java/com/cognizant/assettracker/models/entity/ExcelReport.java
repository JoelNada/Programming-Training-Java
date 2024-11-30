package com.cognizant.assettracker.models.entity;

import lombok.*;

import jakarta.persistence.*;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Getter
@Setter
@Builder
@Table(name = "TBL_AMEX_EXCEL_REPORT")
public class ExcelReport {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long excelId;

    @Column(name = "DOCUMENT")
    private byte[] document;

    @Column(name = "FILENAME")
    private String documentName;

    @Column(name = "CREATE_TIME_STAMP")
    private LocalDateTime createTimestamp;
}