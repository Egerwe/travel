package edu.fk.pojo;

import java.io.Serializable;
import java.util.List;

/**
 * 分类实体类
 */
public class Category implements Serializable {

	private static final long serialVersionUID = 1L;
	private Integer cid;//分类id
    private String cname;//分类名称
    
    private List<Route> routeList;//分类关联的旅游产品

    public Category() {
    }

    public Category(Integer cid, String cname) {
        this.cid = cid;
        this.cname = cname;
    }

    @Override
    public String toString() {
        return "Category{" +
                "cid=" + cid +
                ", cname='" + cname + '\'' +
                '}';
    }

    public Integer getCid() {
        return cid;
    }

    public void setCid(Integer cid) {
        this.cid = cid;
    }

    public String getCname() {
        return cname;
    }

    public void setCname(String cname) {
        this.cname = cname;
    }

	public List<Route> getRouteList() {
		return routeList;
	}

	public void setRouteList(List<Route> routeList) {
		this.routeList = routeList;
	}
    
    
}
