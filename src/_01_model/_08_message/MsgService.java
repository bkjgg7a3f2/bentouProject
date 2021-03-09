package _01_model._08_message;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import _01_model._08_message.Interface.IMsgService;

@Service
public class MsgService implements IMsgService {
	private MsgDao MsgDao;

	@Autowired
	public MsgService(MsgDao MsgDao) {
		this.MsgDao = MsgDao;
	}

	@Override
	public MsgBean add(MsgBean Msg) {
		return MsgDao.add(Msg);
	}

	@Override
	public ArrayList<MsgBean> showAllbySupply_id(int supply_id) {
		return MsgDao.showAllbySupply_id(supply_id);
	}

	@Override
	public ArrayList<MsgBean> showAllbyBoth_id(MsgBean mb) {
		return MsgDao.showAllbyBoth_id(mb);
	}

}
