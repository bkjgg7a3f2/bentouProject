package _01_model._02_supply.Interface;

import java.util.ArrayList;

import _01_model._02_supply.Supply;

public interface ISupplyDAO {
	public Supply insert(Supply sup);

	public Supply update_data(Supply s);

	public Supply update_pwd(Supply s);

	public Supply selectOne(int supply_id);

	public ArrayList<Supply> selectall();

	public Supply selectOnebyacc(String useracc);

	public Supply selectOnebyacc_pwd(Supply s);

	public int select_newest_id();
}
