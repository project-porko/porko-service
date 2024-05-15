package io.porko.member.facade;

import static io.porko.utils.ConvertUtils.convertToMap;

import io.porko.member.controller.model.ValidateDuplicateRequest;
import io.porko.member.controller.model.ValidateDuplicateResponse;
import io.porko.member.exception.MemberErrorCode;
import io.porko.member.exception.MemberException;
import io.porko.member.facade.dto.ValidateDuplicateRequestField;
import io.porko.member.facade.strategy.ValidateDuplicateStrategy;
import java.util.List;
import java.util.Map;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ValidateDuplicateFacade {
    private final List<ValidateDuplicateStrategy> validateDuplicateStrategies;

    public ValidateDuplicateResponse isDuplicated(ValidateDuplicateRequest validateDuplicateRequest) {
        ValidateDuplicateRequestField requestField = extractValidateDuplicateRequestField(validateDuplicateRequest);

        return validateDuplicateStrategies.stream()
            .filter(it -> it.isSupport(requestField.requestType()))
            .findAny()
            .orElseThrow(() -> new MemberException(MemberErrorCode.UNSUPPORTED_VALIDATE_DUPLICATE_TARGET, validateDuplicateRequest))
            .isDuplicated(requestField)
            ;
    }

    private ValidateDuplicateRequestField extractValidateDuplicateRequestField(ValidateDuplicateRequest validateDuplicateRequest) {
        Map<String, Object> validateDuplicateRequestMap = convertToMap(validateDuplicateRequest);
        return extractRequestEntry(validateDuplicateRequestMap);
    }

    private ValidateDuplicateRequestField extractRequestEntry(Map<String, Object> rquestMap) {
        return rquestMap.entrySet().stream()
            .filter(entry -> entry.getValue() != null)
            .map(ValidateDuplicateRequestField::of)
            .findAny()
            .orElseThrow(() -> new MemberException(MemberErrorCode.INVALID_VALIDATE_DUPLICATE_TARGET_FORMAT, rquestMap))
            ;
    }
}
