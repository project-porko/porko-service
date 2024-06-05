package io.porko.widget.domain;

import io.porko.config.jpa.auditor.MetaFields;
import io.porko.member.domain.Member;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.ForeignKey;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Entity
@Table(
    uniqueConstraints = @UniqueConstraint(
        name = "UK_MEMBER_WIDGET",
        columnNames = {"memberId", "widgetId"}
    )
)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class MemberWidget extends MetaFields {
    private static final int OPTIONAL_SEQUENCE = -1;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(
        name = "member_id",
        nullable = false,
        foreignKey = @ForeignKey(name = "FK_WIDGET_TO_MEMBER")
    )
    private Member member;

    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(
        name = "widget_id",
        nullable = false,
        foreignKey = @ForeignKey(name = "FK_MEMBER_WIDGET_TO_WIDGET")
    )
    private Widget widget;

    @Column(nullable = false)
    private int sequence;

    public MemberWidget(Member member, Widget widget, int sequence) {
        this.member = member;
        this.widget = widget;
        this.sequence = sequence;
    }

    public static MemberWidget of(Member member, Widget widget, int sequence) {
        return new MemberWidget(member, widget, sequence);
    }

    public static MemberWidget optionalOf(Member member, Widget widget) {
        return new MemberWidget(member, widget, OPTIONAL_SEQUENCE);
    }

    public WidgetCode getWidgetCode() {
        return widget.getWidgetCode();
    }

    public String getWidgetDescription() {
        return widget.getDescription();
    }

    public boolean isSequenced() {
        return !isUnsequenced();
    }

    public boolean isUnsequenced() {
        return sequence == OPTIONAL_SEQUENCE;
    }
}
