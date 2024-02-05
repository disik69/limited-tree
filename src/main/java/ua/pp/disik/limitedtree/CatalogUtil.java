package ua.pp.disik.limitedtree;

import java.util.List;
import java.util.stream.Collectors;

public final class CatalogUtil {
    public static CatalogItem compileTreeFromTerminals(List<Long> ids, List<CatalogItem> items) {
        List<CatalogItem> roots = items.stream()
                .filter(item -> item.getParentId() == 0)
                .collect(Collectors.toList());
        if (roots.size() == 0) {
            throw new RuntimeException("Catalog list doesn't contain root");
        }
        if (roots.size() > 1) {
            throw new RuntimeException("Catalog list contains several roots");
        }
        CatalogItem root = roots.get(0);

        List<CatalogItem> terminalItems = ids.stream()
                .map(id -> searchItemInPile(id, items))
                .collect(Collectors.toList());

        for (CatalogItem terminalItem: terminalItems) {
            CatalogItem currentItem = terminalItem;

            while (currentItem != root) {
                CatalogItem parentItem = searchItemInBranches(currentItem.getParentId(), terminalItems);
                if (parentItem == null) {
                    parentItem = searchItemInPile(currentItem.getParentId(), items);

                    parentItem.getChilds().add(currentItem);
                    currentItem.setParent(parentItem);

                    currentItem = parentItem;
                } else {
                    parentItem.getChilds().add(currentItem);
                    currentItem.setParent(parentItem);

                    break;
                }
            }
        }

        return root;
    }

    private static CatalogItem searchItemInPile(Long id, List<CatalogItem> pile) {
        for (CatalogItem item: pile) {
            if (item.getId() == id) {
                return item;
            }
        }
        return null;
    }

    private static CatalogItem searchItemInBranches(Long id, List<CatalogItem> terminalItems) {
        for (CatalogItem terminalItem: terminalItems) {
            CatalogItem currentItem = terminalItem;

            while (currentItem != null) {
                if (currentItem.getId() == id) {
                    return currentItem;
                }

                currentItem = currentItem.getParent();
            }
        }
        return null;
    }
}

