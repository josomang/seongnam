package com.gnc.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.gnc.dto.Criteria;
import com.gnc.dto.LessonsDto;



@Mapper
public interface LessonsDao {
	public void registerDao(String title, String start_at,String end_at, String time,String lecturer, String type,String description);
	
	public void updateDao(@Param("id")int id,@Param("title")String title,
			@Param("start_at")String start_at,@Param("end_at")String end_at, @Param("time")String time,@Param("lecturer")String lecturer, @Param("type")String type,@Param("description")String description);
	
	public List<String> lessonsListDao(Criteria cri);
	
	public List<LessonsDto> lessonsDao(@Param("id")Object id);
	
	public int getCount ();
	
	public int getIdDao(int id);
	
	public List<String> searchDao(@Param("keyword")Object keyword,Criteria cri);
	
	public void deleteDao(@Param("id")int id);
	
	public List<String> test();
	
	public void tblRegisterDao(int id,int center_id, String center_ttl) ;
	
	public List<String> people(@Param("id")int id);
}

