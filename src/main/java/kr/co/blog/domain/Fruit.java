package kr.co.blog.domain;

import java.util.ArrayList;
import java.util.List;

public class Fruit {
    private String name;
    private String type;
    private Grape grape;
    private List<Banana> banana = new ArrayList<Banana>();

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Grape getGrape() {
        return grape;
    }

    public void setGrape(Grape grape) {
        this.grape = grape;
    }

    public List<Banana> getBanana() {
        return banana;
    }

    public void setBanana(List<Banana> banana) {
        this.banana = banana;
    }

    /**
     * 
     * @author kbtapjm
     * grape
     *
     */
    public static class Grape {
        private String name;
        private  int amount;
        private  String country;
        public String getName() {
            return name;
        }
        public void setName(String name) {
            this.name = name;
        }
        public int getAmount() {
            return amount;
        }
        public void setAmount(int amount) {
            this.amount = amount;
        }
        public String getCountry() {
            return country;
        }
        public void setCountry(String country) {
            this.country = country;
        }
    }
    
    /**
     * banana 
     * @author kbtapjm
     *
     */
    public static class Banana {
        private String name;
        private  int amount;
        private  String country;
        private int tax;
        public String getName() {
            return name;
        }
        public void setName(String name) {
            this.name = name;
        }
        public int getAmount() {
            return amount;
        }
        public void setAmount(int amount) {
            this.amount = amount;
        }
        public String getCountry() {
            return country;
        }
        public void setCountry(String country) {
            this.country = country;
        }
        public int getTax() {
            return tax;
        }
        public void setTax(int tax) {
            this.tax = tax;
        }
    }
     
}
