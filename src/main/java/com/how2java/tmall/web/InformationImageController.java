package com.how2java.tmall.web;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

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

import com.how2java.tmall.pojo.Information;
import com.how2java.tmall.pojo.InformationImage;
import com.how2java.tmall.service.InfoCategoryService;
import com.how2java.tmall.service.InformationImageService;
import com.how2java.tmall.service.InformationService;
import com.how2java.tmall.util.ImageUtil;

@RestController
public class InformationImageController {

	@Autowired
	InfoCategoryService infoCategoryService;
	@Autowired
	InformationService informationService;
	@Autowired
	InformationImageService informationImageService;

	/**
	 * 获取图片列表
	 * 
	 * @param type
	 * @param id
	 * @return
	 */
	@GetMapping("informations/{infoid}/informationImages")
	public List<InformationImage> list(@RequestParam("type") String type, @PathVariable("infoid") int id) {

		Information information = informationService.get(id);

		if (InformationImageService.type_single.equals(type)) {
			List<InformationImage> singles = informationImageService.listSingleInformationImage(information);
			return singles;
		} else if (InformationImageService.type_detail.equals(type)) {
			List<InformationImage> details = informationImageService.listDetailInformationImage(information);
			return details;
		} else {
			return new ArrayList<>();
		}
	}

	@PostMapping("informationImages")
	public Object add(@RequestParam("infoid") int infoid, @RequestParam("type") String type, MultipartFile image,
			HttpServletRequest request) {

		InformationImage informationImage = new InformationImage();
		Information information = informationService.get(infoid);
		informationImage.setInformation(information);
		informationImage.setType(type);

		informationImageService.add(informationImage);
		String folder = "img/";
		if (InformationImageService.type_single.equals(type)) {
			folder += "informationSingle";
		} else {
			folder += "informationDetail";
		}
		File imageFolder = new File(request.getServletContext().getRealPath(folder));
		File file = new File(imageFolder, informationImage.getId() + ".jpg");
		String fileName = file.getName();
		if (!file.getParentFile().exists()) {
			file.getParentFile().mkdirs();
		}
		try {
			image.transferTo(file);
			BufferedImage img = ImageUtil.change2jpg(file);
			ImageIO.write(img, "jpg", file);
		} catch (IOException e) {
			e.printStackTrace();
		}
		if (InformationImageService.type_single.equals(informationImage.getType())) {
			String imageFolder_small = request.getServletContext().getRealPath("img/informationSingle_small");
			String imageFolder_middle = request.getServletContext().getRealPath("img/informationSingle_middle");
			File f_small = new File(imageFolder_small, fileName);
			File f_middle = new File(imageFolder_middle, fileName);
			f_small.getParentFile().mkdirs();
			f_middle.getParentFile().mkdirs();
			ImageUtil.resizeImage(file, 56, 56, f_small);
			ImageUtil.resizeImage(file, 217, 190, f_middle);
		}

		return informationImage;
	}

	@DeleteMapping("/informationImages/{infoid}")
	public String delete(@PathVariable("infoid") int infoid, HttpServletRequest request) {
		InformationImage bean = informationImageService.get(infoid);
		informationImageService.delete(infoid);
		String folder = "img/";
		if (InformationImageService.type_single.equals(bean.getType())) {
			folder += "informationSingle";
		} else {
			folder += "informationDetail";
		}
		File imageFolder = new File(request.getServletContext().getRealPath(folder));
		File file = new File(imageFolder, bean.getId() + ".jpg");
		String fileName = file.getName();
		file.delete();
		if (InformationImageService.type_single.equals(bean.getType())) {
			String imageFolder_small = request.getServletContext().getRealPath("img/informationSingle_small");
			String imageFolder_middle = request.getServletContext().getRealPath("img/informationSingle_middle");
			File f_small = new File(imageFolder_small, fileName);
			File f_middle = new File(imageFolder_middle, fileName);
			f_small.delete();
			f_middle.delete();
		}
		return null;
	}
}
