package ua.pp.disik.limitedtree;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class CatalogItem {
    @JsonIgnore
    private CatalogItem parent;

    private long id;
    private long parentId;
    private List<CatalogItem> childs = new ArrayList<>();
}
