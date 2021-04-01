package cd.com.a.daoImpl;

import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import cd.com.a.dao.ShopDao;
import cd.com.a.model.adminShopParam;
import cd.com.a.model.shopDesignerDto;
import cd.com.a.model.shopDto;
import cd.com.a.model.shopListParam;
import cd.com.a.model.shopPagingParam;
import cd.com.a.model.shopResvDto;
import cd.com.a.model.shopSellerPagingParam;
import cd.com.a.model.shopSellerResvParam;
import cd.com.a.model.shopShowResvParam;

@Repository
public class ShopDaoImpl implements ShopDao {
	@Autowired
	SqlSession sqlSession;
	
	String ns ="Shop.";


	
	@Override
	public List<shopDto> getShopList(shopListParam param) {
		List<shopDto> list = sqlSession.selectList(ns +"getShopList", param);
		return list;
	}

	@Override
	public List<shopDto> getSellerShopList(int mem_seq) {
		List<shopDto> list = sqlSession.selectList(ns +"getSellerShopList", mem_seq);
		return list;
	}
	
	@Override
	public List<shopDesignerDto> getDesigner(int shop_seq) {
		List<shopDesignerDto> deslist = sqlSession.selectList(ns+"getDesigner", shop_seq);
		return deslist;
	}
	
	@Override
	public List<shopDesignerDto> getDesignerAll(int shop_seq) {
		List<shopDesignerDto> deslist = sqlSession.selectList(ns+"getDesignerAll", shop_seq);
		return deslist;
	}

	@Override
	public List<String> getResv(shopResvDto resvDto) {
		List<String> list = sqlSession.selectList(ns+"getShopResv", resvDto);
		return list;
	}
	
	@Override
	public shopDesignerDto getDesignerInfo(int design_seq) {
		shopDesignerDto dto = sqlSession.selectOne(ns+"getDesignerInfo2", design_seq);
		return dto;
	}

	@Override
	public shopDto getShopDetail(int shop_seq) {
		shopDto dto = sqlSession.selectOne(ns+"getShopDetail", shop_seq);
		return dto;
	}


	@Override
	public shopDesignerDto getDesignerInfo(int design_seq) {
		shopDesignerDto dto = sqlSession.selectOne(ns+"getDesignerInfo2", design_seq);
		return dto;
	}

	@Override
	public int resvShop(shopResvDto shopResv) {
		int n = sqlSession.insert(ns+"resvShop", shopResv);
		System.out.println("============shopDao:"+shopResv.getShop_resv_seq());
		int shop_resv_seq = 0;
		if(n>0) {
			shop_resv_seq = shopResv.getShop_resv_seq();
		}
		return shop_resv_seq;
	}

	@Override
	public shopResvDto getShopResv(int shop_resv_seq) {
		return sqlSession.selectOne(ns+"getShopResvInfo", shop_resv_seq);
	}

	@Override
	public List<shopShowResvParam> showShopResv(shopPagingParam param) {
		List<shopShowResvParam> list = sqlSession.selectList(ns+"showShopResv", param);
		return list;
	}

	@Override
	public boolean cancelShopResv(shopResvDto shopresv) {
		int n = sqlSession.update(ns+"cancelShopResv", shopresv);
		return n>0?true:false;
	}

	@Override
	public int shopCalcelTimeCheck(shopResvDto shopresv) {
		return sqlSession.selectOne(ns+"shopCalcelTimeCheck", shopresv);
	}

	@Override
	public int getShopResvCount(int mem_seq) {
		return sqlSession.selectOne(ns+"getShopResvCount", mem_seq);
	}

	@Override
	public int getShopCount() {
		return sqlSession.selectOne(ns+"getShopCount");
	}

	

	@Override
	public int getSellerResvCount(shopSellerPagingParam param) {
		return sqlSession.selectOne(ns+"getSellerResvCount", param);
	}

	@Override
	public List<shopSellerResvParam> getSellerShopResvList(shopSellerPagingParam param) {
		return sqlSession.selectList(ns+"getSellerShopResvList", param);
	}

	@Override
	public shopSellerResvParam getSellerResvDetail(int shop_resv_seq) {
		return sqlSession.selectOne(ns+"getSellerShopResvDetail", shop_resv_seq);
	}

	@Override
	public boolean shopResvUpdate(shopResvDto resv) {
		int n = sqlSession.update(ns+"shopResvUpdate", resv);
		return n>0?true:false; 
	}
	


	@Override
	public List<shopShowResvParam> shopShopCancelResv(shopPagingParam param) {
		List<shopShowResvParam> list = sqlSession.selectList(ns+"shopResvCancelList", param);
		return list;
	}

	@Override
	public int getShopCancelResvCount(int mem_seq) {
		return sqlSession.selectOne(ns+"getShopCancelResvCount", mem_seq);
	}
	


}
