package com.how2java.tmall.web;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class AdminPageController {
	@GetMapping(value="/admin")
	public String admin(){
		return "redirect:admin_patent_list";
	}

	@GetMapping(value="/admin_patent_list")
	public String listPatent(){
		return "admin/listPatent";
	}

	@GetMapping(value="/admin_patent_edit")
	public String editPatent(){
		return "admin/editPatent";
	}
	@GetMapping(value="/admin_user_list")
	public String listUser(){
		return "admin/listUser";

	}
	@GetMapping(value="/admin_inventor_list")
	public String listInventor(){
		return "admin/listInventor";

	}
	@GetMapping(value="/admin_inventor_edit")
	public String editInventor(){
		return "admin/editInventor";
	}

	@GetMapping(value="/admin_infocategory_list")
	public String listInfoCategory(){
		return "admin/listInfoCategory";
	}

	@GetMapping(value="/admin_category_edit")
	public String editCategory(){
		return "admin/editCategory";

	}

	@GetMapping(value="/admin_order_list")
	public String listOrder(){
		return "admin/listOrder";

	}

	@GetMapping(value="/admin_product_list")
	public String listProduct(){
		return "admin/listProduct";

	}
	
	@GetMapping(value="/admin_information_list")
	public String listInformation(){
		return "admin/listInformation";

	}
	
	@GetMapping(value="/admin_informationImage_list")
	public String listInformationImage(){
		return "admin/listInformationImage";
	}
	
	@GetMapping(value="/admin_product_edit")
	public String editProduct(){
		return "admin/editProduct";

	}
	@GetMapping(value="/admin_productImage_list")
	public String listProductImage(){
		return "admin/listProductImage";

	}

	@GetMapping(value="/admin_property_list")
	public String listProperty(){
		return "admin/listProperty";

	}

	@GetMapping(value="/admin_property_edit")
	public String editProperty(){
		return "admin/editProperty";

	}

	@GetMapping(value="/admin_propertyValue_edit")
	public String editPropertyValue(){
		return "admin/editPropertyValue";

	}


	
	@GetMapping(value="/admin_assess_list")
	public String listCategoryAssess(){
		return "admin/listCategoryAssess";
	}
	
	@GetMapping(value="/admin_productassess_list")
	public String listProductAssess(){
		return "admin/listProductAssess";
	}
	
	@GetMapping(value="/admin_productAssess_edit")
	public String editProductAssess(){
		return "admin/editProductAssess";

	}
	@GetMapping(value="/admin_agent_list")
	public String listAgent(){
		return "admin/listAgent";
	}
	
	//专利经纪人列表
	@GetMapping(value="/admin_patentbroker_list")
	public String listPatentBroker(){
		return "admin/listPatentBroker";
	}
	
	@GetMapping(value="/admin_service_list")
	public String listService(){
		return "admin/listRightService";
	}
	//知产服务产品列表
	@GetMapping(value="/admin_rightProduct_list")
	public String listServiceProduct(){
		return "admin/listRightServiceProduct";
	}
	
	@GetMapping(value="/admin_serviceCategory_edit")
	public String editServiceCategory(){
		return "admin/editServiceCategory";
	}
	
	@GetMapping(value="/admin_rightServiceImage_list")
	public String listRightServiceImage(){
		return "admin/listRightServiceImage";
	}
}
