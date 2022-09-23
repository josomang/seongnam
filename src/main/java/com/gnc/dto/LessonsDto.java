package com.gnc.dto;



import java.util.Date;

import lombok.Data;

@Data
public class LessonsDto {
	private int id;
	private String title;
	private String lecturer;
	private String type;
	private String description;
	private String start_at;
	private String end_at;
	private int center;
	private int attendances;
	private String time;

	
}
