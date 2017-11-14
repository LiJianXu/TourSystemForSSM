package com.ssm.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ssm.dao.OccupationsMapper;
import com.ssm.entity.Occupations;

@Service
public class OccupationsService {
	
	@Autowired
	private OccupationsMapper occupationsMapper;
	
	/**
	 * 通过职业id 得到职业信息
	 * @param occid
	 * @return
	 */
	public Occupations getOccupationsById(Integer occid){
		Occupations occupations=null;
		try {
			occupations=occupationsMapper.selectByPrimaryKey(occid);
			return occupations;
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		return occupations;
	}
	
	//添加职业信息
	public Integer insertOccupations(String name){
		Integer id=0;
		try {
			id=occupationsMapper.insertByName(name);
			if (id>0) {
				return id;
			}
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		return 0;
	}
}
