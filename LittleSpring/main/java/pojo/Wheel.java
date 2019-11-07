package pojo;

/**
 * @author ShaoJiale
 * @create 2019/11/7
 * @function 用于测试IOC容器的实体类
 */
public class Wheel {
    private String brand;
    private String specification ;

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public String getSpecification() {
        return specification;
    }

    public void setSpecification(String specification) {
        this.specification = specification;
    }

    @Override
    public String toString() {
        return "Wheel{" +
                "brand='" + brand + '\'' +
                ", specification='" + specification + '\'' +
                '}';
    }
}
