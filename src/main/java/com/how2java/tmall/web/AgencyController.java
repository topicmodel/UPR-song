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
import com.how2java.tmall.service.AgencyService;
import com.how2java.tmall.util.ImageUtil;
import com.how2java.tmall.util.Page4Navigator;

@RestController
public class AgencyController {

	@Autowired
	AgencyService agencyService;

	@GetMapping("/agents")
	public Page4Navigator<Agency> list(@RequestParam(value = "start", defaultValue = "0") int start,
			@RequestParam(value = "size", defaultValue = "5") int size) {
		start = start < 0 ? 0 : start;
		Page4Navigator<Agency> page = agencyService.list(start, size, 5);
		return page;
	}

	// 增加公司
	@PostMapping("/agents")
	public Object add(Agency agency, MultipartFile image, HttpServletRequest request) throws IOException {
		agencyService.add(agency);
		saveOrUpdateImageFile(agency, image, request);
		return agency;
	}
	
	//删除公司
	@DeleteMapping("/agents/{id}")
	public Object delete(
			@PathVariable("id") int id,
			HttpServletRequest request){
		agencyService.delete(id);
		File imageFolder = new File(request.getServletContext().getRealPath("img/agent"));
		File file = new File(imageFolder,id+".jpg");
		file.delete();
		return null;
	}
	
	private void saveOrUpdateImageFile(Agency agency, MultipartFile image, HttpServletRequest request)
			throws IOException {
		File imageFolder = new File(request.getServletContext().getRealPath("img/agent"));
		File file = new File(imageFolder, agency.getId() + ".jpg");
		if (!file.getParentFile().exists()) {
			file.getParentFile().mkdirs();
		}
		image.transferTo(file);
		BufferedImage img = ImageUtil.change2jpg(file);
		ImageIO.write(img, "jpg", file);
	}
	

	
}
