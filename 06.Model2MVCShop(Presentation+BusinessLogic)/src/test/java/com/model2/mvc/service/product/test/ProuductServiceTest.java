package com.model2.mvc.service.product.test;

import java.util.List;
import java.util.Map;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.model2.mvc.common.Search;
import com.model2.mvc.service.domain.Product;
import com.model2.mvc.service.domain.User;
import com.model2.mvc.service.product.ProductService;



/*
 *	FileName :  UserServiceTest.java
 * ㅇ JUnit4 (Test Framework) 과 Spring Framework 통합 Test( Unit Test)
 * ㅇ Spring 은 JUnit 4를 위한 지원 클래스를 통해 스프링 기반 통합 테스트 코드를 작성 할 수 있다.
 * ㅇ @RunWith : Meta-data 를 통한 wiring(생성,DI) 할 객체 구현체 지정
 * ㅇ @ContextConfiguration : Meta-data location 지정
 * ㅇ @Test : 테스트 실행 소스 지정
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration	(locations = {	"classpath:config/context-common.xml",
									"classpath:config/context-aspect.xml",
									"classpath:config/context-mybatis.xml",
									"classpath:config/context-transaction.xml" })
public class ProuductServiceTest {

	//==>@RunWith,@ContextConfiguration 이용 Wiring, Test 할 instance DI
	@Autowired
	@Qualifier("productServiceImpl")
	private ProductService productService;

	//@Test
	public void testAddProduct() throws Exception {

		Product product = new Product();
		product.setProdName("아아아");
		product.setProdDetail("아아");
		product.setManuDate("111");
		product.setPrice(Integer.parseInt("333"));
		product.setFileName("아아아");

		productService.addProduct(product);

		//==> console 확인
		System.out.println(product);
		
		//==> API 확인
		Assert.assertEquals("아아아", product.getProdName());
		Assert.assertEquals("아아", product.getProdDetail());
		Assert.assertEquals("111", product.getManuDate());
		Assert.assertEquals(333, product.getPrice());
		Assert.assertEquals("아아아", product.getFileName());
	}

	//@Test	
	public void testGetProduct() throws Exception {
		
	Product product = new Product();
	product = productService.getProduct(10024);
	
//	product.setProdName("아아아");
//	product.setProdDetail("아아");
//	product.setManuDate("111");
//	product.setPrice(Integer.parseInt("333"));
//	product.setFileName("아아아");
//		
//	//==> console 확인
//	System.out.println(product);
	
	//==> API 확인
	Assert.assertEquals("아아아", product.getProdName());
	Assert.assertEquals("아아", product.getProdDetail());
	Assert.assertEquals("111", product.getManuDate());
	Assert.assertEquals(333, product.getPrice());
	Assert.assertEquals("아아아", product.getFileName());
	}

//@Test	
public void testUpdateProduct() throws Exception{
	 
	Product product = productService.getProduct(10024);
	
	Assert.assertNotNull(product);
	
	Assert.assertEquals("아아아", product.getProdName());
	Assert.assertEquals("아아", product.getProdDetail());
	Assert.assertEquals("111", product.getManuDate());
	Assert.assertEquals(333, product.getPrice());
	Assert.assertEquals("아아아", product.getFileName());

	product.setProdName("야야야");
	product.setProdDetail("오오오오오오");
	product.setManuDate("3333");
	product.setPrice(44444);
	product.setFileName("라라라라");
	
	productService.updateProduct(product);
	product = productService.getProduct(10024);
	Assert.assertNotNull(product);
	
	System.out.println(product);
		
	//==> API 확인
	Assert.assertEquals("야야야", product.getProdName());
	Assert.assertEquals("오오오오오오", product.getProdDetail());
	Assert.assertEquals("3333", product.getManuDate());
	Assert.assertEquals(44444, product.getPrice());
	Assert.assertEquals("라라라라", product.getFileName());
 }
 
	//@Test
	 public void testGetProductListAll() throws Exception{
		System.out.println("시작");
	 	Search search = new Search();
	 	search.setCurrentPage(1);
	 	search.setPageSize(3);
	 	Map<String,Object> map = productService.getProductList(search);
	 	System.out.println("여기");
	 	List<Object> list = (List<Object>)map.get("list");
	 	Assert.assertEquals(3, list.size());
	 	
		//==> console 확인
	 	System.out.println(list);
	 	
	 	Integer totalCount = (Integer)map.get("totalCount");
	 	System.out.println(totalCount);
	 	
	 	System.out.println("=======================================");
	 	
	 	search.setCurrentPage(1);
	 	search.setPageSize(3);
	 	search.setSearchCondition("0");
	 	search.setSearchKeyword("");
	 	map = productService.getProductList(search);
	 	
	 	list = (List<Object>)map.get("list");
	 	Assert.assertEquals(3, list.size());
	 	
	 	//==> console 확인
	 	System.out.println(list);
	 	
	 	totalCount = (Integer)map.get("totalCount");
	 	System.out.println(totalCount);
	 }
	 
	 //@Test
	 public void testGetProductListByProductNo() throws Exception{
		 
		 	Search search = new Search();
		 	search.setCurrentPage(1);
		 	search.setPageSize(3);
		 	search.setSearchCondition("0");
		 	search.setSearchKeyword("10000");
		 	Map<String,Object> map = productService.getProductList(search);
		 	
		 	List<Object> list = (List<Object>)map.get("list");
		 	Assert.assertEquals(1, list.size());
		 	
			//==> console 확인
		 	System.out.println(list);
		 	
		 	Integer totalCount = (Integer)map.get("totalCount");
		 	System.out.println(totalCount);
		 	
		 	System.out.println("=======================================");
		 	
		 	search.setSearchCondition("1");
		 	search.setSearchKeyword(""+System.currentTimeMillis());
		 	map = productService.getProductList(search);
		 	
		 	list = (List<Object>)map.get("list");
		 	Assert.assertEquals(0, list.size());
		 	
			//==> console 확인
		 	System.out.println(list);
		 	
		 	totalCount = (Integer)map.get("totalCount");
		 	System.out.println(totalCount);
		 }	 
	 
	 
	 
	// @Test
	 public void testGetProductListByProductName() throws Exception{
		 
		 	Search search = new Search();
		 	search.setCurrentPage(1);
		 	search.setPageSize(3);
		 	search.setSearchCondition("1");
		 	search.setSearchKeyword("하리보");
		 	Map<String,Object> map = productService.getProductList(search);
		 	
		 	List<Object> list = (List<Object>)map.get("list");
		 	Assert.assertEquals(1, list.size());
		 	
			//==> console 확인
		 	System.out.println(list);
		 	
		 	Integer totalCount = (Integer)map.get("totalCount");
		 	System.out.println(totalCount);
		 	
		 	System.out.println("=======================================");
		 	
		 	search.setSearchCondition("1");
		 	search.setSearchKeyword(""+System.currentTimeMillis());
		 	map = productService.getProductList(search);
		 	
		 	list = (List<Object>)map.get("list");
		 	Assert.assertEquals(0, list.size());
		 	
			//==> console 확인
		 	System.out.println(list);
		 	
		 	totalCount = (Integer)map.get("totalCount");
		 	System.out.println(totalCount);
		 }	 
	 @Test
	 public void testGetProductListByProductPrice() throws Exception{
		 
		 	Search search = new Search();
		 	search.setCurrentPage(1);
		 	search.setPageSize(3);
		 	search.setSearchCondition("2");
		 	search.setSearchKeyword("111");
		 	Map<String,Object> map = productService.getProductList(search);
		 	
		 	List<Object> list = (List<Object>)map.get("list");
		 	Assert.assertEquals(3, list.size());
		 	
			//==> console 확인
		 	System.out.println(list);
		 	
		 	Integer totalCount = (Integer)map.get("totalCount");
		 	System.out.println(totalCount);
		 	
		 	System.out.println("=======================================");
		 	
		 	search.setSearchCondition("1");
		 	search.setSearchKeyword(""+System.currentTimeMillis());
		 	map = productService.getProductList(search);
		 	
		 	list = (List<Object>)map.get("list");
		 	Assert.assertEquals(0, list.size());
		 	
			//==> console 확인
		 	System.out.println(list);
		 	
		 	totalCount = (Integer)map.get("totalCount");
		 	System.out.println(totalCount);
		 }	 
	 
}