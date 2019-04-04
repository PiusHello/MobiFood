package com.example.mobifood.Model;

public class FoodUnderCategoryModel
{
   public String Description,  Image,  MenuID,  Name,  Price;
    public FoodUnderCategoryModel()
    {

    }

    public FoodUnderCategoryModel(String description, String image, String menuID, String name, String price) {
        Description = description;
        Image = image;
        MenuID = menuID;
        Name = name;
        Price = price;
    }

    public String getDescription() {
        return Description;
    }

    public void setDescription(String description) {
        Description = description;
    }

    public String getImage() {
        return Image;
    }

    public void setImage(String image) {
        Image = image;
    }

    public String getMenuID() {
        return MenuID;
    }

    public void setMenuID(String menuID) {
        MenuID = menuID;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getPrice() {
        return Price;
    }

    public void setPrice(String price) {
        Price = price;
    }
}

