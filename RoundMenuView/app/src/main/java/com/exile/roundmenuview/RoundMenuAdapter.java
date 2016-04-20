package com.exile.roundmenuview;

import java.util.List;

/**
 * 圆盘菜单的适配器
 * Created by Exile on 2016/4/19.
 */
public class RoundMenuAdapter {
    private List<RoundMenuBean> menuBeanList;
    public RoundMenuAdapter(List<RoundMenuBean> menuBeanList) {
        this.menuBeanList = menuBeanList;

    }

    /**
     * 获取菜单的列表
     * @return
     */
    public List<RoundMenuBean> getMenuBeanList(){
        return menuBeanList;
    }

    /**
     * 获取列表长度
     * @return
     */
    public int getMenuBeanListSize(){
        return menuBeanList.size();
    }
}
