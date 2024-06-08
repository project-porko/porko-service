package io.porko.account.domain;

import static io.porko.account.csv.CsvToJavaBeansConverter.getTransactionRecordCsvBeans;
import static org.assertj.core.api.Assertions.assertThat;

import io.porko.account.csv.TransactionRecordCsvBean;
import io.porko.account.csv.TransactionRecordCsvBeans;
import java.io.IOException;
import java.net.URISyntaxException;
import java.util.List;
import java.util.stream.Stream;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

@DisplayName("Utils:CsvToJavaBeansConverter")
class CsvToJavaBeansConverterTest {
    @ParameterizedTest
    @MethodSource
    @DisplayName("금융 거래 기록 csv 파일을 Java Entity로 변환")
    void mapTo(AccountHolder accountHolder, String fileName) throws URISyntaxException, IOException {
        // When
        List<TransactionRecordCsvBean> actual = getTransactionRecordCsvBeans(fileName, TransactionRecordCsvBean.class);
        List<TransactionRecord> expected = TransactionRecordCsvBeans.toEntity(accountHolder, actual);

        // Then
        assertThat(actual.size()).isEqualTo(expected.size());
    }

    private static Stream<Arguments> mapTo() {
        return Stream.of(
            Arguments.of(AccountHolder.of("이채민", "01011111111"), "data/leechemin.csv"),
            Arguments.of(AccountHolder.of("이민정", "01022222222"), "data/leeminjung.csv"),
            Arguments.of(AccountHolder.of("손보리", "01033333333"), "data/sonbori.csv")
        );
    }
}
