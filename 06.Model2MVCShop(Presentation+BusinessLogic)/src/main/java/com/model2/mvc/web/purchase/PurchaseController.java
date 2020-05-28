package com.model2.mvc.web.purchase;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.model2.mvc.common.Page;
import com.model2.mvc.common.Search;
import com.model2.mvc.service.domain.Product;
import com.model2.mvc.service.domain.Purchase;
import com.model2.mvc.service.domain.User;
import com.model2.mvc.service.product.ProductService;
import com.model2.mvc.service.product.impl.ProductServiceImpl;
import com.model2.mvc.service.purchase.PurchaseService;
import com.model2.mvc.service.user.UserService;
import com.model2.mvc.service.user.impl.UserServiceImpl;

@Controller
public class PurchaseController {

	@Autowired @Qualifier("purchaseServiceImpl")
	private PurchaseService purchaseService;
	@Autowired @Qualifier("productServiceImpl")
	private ProductService productService;
	
	public PurchaseController(){
		System.out.println(this.getClass());
	}
	
	@Value("#{commonProperties['pageUnit']}")
	int pageUnit;
	
	@Value("#{commonProperties['pageSize']}")
	int pageSize;
	
	
	@RequestMapping("/addPurchaseView.do")
	public String addPurchaseView(@ModelAttribute("purchase") Purchase purchase, @RequestParam("prod_no") int prodNo, HttpServletRequest request, HttpSession session, Model model) throws Exception {
		System.out.println("시작");
		

		System.out.println("prodNo"+prodNo);
		
		purchase.setBuyer((User)session.getAttribute("user"));
		purchase.setPurchaseProd(productService.getProduct(prodNo));
		
		model.addAttribute("purchase", purchase);
		System.out.println("purchase"+purchase);
		System.out.println("/addPurchaseView.do");
		
		return "forward:/purchase/addPurchaseView.jsp";
	}
	
	@RequestMapping("/addPurchase.do")
	public String addPurchase(@ModelAttribute("purchase") Purchase purchase, HttpServletRequest request, @RequestParam("prod_no") int prodNo, HttpSession session, Model model) throws Exception {
	
		System.out.println("/addPurchase.do");
			
		purchase.setPurchaseProd(productService.getProduct(prodNo));
		purchase.setBuyer((User)session.getAttribute("user"));
		purchase.setDivyAddr(request.getParameter("receiverAddr"));
		purchase.setDivyRequest(request.getParameter("receiverRequest"));
		purchase.setDivyDate(request.getParameter("receiverDate"));	
	
		purchaseService.addPurchase(purchase);
		
		model.addAttribute("purchase", purchase);
		
		return "forward:/purchase/addPurchase.jsp";	
	}
	
	@RequestMapping("/getPurchase.do")
	public String getPurchase(@RequestParam("tranNo") int tranNo, HttpServletRequest request, Model model) throws Exception {

	Purchase purchase = purchaseService.getPurchase(tranNo);
	
	model.addAttribute("purchase", purchase);
	
	return "forward:/purchase/getPurchase.jsp";
	}
	
	@RequestMapping("/updatePurchaseView.do")
	public String updatePurchaseView( @RequestParam("tranNo") int tranNo , Model model ) throws Exception{
		System.out.println("시작");
		System.out.println("/updatePurchaseView.do");
		//Business Logic
		Purchase purchase = purchaseService.getPurchase(tranNo);
		// Model 과 View 연결
		model.addAttribute("purchase", purchase);
		System.out.println("purchase"+purchase);
		return "forward:/purchase/updatePurchaseView.jsp";
	}
	
	@RequestMapping("/updatePurchase.do")
	public String updatePurchase( @RequestParam("tranNo") int tranNo, HttpServletRequest request,  Model model, HttpSession session) throws Exception{
	
		System.out.println("/updatePurchase.do");
		
	    Purchase purchase = purchaseService.getPurchase(tranNo);
		purchase.setBuyer((User)session.getAttribute("user"));
		purchase.setTranNo(Integer.parseInt(request.getParameter("tranNo")));
		purchase.setPaymentOption(request.getParameter("paymentOption"));
		purchase.setReceiverName(request.getParameter("receiverName"));
		purchase.setReceiverPhone(request.getParameter("receiverPhone"));
		purchase.setDivyAddr(request.getParameter("receiverAddr"));
		purchase.setDivyRequest(request.getParameter("receiverRequest"));
		purchase.setDivyDate(request.getParameter("divyDate"));

		purchaseService.updatePurchase(purchase);
		
		model.addAttribute("purchase", purchase);
		

		return "forward:/purchase/getPurchase.jsp";
	}
	
	
	
	@RequestMapping("/listPurchase.do")
	public String listPurchase( @ModelAttribute("search") Search search , Model model , HttpServletRequest request, HttpSession session) throws Exception{
		
		System.out.println("/listPurchase.do");
		
		if(search.getCurrentPage() ==0 ){
			search.setCurrentPage(1);
		}
		System.out.println("search"+search);
		search.setPageSize(pageSize);
		
		// Business logic 수행
		Map<String , Object> map=purchaseService.getPurchaseList(search, ((User)session.getAttribute("user")).getUserId());
		
		Page resultPage = new Page( search.getCurrentPage(), ((Integer)map.get("totalCount")).intValue(), pageUnit, pageSize);
		System.out.println(resultPage);
		
		// Model 과 View 연결
		model.addAttribute("list", map.get("list"));
		model.addAttribute("resultPage", resultPage);
		model.addAttribute("search", search);
		
		return "forward:/purchase/listPurchase.jsp";
	}
	@RequestMapping("/updateTranCode.do")
	public String updateTranCode(@ModelAttribute("purchase") Purchase purchase, @RequestParam("tranNo") int tranNo, HttpServletRequest request, Model model) throws Exception{
		
		purchase.setTranNo(Integer.parseInt(request.getParameter("tranNo")));
		purchase.setTranCode(request.getParameter("tranCode"));
		
		purchaseService.updateTranCode(purchase);
		
		model.addAttribute("purchase", purchase);
		return "forward:/listPurchase.do";
	}
	@RequestMapping("/updateTranCodeByProd.do")
	public String UpdateTranCodeByProd (@ModelAttribute("purchase") Purchase purchase, @ModelAttribute("product") Product product, @RequestParam("prodNo") int prodNo,HttpServletRequest request, Model model ) throws Exception {
		System.out.println("시작");
		purchase.setTranCode(request.getParameter("tranCode"));
		purchase.setPurchaseProd(product);
		
		System.out.println("trancode"+purchase);
		purchaseService.updateTranCode(purchase);
		model.addAttribute("purchase", purchase);
		
		System.out.println("trancode"+purchase);
		return "forward:/listProduct.do?menu=manage";
	}
}
