package _01_model._05_orders_detail.Interface;

import java.util.ArrayList;
import java.util.Map;

import _01_model._05_orders_detail.Details;

public interface IDetailsService {
	public Details add(Details de);

	public Details update(Details de);

	public void delete(int order_id);

	public Details selectOne(int order_id);

	public ArrayList<Details> selectAllbyOrder_id(Details de);

	public Map<Integer, Integer> map_details(String order_id);
}
