package com.example.Library.Model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
public class Item {

        @Id
        @GeneratedValue
        private Integer ItemId;
        private String ItemName;


        public Item() {}

        Item(String name) {
            this.ItemName = name;

        }

        public Integer getItemId() {
            return this.ItemId;
        }

        public String getItemName() {
            return this.ItemName;
        }

        public void setItemId(Integer ItemId) { this.ItemId = ItemId; }

        public void setItemName(String ItemName){ this.ItemName = ItemName; }


    }

