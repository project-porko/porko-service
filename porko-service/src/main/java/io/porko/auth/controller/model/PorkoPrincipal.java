package io.porko.auth.controller.model;

import java.util.Collections;
import lombok.Getter;
import org.springframework.security.core.userdetails.User;

@Getter
public class PorkoPrincipal extends User {
    private Long id;

    private PorkoPrincipal(Long id, String memberId) {
        super(memberId, "", Collections.emptySet());
        this.id = id;
    }

    public static PorkoPrincipal of(Long id, String memberId) {
        return new PorkoPrincipal(id, memberId);
    }
}
