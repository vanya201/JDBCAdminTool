package ua.cn.stu.model;

public class Product {
    private Long id;
    private String name;
    private String description;
    private Long SellerID;

    public Long getSellerID() { return SellerID;}
    public void setSellerID(Long sellerID) { SellerID = sellerID;}
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public String getDescription() {
        return description;
    }
    public void setDescription(String description) {
        this.description = description;
    }
    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    @Override
    public String toString() {
        return "id - " + id + ", name - " + name + ", description - " +
                description + ", SellerID - " + SellerID;
    }
}
