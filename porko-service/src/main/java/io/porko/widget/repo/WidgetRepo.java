package io.porko.widget.repo;

import io.porko.widget.domain.Widget;
import org.springframework.data.jpa.repository.JpaRepository;

public interface WidgetRepo extends JpaRepository<Widget, Long> {
}
