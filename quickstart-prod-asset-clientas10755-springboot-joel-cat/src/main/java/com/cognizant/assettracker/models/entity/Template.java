package com.cognizant.assettracker.models.entity;

import lombok.*;

import jakarta.persistence.*;

import java.time.LocalDateTime;


@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Entity

@Table(name = "TBL_AMEX_TEMPLATE")
public class Template {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long templateId;

    @Column(name = "DOCUMENT")
    private byte[] document;

    @Column(name = "FILENAME")
    private String documentName;

    @Column(name = "CREATE_TIME_STAMP")
    private LocalDateTime createTimestamp;
}
