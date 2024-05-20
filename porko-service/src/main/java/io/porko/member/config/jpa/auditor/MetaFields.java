package io.porko.member.config.jpa.auditor;

import jakarta.persistence.Column;
import jakarta.persistence.MappedSuperclass;
import lombok.Getter;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.LastModifiedBy;

@Getter
@MappedSuperclass
public abstract class MetaFields extends TimeMetaFields {
    @Column(updatable = false)
    @CreatedBy
    private Long createdBy;

    @LastModifiedBy
    private Long updatedBy;
}
