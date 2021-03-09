package _01_model._05_orders_detail;

import java.util.ArrayList;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import _01_model._05_orders_detail.Interface.IDetailsService;

@Service
public class DetailsService implements IDetailsService {
	private DetailsDao dDao;

	@Autowired
	public DetailsService(DetailsDao dDao) {
		this.dDao = dDao;
	}

	@Override
	public Details add(Details de) {
		return dDao.add(de);
	}

	@Override
	public Details update(Details de) {
		return dDao.update(de);
	}

	@Override
	public void delete(int order_id) {
		dDao.delete(order_id);
	}

	@Override
	public Details selectOne(int order_id) {
		return dDao.selectOne(order_id);
	}
	
	@Override
	public ArrayList<Details> selectAllbyOrder_id(Details de) {
		return dDao.selectAllbyOrder_id(de);
	}
	
	@Override
	public Map<Integer, Integer> map_details(String order_id) {
		return dDao.map_details(order_id);
	}
	
	public ArrayList<Details> selectAllbySupply_id(int supply_id){
		return dDao.selectAllbySupply_id(supply_id);
	}
}
