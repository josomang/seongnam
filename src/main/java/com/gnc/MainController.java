package com.gnc;

import java.util.Date;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.SessionAttribute;

import com.gnc.dao.LessonsDao;
import com.gnc.dao.UserDao;
import com.gnc.dto.Criteria;
import com.gnc.dto.LessonsDto;
import com.gnc.dto.LoginDto;
import com.gnc.dto.Paging;

@Controller
public class MainController {
	
	String key;
	int masterId;
	@Autowired
	UserDao userDao;
	
	@Autowired
	LessonsDao lessonsDao;

	
	@GetMapping("/")
	public String root(@SessionAttribute(name=SessionConstants.LOGIN_MEMBER,required=false)
	String check) {
		
			return "redirect:/admin";
	}

	@GetMapping("/login")
	public String loginForm() {
			return "login";
	}
	
	@PostMapping("/login")
	public String login(@RequestBody LoginDto loginDto,  BindingResult bindingResult, 
			HttpServletRequest request) {
	String check = userDao.getUserAccount(loginDto.getUserId(), loginDto.getUserPw());
		
		
		if(check== null) {
			bindingResult.reject("loginFail","아이디 또는 비밀번호가 맞지 않습니다.");
			return "login";
		}
		
			HttpSession session = request.getSession();
			session.setAttribute(SessionConstants.LOGIN_MEMBER, check);
			
			
			return "redirect:/";
		
		
		
	}
	
	

	@PostMapping("/logout")
	public String logout(HttpServletRequest request) {
		System.out.print("실행되었습니다.");
		Date date = new Date();
		System.out.println(date);
		HttpSession session = request.getSession(false);
		if(session != null) {
			session.invalidate();
		}
		return "redirect:/login";
		
	}
	

	@GetMapping("/admin")
	public String home(@SessionAttribute(name=SessionConstants.LOGIN_MEMBER,required=false)
	String check	,@ModelAttribute("cri")Criteria cri,Model model,LessonsDto lessonsDto) {
		if(check == null) {
			return "redirect:/login";
		}
		
		model.addAttribute("list", lessonsDao.lessonsListDao(cri));
		
		
		
		Paging pageMaker = new Paging();
		pageMaker.setCri(cri);
		
		pageMaker.setTotalCount(lessonsDao.getCount());
		
		
		
	
		
		
		
		model.addAttribute("pageMaker", pageMaker);
		
		
		
		return "admin01_list";
	}



	@PostMapping("/register")
	public @ResponseBody String register(@RequestBody LessonsDto lessonsDto) {
		
		lessonsDao.registerDao(lessonsDto.getTitle(), lessonsDto.getStart_at(),
				lessonsDto.getEnd_at(), lessonsDto.getTime(),
				lessonsDto.getLecturer(), lessonsDto.getType(), lessonsDto.getDescription());

		return "redirect:";
	}
	
	
	@PostMapping("/update")
	public @ResponseBody String update(@RequestBody LessonsDto lessonsDto) {
		
		lessonsDao.updateDao(masterId,lessonsDto.getTitle(), lessonsDto.getStart_at(),
				lessonsDto.getEnd_at(), lessonsDto.getTime(),
				lessonsDto.getLecturer(), lessonsDto.getType(), lessonsDto.getDescription());

		return "redirect:/view";
	}


	@RequestMapping("/lect_result")
	public String admin01_view(@SessionAttribute(name=SessionConstants.LOGIN_MEMBER,required=false)
	String check ,int id,Model model,LessonsDto lessonsDto) {
		if(check == null) {
			return "redirect:/login";
		}
		//List<LessonsDto> a =  lessonsDao.lessonsDao(id);
		model.addAttribute("view",lessonsDao.lessonsDao(id));
		
		masterId=id;
		
		
		//System.out.println(id);
		//System.out.println(lessonsDao.lessonsDao(id));
		//System.out.print(lessonsDao.lessonsDao(lessonsDto.getId()));
		
		return "admin01_view";
	}
	/*
	 * @RequestMapping("/lect_result") public String lect_result(int
	 * center_id,String center_ttl,Model model) {
	 * lessonsDao.tblRegisterDao(masterId, center_id, center_ttl);
	 * model.addAttribute("people",lessonsDao.people(masterId));
	 * 
	 * 
	 * 
	 * return ""; }
	 */
	
	
	
	@RequestMapping("/keyword")
	
	public String home2( @RequestBody String keyword) {
		
	
		key = keyword;
		return "redirect:" + "/search";
		
	}
	
	@RequestMapping("/search")
	public String search(@SessionAttribute(name=SessionConstants.LOGIN_MEMBER,required=false)
	String check,@ModelAttribute("cri")Criteria cri,Model model) {
	
		if(check == null) {
			return "redirect:/login";
		}
		model.addAttribute("list", lessonsDao.searchDao(key,cri));
		
		
		
		
		Paging pageMaker = new Paging();
		pageMaker.setCri(cri);
		
		pageMaker.setTotalCount(lessonsDao.getCount());
		
		
		
	
		
		
		
		model.addAttribute("pageMaker", pageMaker);
		
		
		
		return "admin01_list";
	}
	@GetMapping("/ar")
	public String test(@SessionAttribute(name=SessionConstants.LOGIN_MEMBER,required=false)
	String check) {
		if(check == null) {
			return "redirect:/login";
		}
		return "admin02";
	}
	
	@PostMapping("/delete")
	public String delete() {
		lessonsDao.deleteDao(masterId);
		
		return "redirect:/admin";
		
		
	}
	
}




