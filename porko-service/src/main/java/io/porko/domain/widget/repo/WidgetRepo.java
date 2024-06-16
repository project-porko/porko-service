package io.porko.domain.widget.repo;

import io.porko.domain.widget.domain.Widget;
import org.springframework.data.jpa.repository.JpaRepository;

public interface WidgetRepo extends JpaRepository<Widget, Long> {
}
