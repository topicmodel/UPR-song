package com.how2java.tmall.web;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.how2java.tmall.pojo.Agency;
import com.how2java.tmall.pojo.PatentBroker;
import com.how2java.tmall.service.AgencyService;
import com.how2java.tmall.service.PatentBrokerService;
import com.how2java.tmall.util.ImageUtil;
import com.how2java.tmall.util.Page4Navigator;

@RestController
public class PatentBrokerController {

	@Autowired
	PatentBrokerService patentBrokerService;
	@Autowired
	AgencyService agencyService; 
	
	/**
	 * 根据agencyid查询专利经纪人列表
	 * @param agencyid 代理商id
	 * @param start  开始位置
	 * @param size	  每页显示多少数据
	 * @return  专利经纪人的分页数据
	 */
	@GetMapping("/agency/{agencyid}/brokers")
	public Page4Navigator<PatentBroker> list(@PathVariable("agencyid") int agencyid,
			@RequestParam(name = "start", defaultValue = "0") int start,
			@RequestParam(name = "size", defaultValue = "5") int size) {
		System.err.println("代理商id:"+agencyid);
		start = start < 0 ? 0 : start;
		Page4Navigator<PatentBroker> page = patentBrokerService.list(agencyid, start, size, 5);
		//System.err.println(page.getContent().get(0).toString());
		return page;
	}
	//patentdomain/"+domainid+"/"+this.uri+"?start="+start
	/**
	 * 根据domainid获取专利经纪人列表
	 * @param domainid 专业领域id
	 * @param start
	 * @param size
	 * @return
	 */
	@GetMapping("/patentdomain/{domainid}/forebroker")
	public Page4Navigator<PatentBroker> listByDomain(
			@PathVariable("domainid") int domainid,
			@RequestParam(name="start",defaultValue="0") int start,
			@RequestParam(name="size",defaultValue="5") int size){
		start=start<0?0:start;
		Page4Navigator<PatentBroker> page = patentBrokerService.listByDomain(domainid, start, size, 5);
		//System.err.println(page.getContent().get(0).toString());
		return page;
	}
	//增加专利经纪人
	@PostMapping("/brokers")
	public Object add(
			PatentBroker bean,
			MultipartFile image, HttpServletRequest request) throws IOException {
		System.err.println("patentBroker:"+bean.toString());		
		patentBrokerService.add(bean);
		saveOrUpdateImageFile(bean, image, request);
		return bean;
	}
	
	//删除专利经纪人
	
	@DeleteMapping("/brokers/{id}")
	public Object delete(@PathVariable("id") int id,HttpServletRequest request){
		patentBrokerService.delete(id);
		File imageFolder=new File(request.getServletContext().getRealPath("img/broker"));
		File file =new File(imageFolder,id+".jpg");
		file.delete();
		return null;
	}
	
	//添加图片
	private void saveOrUpdateImageFile(PatentBroker patentBroker, MultipartFile image, HttpServletRequest request)
			throws IOException {
		File imageFolder = new File(request.getServletContext().getRealPath("img/broker"));
		File file = new File(imageFolder, patentBroker.getId() + ".jpg");
		if (!file.getParentFile().exists()) {
			file.getParentFile().mkdirs();
		}
		image.transferTo(file);
		BufferedImage img = ImageUtil.change2jpg(file);
		ImageIO.write(img, "jpg", file);
	}
	
}
