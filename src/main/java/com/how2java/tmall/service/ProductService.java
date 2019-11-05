package com.how2java.tmall.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.how2java.tmall.dao.ProductDAO;
import com.how2java.tmall.pojo.Category;
import com.how2java.tmall.pojo.Product;
import com.how2java.tmall.pojo.User;
import com.how2java.tmall.util.Page4Navigator;

@Service
public class ProductService {

	@Autowired
	ProductDAO productDAO;
	@Autowired
	CategoryService categoryService;
	@Autowired
	ProductImageService productImageService;
	@Autowired
	OrderItemService orderItemService;
	@Autowired
	ReviewService reviewService;
	@Autowired
	UserService userService;

	public void add(Product bean) {
		productDAO.save(bean);
	}

	public void delete(int id) {
		productDAO.delete(id);
	}

	public Product get(int id) {
		return productDAO.findOne(id);
	}

	public void update(Product bean) {
		productDAO.save(bean);
	}

	public List<Product> findAll() {
		return productDAO.findAll();
	}

	public Page4Navigator<Product> list(int cid, int start, int size, int navigatePages) {
		Category category = categoryService.get(cid);
		Sort sort = new Sort(Sort.Direction.DESC, "id");
		Pageable pageable = new PageRequest(start, size, sort);
		Page<Product> pageFromJPA = productDAO.findByCategory(category, pageable);
		return new Page4Navigator<>(pageFromJPA, navigatePages);
	}

	// 不设置条件查询所有商品
	public Page4Navigator<Product> list(int start, int size, int navigatePages) {
		Sort sort = new Sort(Sort.Direction.DESC, "id");
		Pageable pageable = new PageRequest(start, size, sort);
		Page<Product> pageFromJPA = productDAO.findByOrderByMatchScoreDesc(pageable);
		return new Page4Navigator<>(pageFromJPA, navigatePages);
	}
	
	// 根据分类和用户查询专利商品
	public Page4Navigator<Product> list(int cid, int holdid, int start, int size, int navigatePages) {
		Category category = categoryService.get(cid);
		User user = userService.getById(holdid);
		Sort sort = new Sort(Sort.Direction.DESC, "id");
		Pageable pageable = new PageRequest(start, size, sort);
		Page<Product> pageFromJPA = productDAO.findByCategoryAndUser(category, user, pageable);
		return new Page4Navigator<>(pageFromJPA, navigatePages);
	}

	// 普通分类商品填充
	public void fill(List<Category> categorys) {
		for (Category category : categorys) {
			fill(category);
		}
	}

	public void fill(Category category) {
		List<Product> products = listByCategory(category);
		productImageService.setFirstProdutImages(products);
		category.setProducts(products);
	}

	// 特定要求商品填充
	public void specialFill(List<Category> categorys) {
		for (Category category : categorys) {
			specialFill(category);
		}
	}

	public void specialFill(Category category) {
		List<Product> products = listByCategoryOrderByScore(category);
		productImageService.setFirstProdutImages(products);
		category.setProducts(products);
	}

	public void fillByRow(List<Category> categorys) {
		int productNumberEachRow = 8;
		for (Category category : categorys) {
			List<Product> products = category.getProducts();
			List<List<Product>> productsByRow = new ArrayList<>();
			for (int i = 0; i < products.size(); i += productNumberEachRow) {
				int size = i + productNumberEachRow;
				size = size > products.size() ? products.size() : size;
				List<Product> productsOfEachRow = products.subList(i, size);
				productsByRow.add(productsOfEachRow);
			}
			category.setProductsByRow(productsByRow);
		}
	}

	// 根据id排序，获取产品列表
	public List<Product> listByCategory(Category category) {
		return productDAO.findByCategoryOrderById(category);
	}

	// 根据Score排序，获取产品列表
	public List<Product> listByCategoryOrderByScore(Category category) {
		return productDAO.findByCategoryOrderByScoreDesc(category);
	}

	public void setSaleAndReviewNumber(Product product) {
		int saleCount = orderItemService.getSaleCount(product);
		product.setSaleCount(saleCount);

		int reviewCount = reviewService.getCount(product);
		product.setReviewCount(reviewCount);

	}

	public void setSaleAndReviewNumber(List<Product> products) {
		for (Product product : products)
			setSaleAndReviewNumber(product);
	}

	public List<Product> search(String keyword, int start, int size) {
		Sort sort = new Sort(Sort.Direction.DESC, "id");
		Pageable pageable = new PageRequest(start, size, sort);
		List<Product> products = productDAO.findByNameLike("%" + keyword + "%", pageable);
		return products;
	}

}
