package com.example.android.employeesmanagementsoftware.DepartmentDB.DepartementRowData;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Helper class for providing sample content for user interfaces created by
 * Android template wizards.
 * <p>
 * TODO: Replace all uses of this class before publishing your app.
 */
public class DepartmentData {

    /**
     * An array of sample (Department) items.
     */
    private List<DepartmentItem> items = new ArrayList<DepartmentItem>();

    /**
     * A map of sample (dummy) items, by ID.
     */
    private Map<String, DepartmentItem> itemMap = new HashMap<String, DepartmentItem>();




    public void addItem(DepartmentItem item) {
        items.add(item);
        itemMap.put(item.id, item);
    }
    public  List<DepartmentItem> getItems(){
        return items;
    }

    /**
     * A dummy item representing a piece of content.
     */
    public static class DepartmentItem {
        public final String id;
        public final String name;
        public final String details;

        public DepartmentItem(String id, String content, String details) {
            this.id = id;
            this.name = content;
            this.details = details;
        }

        @Override
        public String toString() {
            return name;
        }
    }
}
