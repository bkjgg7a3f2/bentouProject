package _01_model._04_orders;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import _01_model._04_orders.Interface.IOrdersService;

@Service
public class OrdersService implements IOrdersService {
	private OrdersDao oDao;

	@Autowired
	public OrdersService(OrdersDao oDao) {
		this.oDao = oDao;
	}

	@Override
	public Orders add(Orders od) {
		return oDao.add(od);
	}

	@Override
	public Orders update(Orders od) {
		return oDao.update(od);
	}

	@Override
	public void delete(int order_id) {
		oDao.delete(order_id);
	}

	@Override
	public Orders selectOne(String order_id) {
		return oDao.selectOne(order_id);
	}

	@Override
	public ArrayList<Orders> selectAll_cstmr_view(int order_cstmr_id) {
		return oDao.selectAll_cstmr_view(order_cstmr_id);
	}

	@Override
	public ArrayList<Orders> selectAll_supply_view(int order_supply_id) {
		return oDao.selectAll_supply_view(order_supply_id);
	}

}
