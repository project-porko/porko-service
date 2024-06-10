package io.porko.pagination.response;

import java.util.List;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.domain.Page;

@Getter
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class PageResponse<T> {
    private Integer totalPages;
    private Long totalElementCount;
    private Integer currentPage;
    private Integer currentElementCount;
    private Integer perPageNumber;

    private Boolean firstPage;
    private Boolean lastPage;
    private Boolean hasNextPage;
    private Boolean hasPrevious;

    private List<T> elements;

    private PageResponse(
        Integer totalPages,
        Long totalElementCount,
        Integer currentPage,
        Integer currentElementCount,
        Integer perPageNumber,
        Boolean firstPage,
        Boolean lastPage,
        Boolean hasNextPage,
        Boolean hasPrevious,
        List<T> elements
    ) {
        this.totalPages = totalPages;
        this.totalElementCount = totalElementCount;
        this.currentPage = currentPage;
        this.currentElementCount = currentElementCount;
        this.perPageNumber = perPageNumber;
        this.firstPage = firstPage;
        this.lastPage = lastPage;
        this.hasNextPage = hasNextPage;
        this.hasPrevious = hasPrevious;
        this.elements = elements;
    }

    public static <T> PageResponse<T> mapTo(Page<T> page) {
        return new PageResponse<>(
            page.getTotalPages(),
            page.getTotalElements(),
            getCurrentPage(page),
            page.getNumberOfElements(),
            page.getSize(),
            page.isFirst(),
            page.isLast(),
            page.hasNext(),
            page.hasPrevious(),
            page.getContent()
        );
    }

    private static <T> int getCurrentPage(Page<T> page) {
        return page.getNumber() + 1;
    }
}
