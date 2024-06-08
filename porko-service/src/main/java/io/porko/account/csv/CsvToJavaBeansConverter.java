package io.porko.account.csv;

import com.opencsv.bean.CsvToBeanBuilder;
import java.io.IOException;
import java.io.Reader;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

public class CsvToJavaBeansConverter {
    public static List<TransactionRecordCsvBean> getTransactionRecordCsvBeans(String fileName, Class clazz) throws URISyntaxException, IOException {
        Path path = Paths.get(ClassLoader.getSystemResource(fileName).toURI());
        Reader reader = Files.newBufferedReader(path);
        return new CsvToBeanBuilder<CsvBean>(reader)
            .withType(clazz)
            .build()
            .parse();
    }
}
