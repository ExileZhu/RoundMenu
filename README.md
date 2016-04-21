# RoundMenu
这是一个圆盘选择控件，有点击选择的对应条目的方法，没有adapter 
                                                          v0.1 beta
#v0.2 
添加 bean 和adapter
使用方法：
 RoundMenuView rmv_wheel = (RoundMenuView) findViewById(R.id.rmv_wheel);
        ArrayList<RoundMenuBean> arrayList = new ArrayList<>();
        arrayList.add(new RoundMenuBean("质 量",1));
        arrayList.add(new RoundMenuBean("服 务",2));
        arrayList.add(new RoundMenuBean("评 价",3));
        arrayList.add(new RoundMenuBean("发 货 速 度",4));
        arrayList.add(new RoundMenuBean("快 递 速 度",5));
        RoundMenuAdapter roundMenuAdapter = new RoundMenuAdapter(arrayList);
        rmv_wheel.setMenuAdapter(roundMenuAdapter);
