package _01_model._02_supply;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import _01_model._02_supply.Interface.ISupplyService;

@Service
public class SupplyService implements ISupplyService {
	private SupplyDAO sDao;

	@Autowired
	public SupplyService(SupplyDAO sDao) {
		this.sDao = sDao;
	}

	@Override
	public Supply insert(Supply sup) {
		return sDao.insert(sup);
	}

	@Override
	public Supply update_data(Supply s) {
		return sDao.update_data(s);
	}

	public Supply update_image(Supply s) {
		return sDao.update_image(s);
	}

	@Override
	public Supply update_pwd(Supply s) {
		return sDao.update_pwd(s);
	}

	public Supply update_vip(Supply s) {
		return sDao.update_vip(s);
	}

	@Override
	public Supply selectOne(int supply_id) {
		return sDao.selectOne(supply_id);
	}

	@Override
	public ArrayList<Supply> selectall() {
		return sDao.selectall();
	}

	@Override
	public Supply selectOnebyacc_pwd(Supply sup) {
		return sDao.selectOnebyacc_pwd(sup);
	}

	@Override
	public Supply selectOnebyacc(String useracc) {
		return sDao.selectOnebyacc(useracc);
	}

	@Override
	public int select_newest_id() {
		return sDao.select_newest_id();
	}

	public ArrayList<Integer> search_Supply(String txt) {
		return sDao.search_Supply(txt);
	}
}
