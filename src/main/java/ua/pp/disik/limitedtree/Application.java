package ua.pp.disik.limitedtree;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import lombok.SneakyThrows;

import java.io.FileReader;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Application {
    @SneakyThrows
    public static void main(String[] args) {
        ObjectMapper objectMapper = new ObjectMapper()
                .configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false)
                .configure(SerializationFeature.INDENT_OUTPUT, true);

        List<Long> ids = Stream.of(args).map(item -> Long.valueOf(item)).collect(Collectors.toList());
        List<CatalogItem> list = objectMapper.readValue(new FileReader("catalog/list.json"), new TypeReference<List<CatalogItem>>() {});
        CatalogItem tree = CatalogUtil.compileTreeFromTerminals(ids, list);
        System.out.println(objectMapper.writeValueAsString(tree));
    }
}
