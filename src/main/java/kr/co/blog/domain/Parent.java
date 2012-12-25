package kr.co.blog.domain;

import java.util.ArrayList;

public class Parent {
    private String name;
    private int age;
    private ArrayList<Child> children = new ArrayList<Child>();
    
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public int getAge() {
        return age;
    }
    public void setAge(int age) {
        this.age = age;
    }
    public ArrayList<Child> getChildren() {
        return children;
    }
    public void setChildren(ArrayList<Child> children) {
        this.children = children;
    }

    public static class Child {
        private String name;
        private int age;
        public String getName() {
            return name;
        }
        public void setName(String name) {
            this.name = name;
        }
        public int getAge() {
            return age;
        }
        public void setAge(int age) {
            this.age = age;
        }
    }

}


