package com.example.android.employeesmanagementsoftware.DepartmentDB.DepartementRowData;

/**
     * A dummy item representing a piece of content.
     */
    public  class DepartmentItem {
        public final Long id;
        public final String name;
        public final String details;

        public DepartmentItem(Long id, String content, String details) {
            this.id = id;
            this.name = content;
            this.details = details;
        }

        @Override
        public String toString() {
            return name;
        }
    }

