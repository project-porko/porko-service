package io.porko.widget.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Entity
@Table(uniqueConstraints = @UniqueConstraint(
    name = "UK_WIDGET_CODE", columnNames = "widgetCode")
)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Widget {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private WidgetCode widgetCode;

    private Widget(WidgetCode widgetCode) {
        this.widgetCode = widgetCode;
    }

    public Widget(Long id, WidgetCode widgetCode) {
        this.id = id;
        this.widgetCode = widgetCode;
    }

    public static Widget from(WidgetCode widgetCode) {
        return new Widget(widgetCode);
    }

    public static Widget of(Long id, WidgetCode widgetCode) {
        return new Widget(id, widgetCode);
    }
}
