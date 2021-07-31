package graphicView;

public class CardsOfShop {
    private String name;
    private String imgSrc;
    private int price;
    public void setName(String name) {
        this.name = name;
    }

    public void setImgSrc(String imgSrc) {
        this.imgSrc = imgSrc;
    }

    public void setPrice(int price) {
        this.price = price;
    }
    public String getName() {
        return name;
    }

    public String getImgSrc() {
        return imgSrc;
    }

    public double getPrice() {
        return price;
    }

}
