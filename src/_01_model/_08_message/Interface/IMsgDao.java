package _01_model._08_message.Interface;

import java.util.ArrayList;

import _01_model._08_message.MsgBean;

public interface IMsgDao {
	public MsgBean add(MsgBean Msg);

	public ArrayList<MsgBean> showAllbySupply_id(int supply_id);

	public ArrayList<MsgBean> showAllbyBoth_id(MsgBean mb);
}
