package cd.com.a.controller;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.forwardedUrl;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.io.FileUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.google.gson.JsonObject;

import cd.com.a.model.adminShopParam;
import cd.com.a.model.memberDto;
import cd.com.a.model.shopDesignerDto;
import cd.com.a.model.shopDto;
import cd.com.a.model.shopListParam;
import cd.com.a.model.shopPagingParam;
import cd.com.a.model.shopResvDto;
import cd.com.a.model.shopSellerPagingParam;
import cd.com.a.model.shopSellerResvParam;
import cd.com.a.model.shopShowResvParam;
import cd.com.a.service.MemberService;
import cd.com.a.service.ShopService;
import cd.com.a.util.FileUploadUtil;
import oracle.net.aso.f;

@Controller
public class ShopController {
	@Autowired
	ShopService shopService;
	
	@Autowired
	MemberService memService;
	


	
	


	

//--------------------------------------------------------------MJ--------------------------------------------------	
	
	@RequestMapping(value="getShopList.do",  method= {RequestMethod.GET,RequestMethod.POST})
	public String getShopList(Model model, shopListParam param) {
		
		System.out.println("shoplist Param ========================== "+param.toString());
		// paging 처리
		int pageNumber = param.getPageNumber();	// 0 1 2	현재 페이지
		int start = pageNumber * param.getRecordCountPerPage(); // 0, 10, 21
		
		int end = (pageNumber + 1) * param.getRecordCountPerPage();	// 10, 20, 30
		
		param.setStart(start);
		param.setEnd(end);
		
		List<shopDto> shoplist = shopService.getShopList(param);
		List<shopDto> shopSuccessList = new ArrayList<shopDto>();
		if(shoplist.size()!=0) {
			for(int i = 0; i< shoplist.size(); i++) {
				shopDto shopDesignDto = new shopDto();
				shopDesignDto = shoplist.get(i);
				if(shopService.checkDesigner(shopDesignDto.getShop_seq())) {
					shopSuccessList.add(shopDesignDto);
				}
			}
			
		}
		
		//글의 총수
		int totalRecordCount = shopService.getShopCount();
		
		/* model.addAttribute("shoplist", shoplist); */
		model.addAttribute("shoplist", shopSuccessList);
		model.addAttribute("pageNumber", pageNumber);
		model.addAttribute("pageCountPerScreen", 10);
		model.addAttribute("recordCountPerPage", param.getRecordCountPerPage());
		model.addAttribute("totalRecordCount", totalRecordCount);
		model.addAttribute("param",param);
		
		return "/shop/shop";
	}

	
	
	@ResponseBody
	@RequestMapping(value="getResvTime.do",  method= {RequestMethod.GET,RequestMethod.POST})
	public Map<Integer, Object> resv(shopResvDto resvDto) {
		System.out.println("======ResvDto:" + resvDto.toString());
		
		Map<Integer, Object> map = new HashMap<Integer, Object>();
		
		
		 
		 
	/*	List<shopDesignerDto> resvDesList = shopService.getDesigner(shop_seq);
		for(int i = 0; i<resvDesList.size(); i++) {
			shopDesignerDto dto = resvDesList.get(i);
			dd =  dto.getDesign_name();
		}*/
//		 
		List<String> resvTime = shopService.getResv(resvDto);
		
		for(int i = 0; i<resvTime.size(); i++) {
			map.put(i+1,resvTime.get(i));
		}
//		
//		String noResvTime = "";
//			
//		 List<shopResvDto> resvTimeList = shopService.getResv(design_seq);
//			for(int i = 0; i<resvTimeList.size(); i++) {
//				shopResvDto dto = resvTimeList.get(i);
//			
//			}
//		
//		String d = (String) map.get("design_name");
//		System.out.println("========d" + d);
//		//map.put("resvDesList", resvDesList);
//		//map.put("desgine_name",map.get("design_name"));
//		map.put("desgine_name",dd);
//		
//		String desTime[];
//		
//		List<shopDesignerDto> desList = shopService.getDesigner(shop_seq);
//		for(int j = 0; j<desList.size(); j++ ) {
//			shopDesignerDto desDto = desList.get(j);
//			if(desDto.getDesign_seq() == design_seq) {
//				String time = desDto.getDesign_time();
//				desTime = time.split("/");
//				for(int )
//				System.out.println("==========TestDesTiem:"+ desTime);
//			}
//		}
//		map.put("i", time);
//		map.put("i", time);
//		map.put("i", time);
//		map.put("1", time);
//		map.put("1", time);
//		map.put("1", time);
//		
		
		return map;
	}
	
	
	@RequestMapping(value="shopDetail.do",  method= {RequestMethod.GET,RequestMethod.POST})
	public String shopResv(Model model, int shop_seq) {
		
		
		System.out.println("===========shopSeq" + shop_seq);
		List<shopDesignerDto> designerList = shopService.getDesigner(shop_seq);
		shopDto shopDetail = shopService.getShopDetail(shop_seq);
		model.addAttribute("shopDetail", shopDetail);
		model.addAttribute("designerList", designerList);
		
		return "/shop/shopDetail";
	}
	
	@RequestMapping(value="shopResvWrite.do",  method= {RequestMethod.GET,RequestMethod.POST})
	public String shopResvWrite(Model model, HttpServletRequest request, shopResvDto shopResvdto) {
		//String sshop_seq = request.getParameter("shop_seq");
		//int shop_seq = Integer.parseInt(sshop_seq);
		System.out.println("=========!!!!!!!resvdto" + shopResvdto.toString());
		//System.out.println("===========parameterShopSeq:" +shop_seq);
		
		shopDto shopdto = shopService.getShopDetail(shopResvdto.getShop_seq());
		shopDesignerDto desdto = shopService.getDesignerInfo(shopResvdto.getDesign_seq());
		
		memberDto mem = memService.resvMem(shopResvdto.getMem_seq());
		
		shopResvdto.setShop_resv_name(mem.getUser_name());
		shopResvdto.setShop_resv_tel(mem.getPhone());
		
		model.addAttribute("shopdto", shopdto);
		model.addAttribute("desdto", desdto);
		model.addAttribute("shopResvdto", shopResvdto);
		
		return "/shop/shopResvWrite";
	}
	
	@ResponseBody
	@RequestMapping(value="shopResv.do",  method= {RequestMethod.GET,RequestMethod.POST})
	public Map<String, Object> shopResv(@ModelAttribute shopResvDto shopResv) {
		System.out.println("shop 예약 내역:" + shopResv.toString());
		int result = shopService.resvShop(shopResv);
		Map<String, Object> rmap = new HashMap<String, Object>();
		
		if(result != 0) {
			rmap.put("status", "ok");
			rmap.put("rnum", result);
		}else {
			rmap.put("status", "no");
		}
		return rmap;
	}
	
	
	@RequestMapping(value="shopResvAf.do",  method= {RequestMethod.GET,RequestMethod.POST})
	public String shopResvAf(int shop_resv_seq, Model model) {
		System.out.println("===========예약완료 시퀀스:"+ shop_resv_seq);
		shopResvDto shopResv = shopService.getShopResv(shop_resv_seq);
		shopDto shop = shopService.getShopDetail(shopResv.getShop_seq());
		System.out.println("shopResvAf shop toString:" + shop.toString() );
		shopDesignerDto desdto = shopService.getDesignerInfo(shopResv.getDesign_seq());
		
		model.addAttribute("shopResv", shopResv);
		model.addAttribute("shop", shop);
		model.addAttribute("desdto", desdto);
		
		return "/shop/shopResvAf";
		
	}
	
	
	// 유저 미용 예약 리스트
	@RequestMapping(value="showShopResv.do",  method= {RequestMethod.GET,RequestMethod.POST})
	public String showShopResv(Model model, HttpSession session, shopPagingParam param) {
		memberDto loginUser = (memberDto)session.getAttribute("loginUser");
		System.out.println("=============showResv: " + loginUser.getMem_seq() );
		// paging 처리
		int pageNumber = param.getPageNumber();	// 0 1 2	현재 페이지
		int start = pageNumber * param.getRecordCountPerPage(); // 0, 10, 21
		
		int end = (pageNumber + 1) * param.getRecordCountPerPage();	// 10, 20, 30
		
		param.setStart(start);
		param.setEnd(end);
		param.setMem_seq(loginUser.getMem_seq());
		System.out.println("param=====" + param.toString());
		
		List<shopShowResvParam> showShopList = shopService.showShopResv(param);
		
		for(int i =0; i<showShopList.size(); i++) {
			shopShowResvParam dto = showShopList.get(i);
			System.out.println("=====dto.to.:" +dto.toString());
		}
		
		// 글의 총수
		int totalRecordCount = shopService.getShopResvCount(loginUser.getMem_seq());

		model.addAttribute("showShopList", showShopList);
		System.out.println("size=====" + showShopList.size());
		
		model.addAttribute("pageNumber", pageNumber);
		model.addAttribute("pageCountPerScreen", 10);
		model.addAttribute("recordCountPerPage", param.getRecordCountPerPage());
		model.addAttribute("totalRecordCount", totalRecordCount);
	
		return "/shop/showShopResv";
	}
	
	// 유저 미용 예약 취소 리스트 
	@RequestMapping(value="shopResvCancelList.do",  method= {RequestMethod.GET,RequestMethod.POST})
	public String shopResvCancelList(Model model, HttpSession session, shopPagingParam param) {
		memberDto loginUser = (memberDto)session.getAttribute("loginUser");
		// paging 처리
		int pageNumber = param.getPageNumber();	// 0 1 2	현재 페이지
		int start = pageNumber * param.getRecordCountPerPage(); // 0, 10, 21
		
		int end = (pageNumber + 1) * param.getRecordCountPerPage();	// 10, 20, 30
		
		param.setStart(start);
		param.setEnd(end);
		param.setMem_seq(loginUser.getMem_seq());
		
		List<shopShowResvParam> cancelShopList = shopService.shopShopCancelResv(param);
		
		int totalRecordCount = shopService.getShopCancelResvCount(loginUser.getMem_seq());
		System.out.println("================================================cancelseq+" +loginUser.getMem_seq());
		
		model.addAttribute("cancelShopList", cancelShopList);
		
		model.addAttribute("pageNumber", pageNumber);
		model.addAttribute("pageCountPerScreen", 10);
		model.addAttribute("recordCountPerPage", param.getRecordCountPerPage());
		model.addAttribute("totalRecordCount", totalRecordCount);
		
		return "/shop/showShopCancelResv";
		
	}
	
	// 유저 미용 예약 취소
	@ResponseBody
	@RequestMapping(value="cancelShopResv.do",  method= {RequestMethod.GET,RequestMethod.POST})
	public String cancelShopResv(shopResvDto shopresv) {
		String str = "";
		int count = shopService.shopCalcelTimeCheck(shopresv);
		
		if(count == 1 ) {
			boolean b = shopService.cancelShopResv(shopresv);
			if(b) {
				str = "ok";
			}else {
				str = "no";
			}
		} else if(count == 0) {
			str = "no";
		}
		
		return str;
	}
	
	////// 셀러 미용 예약 리스트
	@RequestMapping(value="shopSellerResvList.do",  method= {RequestMethod.GET,RequestMethod.POST})
	public String shopSellerResvList(HttpSession session, Model model, shopSellerPagingParam param) {
		memberDto mem = (memberDto)session.getAttribute("loginUser");
		param.setMemSeq(mem.getMem_seq());
		// paging 처리
		int pageNumber = param.getPageNumber();	// 0 1 2	현재 페이지
		int start = pageNumber * param.getRecordCountPerPage(); // 1, 11, 21
		
		int end = (pageNumber + 1) * param.getRecordCountPerPage();	// 10, 20, 30
		
		param.setStart(start);
		param.setEnd(end);
		int totalRecordCount = shopService.getSellerResvCount(param);
		
		List<shopSellerResvParam> resvlist = shopService.getSellerShopResvList(param);
		for(int i=0; i<resvlist.size();i++) {
			shopSellerResvParam dto = resvlist.get(i);
			
		}
		List<shopDto> shoplist = shopService.getSellerShopList(mem.getMem_seq());
		
		model.addAttribute("shopresvlist", resvlist);
		model.addAttribute("shoplist", shoplist);
		model.addAttribute("pageNumber", pageNumber);
		model.addAttribute("pageCountPerScreen", 10);
		model.addAttribute("recordCountPerPage", param.getRecordCountPerPage());
		model.addAttribute("totalRecordCount", totalRecordCount);
		model.addAttribute("param",param);
		
		return "/smypage/shop/sellerShopResvList";
	}
	
	@RequestMapping(value="shopSellerResvDetail.do",  method= {RequestMethod.GET,RequestMethod.POST})
	public String shopSellerResvDetail(Model model, int shop_resv_seq) {
		shopSellerResvParam shopresv = shopService.getSellerResvDetail(shop_resv_seq);
		model.addAttribute("shopresv", shopresv);
		
		return "/smypage/shop/shopResvDetail";
	}
	
	@RequestMapping(value="shopResvUpdate.do",  method= {RequestMethod.GET,RequestMethod.POST})
	public String shopResvUpdate(Model model,int shop_resv_seq) {
		model.addAttribute("shop_resv_seq", shop_resv_seq);
		return "/shop/shopResvUpdate";
	}

	@ResponseBody
	@RequestMapping(value="shopResvUpdateAf.do",  method= {RequestMethod.GET,RequestMethod.POST})
	public String shopResvUpdate(Model model, shopResvDto resv) {
		String str = "";
		boolean b = shopService.shopResvUpdate(resv);
		
		if(b) {
			str="ok";
		}else {
			str ="no";
		}
		
		return str;
		
	}
	
	
	
	
}






























