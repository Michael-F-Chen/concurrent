package com.ntc.concurrent.part5.bitwise;

/**
 * 演示使用位运算，判断用户权限（或者商品属性等）
 * 定义多个boolean也可以实现，但是，当属性非常多时，使用一个int就可以最多保存32种属性
 * @author Michael-Chen
 */
public class Permission {
	
    // 是否允许查询，二进制第1位，0表示否，1表示是  
    public static final int ALLOW_SELECT = 1 << 0; // 0001  = 1
      
    // 是否允许新增，二进制第2位，0表示否，1表示是  
    public static final int ALLOW_INSERT = 1 << 1; // 0010  = 2
      
    // 是否允许修改，二进制第3位，0表示否，1表示是  
    public static final int ALLOW_UPDATE = 1 << 2; // 0100  = 4
      
    // 是否允许删除，二进制第4位，0表示否，1表示是  
    public static final int ALLOW_DELETE = 1 << 3; // 1000  = 8
    
    // 存储目前的权限状态  
    private int flag; 
    
    // 设置用户的权限
    public void setPer(int per) {
    	flag = per;
    }
    
    // 增加用户的权限（1个或者多个）
    public void enable(int per) {
    	// 位或，表示增加权限
    	flag = flag|per;
    }
    
    // 删除用户的权限（1个或者多个）
    public void disable(int per) {
    	// 先位非再位与 ，表示删除权限
    	flag = flag&~per;
    }
    
    // 判断用户的权限
    public boolean isAllow(int per) {
    	return ((flag&per) == per);
    }
    // 判断用户没有的权限
    public boolean isNotAllow(int per) {
    	return ((flag&per) == 0);
    }
  
    public static void main(String[] args) {
    	// 所有权限都有，就是15
		int flag = 15;
		Permission permission = new Permission();
		permission.setPer(flag);
		// 禁止删除和新增
		permission.disable(ALLOW_DELETE|ALLOW_INSERT);
		
		System.out.println("select = "+permission.isAllow(ALLOW_SELECT));
		System.out.println("update = "+permission.isAllow(ALLOW_UPDATE));
		System.out.println("insert = "+permission.isAllow(ALLOW_INSERT));
		System.out.println("delete = "+permission.isAllow(ALLOW_DELETE));
		
		
	}
}
