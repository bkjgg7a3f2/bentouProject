package _01_model._07_billboard;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import _01_model._07_billboard.Interface.IBillboardService;


@Service
public class BillboardService implements IBillboardService {
	
	private BillboardDao biDao;
	
	@Autowired
	public BillboardService(BillboardDao biDao) {
		this.biDao = biDao;
	}

	@Override
	public Billboard add(Billboard bil) {
		return biDao.add(bil);
	}

	@Override
	public Billboard update(Billboard bil) {
		return biDao.update(bil);
	}

	@Override
	public void delete(int billboard_id) {
		biDao.delete(billboard_id);
	}

	@Override
	public ArrayList<Billboard> Billshow(Billboard bil) {
		return biDao.Billshow(bil);
	}

	@Override
	public ArrayList<Billboard> selectAll() {
		return biDao.selectAll();
	}

	@Override
	public Billboard selectOne(int billboard_id) {
		return biDao.selectOne(billboard_id);
	}

}
