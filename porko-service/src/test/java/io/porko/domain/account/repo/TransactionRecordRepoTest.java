package io.porko.domain.account.repo;


import static io.porko.domain.account.csv.CsvToJavaBeansConverter.getTransactionRecordCsvBeans;
import static org.assertj.core.api.Assertions.assertThat;

import io.porko.config.base.persistence.JpaTestBase;
import io.porko.domain.account.csv.TransactionRecordCsvBean;
import io.porko.domain.account.csv.TransactionRecordCsvBeans;
import io.porko.domain.account.domain.AccountHolder;
import io.porko.domain.account.domain.TransactionRecord;
import java.io.IOException;
import java.net.URISyntaxException;
import java.util.List;
import java.util.stream.Stream;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

@DisplayName("Repo:TransactionRecord")
class TransactionRecordRepoTest extends JpaTestBase {
    private final AccountHolderRepo accountHolderRepo;
    private final TransactionRecordRepo transactionRecordRepo;

    public TransactionRecordRepoTest(AccountHolderRepo accountHolderRepo, TransactionRecordRepo transactionRecordRepo) {
        this.accountHolderRepo = accountHolderRepo;
        this.transactionRecordRepo = transactionRecordRepo;
    }

    @Disabled
    @ParameterizedTest
    @MethodSource
    @DisplayName("Java Entity로 변환된 금융 거래 기록을 저장")
    void mapTo(AccountHolder accountHolder, String fileName) throws URISyntaxException, IOException {
        // Given
        AccountHolder savedAccountHolder = accountHolderRepo.save(accountHolder);
        List<TransactionRecordCsvBean> givenCsvBeans = getTransactionRecordCsvBeans(fileName, TransactionRecordCsvBean.class);
        List<TransactionRecord> givenEntity = TransactionRecordCsvBeans.toEntity(savedAccountHolder, givenCsvBeans);

        // When
        List<TransactionRecord> actual = transactionRecordRepo.saveAllAndFlush(givenEntity);
        List<TransactionRecord> byAccountHolderPhoneNumber = transactionRecordRepo.findByAccountHolder_PhoneNumber(accountHolder.getPhoneNumber());

        // Then
        assertThat(actual).hasSameSizeAs(givenCsvBeans);
        assertThat(actual).hasSameSizeAs(byAccountHolderPhoneNumber.size());
    }

    private static Stream<Arguments> mapTo() {
        return Stream.of(
            Arguments.of(AccountHolder.of("손보리", "01011111111"), "data/sonbori.csv"),
            Arguments.of(AccountHolder.of("이채민", "01022222222"), "data/leechemin.csv"),
            Arguments.of(AccountHolder.of("이민정", "01033333333"), "data/leeminjung.csv")
        );
    }
}
