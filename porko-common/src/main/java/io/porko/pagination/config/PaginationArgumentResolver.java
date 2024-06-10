package io.porko.pagination.config;

import io.porko.pagination.request.Pagination;
import lombok.RequiredArgsConstructor;
import org.springframework.core.MethodParameter;
import org.springframework.core.annotation.MergedAnnotation;
import org.springframework.core.annotation.MergedAnnotations;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.data.web.SortArgumentResolver;
import org.springframework.data.web.SortHandlerMethodArgumentResolver;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.ModelAndViewContainer;

@Component
@RequiredArgsConstructor
public class PaginationArgumentResolver extends PageableHandlerMethodArgumentResolver {
    public static final String PAGE = "page";
    public static final String SIZE = "size";
    public static final int DEFAULT_PAGE_NUMBER = 0;
    public static final int DEFAULT_PAGE_SIZE = 10;
    public static final PageRequest fallBackPageRequest = PageRequest.of(DEFAULT_PAGE_NUMBER, DEFAULT_PAGE_SIZE);

    private final SortArgumentResolver sortResolver = new SortHandlerMethodArgumentResolver();

    @Override
    public Pageable resolveArgument(
        MethodParameter methodParameter,
        ModelAndViewContainer mavContainer,
        NativeWebRequest webRequest,
        WebDataBinderFactory binderFactory
    ) {
        String page = webRequest.getParameter(getParameterNameToUse(getPageParameterName(), methodParameter));
        String pageSize = webRequest.getParameter(getParameterNameToUse(getSizeParameterName(), methodParameter));
        Sort sort = sortResolver.resolveArgument(methodParameter, mavContainer, webRequest, binderFactory);

        Pageable pageable = getPageable(methodParameter, page, pageSize);

        if (methodParameter.hasParameterAnnotation(Pagination.class)) {
            return resolvePaginationArgument(pageable, page, pageSize, sort, methodParameter);
        }
        return super.resolveArgument(methodParameter, mavContainer, webRequest, binderFactory);
    }

    private Pageable resolvePaginationArgument(Pageable pageable, String page, String pageSize, Sort sort, MethodParameter methodParameter) {
        MergedAnnotation<Pagination> pagination = MergedAnnotations.from(methodParameter.getParameterAnnotations()).get(Pagination.class);
        if (hasNoPageRequestParameterValues(page, pageSize)) {
            return getDefaultPageRequest(pagination);
        }

        if (sort.isSorted()) {
            return createPageRequestWithSort(pageable, sort);
        }
        return createPageRequestWithoutSort(pageable);
    }

    private Pageable createPageRequestWithoutSort(Pageable pageable) {
        return PageRequest.of(
            adjustPageNumber(pageable.getPageNumber()),
            pageable.getPageSize()
        );
    }

    private boolean hasNoPageRequestParameterValues(String page, String pageSize) {
        if (page == null && pageSize == null) {
            return true;
        }
        // TODO pageSize파라미터 요청값은 있고, page 파라미터 요청값은 없는 경우 -> page는 기본 값 0 설정, pageSize는 요청값 설정 (PageReqeust.of(0, pageSize))
        // TODO page 파라미터 요청값은 있고, pageSize 파라미터 요청 값은 없는 경우 -> page는 요청값 설정, pageSize는 기본값 10 설정 -> PageReqeust.of(page, 10)
        return false;
    }

    private Pageable getDefaultPageRequest(MergedAnnotation<Pagination> pagination) {
        if (pagination.isPresent()) {
            return pageRequestFromAnnotationDefaultValues(pagination);
        }
        return fallBackPageRequest;
    }

    private static PageRequest pageRequestFromAnnotationDefaultValues(MergedAnnotation<Pagination> pagination) {
        int page = pagination.getInt(PAGE);
        int size = pagination.getInt(SIZE);

        return PageRequest.of(page, size);
    }

    private Pageable createPageRequestWithSort(Pageable pageable, Sort sort) {
        return PageRequest.of(
            adjustPageNumber(pageable.getPageNumber()),
            pageable.getPageSize(),
            sort
        );
    }

    private static int adjustPageNumber(int pageNumber) {
        if (isNotZeroPageNumber(pageNumber)) {
            return pageNumber - 1;
        }
        return pageNumber;
    }

    private static boolean isNotZeroPageNumber(int pageNumber) {
        return pageNumber != 0;
    }
}
