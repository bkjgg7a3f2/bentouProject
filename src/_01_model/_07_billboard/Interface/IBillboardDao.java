package _01_model._07_billboard.Interface;

import java.util.ArrayList;

import _01_model._07_billboard.Billboard;

public interface IBillboardDao {
	public Billboard add(Billboard bil);

	public Billboard update(Billboard bil);

	public void delete(int billboard_id);

	public ArrayList<Billboard> Billshow(Billboard bil);

	public ArrayList<Billboard> selectAll();

	public Billboard selectOne(int billboard_id);
}
