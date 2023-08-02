package com.green.babyfood.admin.model;

import lombok.Data;

@Data
public class AdminProductInsDto {
   String title;
   String name;
   int price;
   int quantity;
   String description;
   int allergy;
   int delete;
}
