package com.how2java.tmall.web;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.how2java.tmall.pojo.Product;
import com.how2java.tmall.pojo.ProductExplain;
import com.how2java.tmall.pojo.ProductImage;
import com.how2java.tmall.service.CategoryService;
import com.how2java.tmall.service.ProductExplainService;
import com.how2java.tmall.service.ProductImageService;
import com.how2java.tmall.service.ProductService;

@RestController
public class ProductExplainController {
	@Autowired
	ProductExplainService productExplainService;

	@Autowired
	ProductService productService;

	@Autowired
	CategoryService categoryService;

	@GetMapping("/products/{pid}/productExplain")
	public List<ProductExplain> list(
			@PathVariable("pid") int pid, 
			@RequestParam("type") String type) {
		System.out.println("id和type"+pid+","+type);
		Product product = productService.get(pid);
		if (ProductExplainService.type_single.equals(type)) {
			List<ProductExplain> abstracts = productExplainService.listProductAbstract(product);
			//System.err.println("说明资料:"+abstracts.get(0).toString());
			return abstracts;
		} else if (ProductExplainService.type_detail.equals(type)) {
			List<ProductExplain> rights = productExplainService.listProductRight(product);
			return rights;
		} else {
			return new ArrayList<>();
		}
	}

	@PostMapping("/productExplain")
	public Object add(@RequestParam("pid") int pid, @RequestParam("type") String type, MultipartFile file,
			HttpServletRequest request) throws Exception {
		System.out.println("上传文件！！！");
		ProductExplain bean = new ProductExplain();
		Product product = productService.get(pid);
		bean.setProduct(product);
		bean.setType(type);

		productExplainService.add(bean);
		String folder = "file/";
		if (ProductExplainService.type_single.equals(bean.getType())) {
			folder += "productAbstract";
		} else {
			folder += "productRight";
		}
		File imageFolder = new File(request.getServletContext().getRealPath(folder));
		File afile = new File(imageFolder,product.getId() + ".html");
		String fileName = afile.getName();
		System.out.println("上传文件名:"+fileName);
		if (!afile.getParentFile().exists())
			afile.getParentFile().mkdirs();
		try {
			file.transferTo(afile);
			System.out.println("上传说明书路径:" + imageFolder.getName() + fileName);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return bean;
	}
	
	@DeleteMapping("/productExplain/{id}")
	public String delete(@PathVariable("id") int id, HttpServletRequest request) {
		ProductExplain bean = productExplainService.get(id);
		Product product =  productService.get(bean.getProduct().getId());
		productExplainService.delete(id);
		String folder = "/file";
		if (ProductExplainService.type_single.equals(bean.getType())) {
			folder += "/productAbstract";
		} else {
			folder += "/productRight";
		}
			
		File imageFolder = new File(request.getServletContext().getRealPath(folder));
		File file = new File(imageFolder, product.getId() + ".html");
		//String fileName = file.getName();
		file.delete();
		return null;
	}
}
