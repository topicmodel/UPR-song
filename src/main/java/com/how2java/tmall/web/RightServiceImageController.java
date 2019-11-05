package com.how2java.tmall.web;

import java.awt.image.BufferedImage;
import java.io.File;
import java.util.ArrayList;
import java.util.List;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.how2java.tmall.pojo.RightService;
import com.how2java.tmall.pojo.RightServiceImage;
import com.how2java.tmall.service.ProductImageService;
import com.how2java.tmall.service.RightServiceImageService;
import com.how2java.tmall.service.RightServiceService;
import com.how2java.tmall.util.ImageUtil;

@RestController
public class RightServiceImageController {
	@Autowired
	RightServiceImageService rightServiceImageService;
	@Autowired
	RightServiceService rightServiceService;

	// rightservices/1/rightServiceImages
	@GetMapping("rightservices/{rsid}/rightServiceImages")
	public List<RightServiceImage> list(@PathVariable("rsid") int rsid, @RequestParam("type") String type) {
		System.err.println("rsid:" + rsid + "," + "type:" + type);
		RightService rightService = rightServiceService.get(rsid);
		System.out.println(rightService.toString());
		if (RightServiceImageService.type_single.equals(type)) {
			List<RightServiceImage> singles = rightServiceImageService.listSingleRightServiceImages(rightService);
			// System.err.println("长度："+singles.size());
			return singles;
		}
		return new ArrayList<>();
	}

	// 增加知产服务图片
	@PostMapping("/rightServiceImages")
	public Object addRightServiceImage(@RequestParam("rsid") int rsid, @RequestParam("type") String type,
			MultipartFile image, HttpServletRequest request) {
		RightServiceImage bean = new RightServiceImage();
		RightService rigthService = rightServiceService.get(rsid);
		bean.setType(type);
		bean.setRightService(rigthService);
		rightServiceImageService.add(bean);

		String folder = "img/";
		if (RightServiceImageService.type_single.equals(bean.getType())) {
			folder += "rigthServiceSingle";
		}
		File imageFolder = new File(request.getServletContext().getRealPath(folder));
		File file = new File(imageFolder, bean.getId() + ".jpg");
		String fileName = file.getName();
		if (!file.getParentFile().exists()) {
			file.getParentFile().mkdirs();
		}
		try {
			image.transferTo(file);
			BufferedImage img = ImageUtil.change2jpg(file);
			ImageIO.write(img, "jpg", file);
		} catch (Exception e) {
			e.printStackTrace();
		}

		// 修剪主图
		if (ProductImageService.type_single.equals(bean.getType())) {
			String imageFolder_small = request.getServletContext().getRealPath("img/rightServiceSingle_small");
			String imageFolder_middle = request.getServletContext().getRealPath("img/rightServiceSingle_middle");
			File f_small = new File(imageFolder_small, fileName);
			File f_middle = new File(imageFolder_middle, fileName);
			f_small.getParentFile().mkdirs();
			f_middle.getParentFile().mkdirs();
			ImageUtil.resizeImage(file, 56, 56, f_small);
			ImageUtil.resizeImage(file, 217, 190, f_middle);
		}
		return bean;
	}

	// 删除图片
	@DeleteMapping("/rightServiceImages/{id}")
	public String delete(@PathVariable("id") int id, HttpServletRequest request) {
		RightServiceImage bean = rightServiceImageService.get(id);
		rightServiceImageService.delete(id);
		String folder = "img/";
		if (RightServiceImageService.type_single.equals(bean.getType())) {
			folder += "rightServiceSingle";
		} else {
			folder += "rightServiceDetail";
		}
		File imageFolder = new File(request.getServletContext().getRealPath(folder));
		File file = new File(imageFolder, bean.getId() + ".jpg");
		String fileName = file.getName();
		file.delete();

		if (RightServiceImageService.type_single.equals(bean.getType())) {
			String imageFolder_small = request.getServletContext().getRealPath("img/rightServiceSingle_small");
			String imageFolder_middle = request.getServletContext().getRealPath("img/rightServiceSingle_middle");
			File f_small = new File(imageFolder_small, fileName);
			File f_middle = new File(imageFolder_middle, fileName);
			f_small.delete();
			f_middle.delete();
		}
		return null;
	}
}
