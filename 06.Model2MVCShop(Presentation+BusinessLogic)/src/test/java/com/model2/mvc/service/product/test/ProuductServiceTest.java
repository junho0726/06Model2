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
 * �� JUnit4 (Test Framework) �� Spring Framework ���� Test( Unit Test)
 * �� Spring �� JUnit 4�� ���� ���� Ŭ������ ���� ������ ��� ���� �׽�Ʈ �ڵ带 �ۼ� �� �� �ִ�.
 * �� @RunWith : Meta-data �� ���� wiring(����,DI) �� ��ü ����ü ����
 * �� @ContextConfiguration : Meta-data location ����
 * �� @Test : �׽�Ʈ ���� �ҽ� ����
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration	(locations = {	"classpath:config/context-common.xml",
									"classpath:config/context-aspect.xml",
									"classpath:config/context-mybatis.xml",
									"classpath:config/context-transaction.xml" })
public class ProuductServiceTest {

	//==>@RunWith,@ContextConfiguration �̿� Wiring, Test �� instance DI
	@Autowired
	@Qualifier("productServiceImpl")
	private ProductService productService;

	//@Test
	public void testAddProduct() throws Exception {

		Product product = new Product();
		product.setProdName("�ƾƾ�");
		product.setProdDetail("�ƾ�");
		product.setManuDate("111");
		product.setPrice(Integer.parseInt("333"));
		product.setFileName("�ƾƾ�");

		productService.addProduct(product);

		//==> console Ȯ��
		System.out.println(product);
		
		//==> API Ȯ��
		Assert.assertEquals("�ƾƾ�", product.getProdName());
		Assert.assertEquals("�ƾ�", product.getProdDetail());
		Assert.assertEquals("111", product.getManuDate());
		Assert.assertEquals(333, product.getPrice());
		Assert.assertEquals("�ƾƾ�", product.getFileName());
	}

	//@Test	
	public void testGetProduct() throws Exception {
		
	Product product = new Product();
	product = productService.getProduct(10024);
	
//	product.setProdName("�ƾƾ�");
//	product.setProdDetail("�ƾ�");
//	product.setManuDate("111");
//	product.setPrice(Integer.parseInt("333"));
//	product.setFileName("�ƾƾ�");
//		
//	//==> console Ȯ��
//	System.out.println(product);
	
	//==> API Ȯ��
	Assert.assertEquals("�ƾƾ�", product.getProdName());
	Assert.assertEquals("�ƾ�", product.getProdDetail());
	Assert.assertEquals("111", product.getManuDate());
	Assert.assertEquals(333, product.getPrice());
	Assert.assertEquals("�ƾƾ�", product.getFileName());
	}

//@Test	
public void testUpdateProduct() throws Exception{
	 
	Product product = productService.getProduct(10024);
	
	Assert.assertNotNull(product);
	
	Assert.assertEquals("�ƾƾ�", product.getProdName());
	Assert.assertEquals("�ƾ�", product.getProdDetail());
	Assert.assertEquals("111", product.getManuDate());
	Assert.assertEquals(333, product.getPrice());
	Assert.assertEquals("�ƾƾ�", product.getFileName());

	product.setProdName("�߾߾�");
	product.setProdDetail("������������");
	product.setManuDate("3333");
	product.setPrice(44444);
	product.setFileName("�����");
	
	productService.updateProduct(product);
	product = productService.getProduct(10024);
	Assert.assertNotNull(product);
	
	System.out.println(product);
		
	//==> API Ȯ��
	Assert.assertEquals("�߾߾�", product.getProdName());
	Assert.assertEquals("������������", product.getProdDetail());
	Assert.assertEquals("3333", product.getManuDate());
	Assert.assertEquals(44444, product.getPrice());
	Assert.assertEquals("�����", product.getFileName());
 }
 
	//@Test
	 public void testGetProductListAll() throws Exception{
		System.out.println("����");
	 	Search search = new Search();
	 	search.setCurrentPage(1);
	 	search.setPageSize(3);
	 	Map<String,Object> map = productService.getProductList(search);
	 	System.out.println("����");
	 	List<Object> list = (List<Object>)map.get("list");
	 	Assert.assertEquals(3, list.size());
	 	
		//==> console Ȯ��
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
	 	
	 	//==> console Ȯ��
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
		 	
			//==> console Ȯ��
		 	System.out.println(list);
		 	
		 	Integer totalCount = (Integer)map.get("totalCount");
		 	System.out.println(totalCount);
		 	
		 	System.out.println("=======================================");
		 	
		 	search.setSearchCondition("1");
		 	search.setSearchKeyword(""+System.currentTimeMillis());
		 	map = productService.getProductList(search);
		 	
		 	list = (List<Object>)map.get("list");
		 	Assert.assertEquals(0, list.size());
		 	
			//==> console Ȯ��
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
		 	search.setSearchKeyword("�ϸ���");
		 	Map<String,Object> map = productService.getProductList(search);
		 	
		 	List<Object> list = (List<Object>)map.get("list");
		 	Assert.assertEquals(1, list.size());
		 	
			//==> console Ȯ��
		 	System.out.println(list);
		 	
		 	Integer totalCount = (Integer)map.get("totalCount");
		 	System.out.println(totalCount);
		 	
		 	System.out.println("=======================================");
		 	
		 	search.setSearchCondition("1");
		 	search.setSearchKeyword(""+System.currentTimeMillis());
		 	map = productService.getProductList(search);
		 	
		 	list = (List<Object>)map.get("list");
		 	Assert.assertEquals(0, list.size());
		 	
			//==> console Ȯ��
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
		 	
			//==> console Ȯ��
		 	System.out.println(list);
		 	
		 	Integer totalCount = (Integer)map.get("totalCount");
		 	System.out.println(totalCount);
		 	
		 	System.out.println("=======================================");
		 	
		 	search.setSearchCondition("1");
		 	search.setSearchKeyword(""+System.currentTimeMillis());
		 	map = productService.getProductList(search);
		 	
		 	list = (List<Object>)map.get("list");
		 	Assert.assertEquals(0, list.size());
		 	
			//==> console Ȯ��
		 	System.out.println(list);
		 	
		 	totalCount = (Integer)map.get("totalCount");
		 	System.out.println(totalCount);
		 }	 
	 
}