package com.ntc.concurrent.part4.rw;

/**
 * 商品的服务的接口
 * @author Michael-Chen
 */
public interface GoodsService {

	public GoodsInfo getNum();//获得商品的信息
	public void setNum(int number);//设置商品的数量
}
