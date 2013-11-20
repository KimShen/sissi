package com.sissi.read.sax;

import java.io.InputStream;
import java.util.Map;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;

import junit.framework.Assert;

import org.junit.Test;

import com.sissi.read.sax.SAXReader;
import com.sissi.read.sax.XMLMapping;

/**
 * @author kim 2013-10-21
 */
public class SAXHandlerTest {

	private SAXReader reader = new SAXReader(new XMLMapping(Thread.currentThread().getContextClassLoader().getResourceAsStream("sax-mapping.xml")));

	@Test(timeout = 5000)
	public void testDefault() throws Exception {
		InputStream input = Thread.currentThread().getContextClassLoader().getResourceAsStream("sax-default.xml");
		Future<Object> future = reader.future(input);
		Employee employee = (Employee) future.get(1, TimeUnit.DAYS);
		Level level = (Level) future.get(1, TimeUnit.DAYS);
		Assert.assertEquals("0", level.getText());
		Dept dept = (Dept) future.get(1, TimeUnit.DAYS);
		Assert.assertEquals("AREA", dept.getName().getArea());
		Assert.assertEquals("Production", dept.getName().getText());
		Assert.assertEquals("0", dept.getSort().getText());
		Assert.assertEquals("SUPPORT", dept.getSupport().getName().getText());
		Area area = (Area) future.get(1, TimeUnit.DAYS);
		Assert.assertEquals("areaA", area.getName().getText());
		Assert.assertEquals("1", area.getSort().getText());
		LineManager lineManager = (LineManager) future.get(1, TimeUnit.DAYS);
		Assert.assertEquals("N/A", lineManager.getName());
		Assert.assertEquals("0", lineManager.getText());
		employee = (Employee) future.get(1, TimeUnit.DAYS);
		Assert.assertEquals("N/A", lineManager.getName());
		Assert.assertEquals("0", lineManager.getText());
		Assert.assertEquals("kim", employee.getName());
		Assert.assertSame(level, employee.getLevel());
		Assert.assertSame(area, employee.getArea());
		Assert.assertSame(dept, employee.getDept());
		Assert.assertSame(lineManager, employee.getLineManager());
	}

	@Test(timeout = 5000)
	public void testWithoutCloseDocument() throws Exception {
		InputStream input = Thread.currentThread().getContextClassLoader().getResourceAsStream("sax-fragement-document.xml");
		Future<Object> future = reader.future(input);
		Employee employee = (Employee) future.get(1, TimeUnit.DAYS);
		Level level = (Level) future.get(1, TimeUnit.DAYS);
		Assert.assertEquals("0", level.getText());
		Dept dept = (Dept) future.get(1, TimeUnit.DAYS);
		Assert.assertEquals("AREA", dept.getName().getArea());
		Assert.assertEquals("Production", dept.getName().getText());
		Assert.assertEquals("0", dept.getSort().getText());
		Assert.assertEquals("SUPPORT", dept.getSupport().getName().getText());
		Area area = (Area) future.get(1, TimeUnit.DAYS);
		Assert.assertEquals("areaA", area.getName().getText());
		Assert.assertEquals("1", area.getSort().getText());
		LineManager lineManager = (LineManager) future.get(1, TimeUnit.DAYS);
		Assert.assertEquals("N/A", lineManager.getName());
		Assert.assertEquals("0", lineManager.getText());
		Assert.assertEquals("kim", employee.getName());
	}

	@Test(timeout = 5000)
	public void testWithoutCloseElement() throws Exception {
		InputStream input = Thread.currentThread().getContextClassLoader().getResourceAsStream("sax-fragement-element.xml");
		Future<Object> future = reader.future(input);
		Employee employee = (Employee) future.get(1, TimeUnit.DAYS);
		Level level = (Level) future.get(1, TimeUnit.DAYS);
		Assert.assertEquals("0", level.getText());
		Assert.assertEquals("kim", employee.getName());
	}

	@Test(timeout = 5000)
	public void testCollector() throws Exception {
		InputStream input = Thread.currentThread().getContextClassLoader().getResourceAsStream("sax-default.xml");
		Future<Object> future = reader.future(input);
		Employee employee = (Employee) future.get(1, TimeUnit.DAYS);
		Level level = (Level) future.get(1, TimeUnit.DAYS);
		Assert.assertEquals("0", level.getText());
		Dept dept = (Dept) future.get(1, TimeUnit.DAYS);
		Assert.assertEquals("AREA", dept.getName().getArea());
		Assert.assertEquals("Production", dept.getName().getText());
		Assert.assertEquals("0", dept.getSort().getText());
		Assert.assertEquals("SUPPORT", dept.getSupport().getName().getText());
		Area area = (Area) future.get(1, TimeUnit.DAYS);
		Assert.assertEquals("areaA", area.getName().getText());
		Assert.assertEquals("1", area.getSort().getText());
		LineManager lineManager = (LineManager) future.get(1, TimeUnit.DAYS);
		Assert.assertEquals("N/A", lineManager.getName());
		Assert.assertEquals("0", lineManager.getText());
		employee = (Employee) future.get(1, TimeUnit.DAYS);
		Assert.assertEquals("N/A", lineManager.getName());
		Assert.assertEquals("0", lineManager.getText());
		Assert.assertEquals("kim", employee.getName());
		Assert.assertSame(level, employee.getLevel());
		Assert.assertSame(area, employee.getArea());
		Assert.assertSame(dept, employee.getDept());
		Assert.assertSame(lineManager, employee.getLineManager());

		Map<String, Object> children = employee.children;
		Assert.assertSame(level, children.get("level"));
		Assert.assertSame(dept, children.get("dept"));
		Assert.assertSame(area, children.get("area"));
		Assert.assertSame(lineManager, children.get("lineManager"));
	}
}
