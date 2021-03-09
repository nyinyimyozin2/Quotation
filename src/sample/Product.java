package sample;

public class Product {
    int no;
    String product;
    String description;
    int unit;
    int quantity;
    int priceperunit;

    public Product(){

    }
    public Product(int no, String product, String description, int unit, int quantity, int priceperunit) {
        super();
        this.no = no;
        this.product = product;
        this.description = description;
        this.unit = unit;
        this.quantity = quantity;
        this.priceperunit = priceperunit;

    }
    public int getNo(){
        return no;
    }
    public void setNo(int no){
        this.no = no;
    }
    public String getProduct(){
        return product;
    }
    public void setProduct(String product){
        this.product = product;
    }
    public String getDescription(){
        return description;
    }
    public void setDescription(String description){
        this.description = description;
    }
    public int getUnit(){
        return unit;
    }
    public void setUnit(int unit){
        this.unit = unit;
    }
    public int getQuantity(){
        return quantity;
    }
    public void setQuantity(int quantity){
        this.quantity = quantity;
    }
    public int getPriceperunit(){
        return priceperunit;
    }
    public void setPriceperunit(int priceperunit){
        this.priceperunit = priceperunit;
    }
}
