package com.example.sharkey.Entity;

import java.util.List;

public class Menu {
    private int id;

    private int parent;

    private String icon;

    private String path;

    private String name;

    private List<Menu> subMenus;

    public List<Menu> getSubMenus() {
        return subMenus;
    }

    public String getName() {
        return name;
    }

    public int getId() {
        return id;
    }

    public int getParent() {
        return parent;
    }

    public String getIcon() {
        return icon;
    }

    public String getPath() {
        return path;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setParent(int parent) {
        this.parent = parent;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public void setSubMenus(List<Menu> subMenus) {
        this.subMenus = subMenus;
    }

    public void setName(String name) {
        this.name = name;
    }
}
