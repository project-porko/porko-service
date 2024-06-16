package io.porko.domain.account.csv;

import com.opencsv.bean.CsvToBeanBuilder;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.nio.charset.StandardCharsets;
import java.util.List;
import org.springframework.core.io.ClassPathResource;

public class CsvToJavaBeansConverter {
    public static List<TransactionRecordCsvBean> getTransactionRecordCsvBeans(String fileName, Class clazz) throws IOException {
        ClassPathResource classPathResource = new ClassPathResource(fileName);
        InputStream is = classPathResource.getInputStream();
        Reader reader = new InputStreamReader(is, StandardCharsets.UTF_8);
        BufferedReader bufferedReader = new BufferedReader(reader);

        return new CsvToBeanBuilder<CsvBean>(bufferedReader)
            .withType(clazz)
            .build()
            .parse();
    }
}
