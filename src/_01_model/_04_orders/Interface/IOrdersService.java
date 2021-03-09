package _01_model._04_orders.Interface;

import java.util.ArrayList;

import _01_model._04_orders.Orders;

public interface IOrdersService {
	public Orders add(Orders od);

	public Orders update(Orders od);

	public void delete(int order_id);
	
	public Orders selectOne(String order_id);
	
	public ArrayList<Orders> selectAll_cstmr_view(int order_cstmr_id);
	
	public ArrayList<Orders> selectAll_supply_view(int order_supply_id);
}
