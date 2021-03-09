package _01_model._03_sales;

import java.util.ArrayList;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import _01_model._03_sales.Interface.ISalesService;
import _01_model._05_orders_detail.Details;

@Service
public class SalesService implements ISalesService {

	private SalesDAO sDao;

	@Autowired
	public SalesService(SalesDAO sDao) {
		this.sDao = sDao;
	}

	@Override
	public Sales insert(Sales sal) {
		return sDao.insert(sal);
	}

	@Override
	public Sales update(Sales sal) {
		return sDao.update(sal);
	}

	@Override
	public void delete(int commodity_id) {
		sDao.delete(commodity_id);
	}

	@Override
	public Sales selectOne(int commodity_id) {
		return sDao.selectOne(commodity_id);
	}

	@Override
	public ArrayList<Sales> selectAllbySupplyId(Sales sal) {
		return sDao.selectAllbySupplyId(sal);
	}

	@Override
	public Map<Integer, Integer> map_sales(Sales sa) {
		return sDao.map_sales(sa);
	}

	@Override
	public ArrayList<Integer> supply_id_appear_once_list(String jsonlist) {
		return sDao.supply_id_appear_once_list(jsonlist);
	}

	@Override
	public ArrayList<Details> insertbySupply_id(int order_supply_id, String id_list) {
		return sDao.insertbySupply_id(order_supply_id, id_list);
	}

	public int supply_limit_sum(int supply_id, String list) {
		return sDao.supply_limit_sum(supply_id, list);
	}

	public ArrayList<Sales> search_Sales(String txt) {
		return sDao.search_Sales(txt);
	}
}
