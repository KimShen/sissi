package com.sisi.read.sax;

import java.util.HashMap;
import java.util.Map;

import com.sisi.read.Collector;

/**
 * @author kim 2013-10-25
 */
public class Employee implements Collector {

	private String name;

	private Level level;

	private Dept dept;

	private Area area;

	private LineManager lineManager;

	private String running;

	public String getRunning() {
		return running;
	}

	public void setRunning(String running) {
		this.running = running;
	}

	public Level getLevel() {
		return level;
	}

	public void setLevel(Level level) {
		this.level = level;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Dept getDept() {
		return dept;
	}

	public void setDept(Dept dept) {
		this.dept = dept;
	}

	public Area getArea() {
		return area;
	}

	public void setArea(Area area) {
		this.area = area;
	}

	public LineManager getLineManager() {
		return lineManager;
	}

	public void setLineManager(LineManager lineManager) {
		this.lineManager = lineManager;
	}

	public Map<String, Object> children = new HashMap<String, Object>();

	public void set(String localName, Object ob) {
		this.children.put(localName, ob);
	}
}
