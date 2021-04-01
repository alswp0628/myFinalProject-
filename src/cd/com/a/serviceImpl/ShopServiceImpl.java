package cd.com.a.serviceImpl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
import cd.com.a.service.ShopService;

@Service
public class ShopServiceImpl implements ShopService {
	@Autowired
	ShopDao shopDao;

	
	@Override
	public List<shopDto> getShopList(shopListParam param) {
		return shopDao.getShopList(param);
	}

	@Override
	public List<shopDto> getSellerShopList(int mem_seq) {
		return shopDao.getSellerShopList(mem_seq);
	}
	
	@Override
	public List<shopDesignerDto> getDesigner(int shop_seq) {
		return shopDao.getDesigner(shop_seq);
	}
	
	@Override
	public List<shopDesignerDto> getDesignerAll(int shop_seq) {
		return shopDao.getDesignerAll(shop_seq);
	}

	@Override
	public List<String> getResv(shopResvDto resvDto) {
		return shopDao.getResv(resvDto);
	}

	@Override
	public shopDto getShopDetail(int shop_seq) {
		return shopDao.getShopDetail(shop_seq);
	}


	@Override
	public shopDesignerDto getDesignerInfo(int design_seq) {
		return shopDao.getDesignerInfo(design_seq);
	}

	@Override
	public int checkDesign(shopDesignerDto designer) {
		return shopDao.checkDesign(designer);
	}



	@Override
	public int resvShop(shopResvDto shopResv) {
		return shopDao.resvShop(shopResv);
	}

	@Override
	public shopResvDto getShopResv(int shop_resv_seq) {
		return shopDao.getShopResv(shop_resv_seq);
	}

	@Override
	public List<shopShowResvParam> showShopResv(shopPagingParam param) {
		return shopDao.showShopResv(param);
	}

	@Override
	public boolean cancelShopResv(shopResvDto shopresv) {
		return shopDao.cancelShopResv(shopresv);
	}

	@Override
	public int shopCalcelTimeCheck(shopResvDto shopresv) {
		return shopDao.shopCalcelTimeCheck(shopresv);
	}

	@Override
	public int getShopResvCount(int mem_seq) {
		return shopDao.getShopResvCount(mem_seq);
	}

	@Override
	public int getShopCount() {
		return shopDao.getShopCount();
	}

	@Override
	public List<shopSellerResvParam> getSellerShopResvList(shopSellerPagingParam param) {
		return shopDao.getSellerShopResvList(param);
	}

	@Override
	public int getSellerResvCount(shopSellerPagingParam param) {
		return shopDao.getSellerResvCount(param);
	}

	@Override
	public shopSellerResvParam getSellerResvDetail(int shop_resv_seq) {
		return shopDao.getSellerResvDetail(shop_resv_seq);
	}

	@Override
	public boolean shopResvUpdate(shopResvDto resv) {
		return shopDao.shopResvUpdate(resv);
	}
	
	
	@Override
	public List<shopShowResvParam> shopShopCancelResv(shopPagingParam param) {
		return shopDao.shopShopCancelResv(param);
	}

	@Override
	public int getShopCancelResvCount(int mem_seq) {
		return shopDao.getShopCancelResvCount(mem_seq);
	}

}
