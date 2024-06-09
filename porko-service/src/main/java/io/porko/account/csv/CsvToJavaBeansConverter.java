package io.porko.account.csv;

import com.opencsv.bean.CsvToBeanBuilder;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.net.URISyntaxException;
import java.util.List;
import org.springframework.core.io.ClassPathResource;

public class CsvToJavaBeansConverter {
    public static List<TransactionRecordCsvBean> getTransactionRecordCsvBeans(String fileName, Class clazz) throws URISyntaxException, IOException {
        File file = new ClassPathResource(fileName).getFile();
        BufferedReader bufferedReader = new BufferedReader(new FileReader(file));

        return new CsvToBeanBuilder<CsvBean>(bufferedReader)
            .withType(clazz)
            .build()
            .parse();
    }
}
