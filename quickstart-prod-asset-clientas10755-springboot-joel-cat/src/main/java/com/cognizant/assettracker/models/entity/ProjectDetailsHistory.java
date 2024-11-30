package com.cognizant.assettracker.models.entity;


import jakarta.persistence.Column;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.*;
import org.hibernate.proxy.HibernateProxy;

import java.time.LocalDateTime;
import java.util.Objects;

@Entity
@Getter
@Setter
@Builder
@ToString
@RequiredArgsConstructor
@AllArgsConstructor
@Table(name = "TBL_AMEX_PROJECT_DETAILS_HISTORY")
public class ProjectDetailsHistory {

    @EmbeddedId
    ProjectDetailsHistoryUpdateDetails projectDetailsHistoryUpdateDetails;

    @Column(name = "PROJECT_NAME")
    private String projectName;

    @Column(name = "PROJECT_MANAGER_EMP_ID")
    private Long projectManagerEmpId;

    @Column(name = "PROJECT_MANAGER_NAME")
    private String projectManagerName;

    @Column(name = "COGNIZANT_PROJECT_START_DATE")
    private String projectStartDate;

    @Column(name = "COGNIZANT_PROJECT_END_DATE")
    private String projectEndDate;

    @Column(name = "COGNIZANT_EPL_NAME ")
    private String ctsEPLName;

    @Column(name = "COGNIZANT_EPL_ID")
    private String ctsEPLId;

    @Column(name = "CREATE_TIME_STAMP")
    private LocalDateTime createTimestamp;

    @Column(name = "IS_DELETED")
    private boolean isDeleted;

    @Override
    public final boolean equals(Object o) {
        if (this == o) return true;
        if (o == null) return false;
        Class<?> oEffectiveClass = o instanceof HibernateProxy ? ((HibernateProxy) o).getHibernateLazyInitializer().getPersistentClass() : o.getClass();
        Class<?> thisEffectiveClass = this instanceof HibernateProxy ? ((HibernateProxy) this).getHibernateLazyInitializer().getPersistentClass() : this.getClass();
        if (thisEffectiveClass != oEffectiveClass) return false;
        ProjectDetailsHistory that = (ProjectDetailsHistory) o;
        return getProjectDetailsHistoryUpdateDetails() != null && Objects.equals(getProjectDetailsHistoryUpdateDetails(), that.getProjectDetailsHistoryUpdateDetails());
    }

    @Override
    public final int hashCode() {
        return Objects.hash(projectDetailsHistoryUpdateDetails);
    }
}
