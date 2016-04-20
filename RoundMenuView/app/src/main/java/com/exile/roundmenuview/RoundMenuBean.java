package com.exile.roundmenuview;

/**
 * Created by Exile on 2016/4/19.
 */
public class RoundMenuBean {
    public RoundMenuBean(String itemName, int starCount) {
        this.itemName = itemName;
        this.starCount = starCount;
    }
    private String itemName;
    private int starCount;

    /**
     * 获取菜单的名称
     * @return
     */
    public String getItemName(){
        return itemName;
    }

    /**
     * 获取名称对应的星级
     * @return
     */
    public int getStarCount(){
        return starCount;
    }
}
