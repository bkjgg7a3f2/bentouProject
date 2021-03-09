package _01_model._03_sales.Interface;

import java.util.ArrayList;
import java.util.Map;

import _01_model._03_sales.Sales;
import _01_model._05_orders_detail.Details;

public interface ISalesService {
	public Sales insert(Sales sal);

	public Sales update(Sales sal);

	public void delete(int commodity_id);

	public Sales selectOne(int commodity_id);

	public ArrayList<Sales> selectAllbySupplyId(Sales sal);

	public Map<Integer, Integer> map_sales(Sales sa);

	public ArrayList<Integer> supply_id_appear_once_list(String id_list);

	public ArrayList<Details> insertbySupply_id(int order_supply_id, String id_list);
}
