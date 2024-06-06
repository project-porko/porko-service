package io.porko.widget.domain;

import static io.porko.widget.domain.Sequence.unorderedSequence;

import io.porko.config.jpa.auditor.MetaFields;
import io.porko.member.domain.Member;
import jakarta.persistence.AttributeOverride;
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
    @AttributeOverride(name = "value", column = @Column(name = "sequence"))
    private Sequence sequence;

    public MemberWidget(Member member, Widget widget, Sequence sequence) {
        this.member = member;
        this.widget = widget;
        this.sequence = sequence;
    }

    public static MemberWidget of(Member member, Widget widget, int sequence) {
        return new MemberWidget(member, widget, Sequence.orderedFrom(sequence));
    }

    public static MemberWidget unorderedOf(Member member, Widget widget) {
        return new MemberWidget(member, widget, unorderedSequence());
    }

    public WidgetCode getWidgetCode() {
        return widget.getWidgetCode();
    }

    public int getSequence() {
        return sequence.getValue();
    }

    public String getWidgetDescription() {
        return widget.getDescription();
    }

    public boolean isSequenced() {
        return !isUnsequenced();
    }

    public boolean isUnsequenced() {
        return sequence.isUnsequenced();
    }
}
