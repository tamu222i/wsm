package com.tamu222i.wsm;

import java.util.Date;
import java.io.IOException;
 
import com.tamu222i.wsm.bean.Access;
import com.tamu222i.wsm.mapper.AccessMapper;
import java.io.InputStream;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.apache.ibatis.session.SqlSession;


public class AccessService {
 
  private AccessMapper accessMapper;
  private SqlSession session;
 
  public AccessService() {
	  /*
	  BlogMapper mapper = session.getMapper(BlogMapper.class);
	  Blog blog = new Blog();
	  blog.setId(0);
	  blog.setContent("Test-Test");
	  mapper.insert(blog);
	  int count = mapper.countByExample(new BlogExample());
	  System.out.println(count);
	  session.close();
	  */
	  try {
		  String resource = "mybatis-config.xml";
		  InputStream inputStream = Resources.getResourceAsStream(resource);
		  SqlSessionFactory sqlSessionFactory = new SqlSessionFactoryBuilder().build(inputStream);
		  session = sqlSessionFactory.openSession();
		  accessMapper = session.getMapper(AccessMapper.class);
	  } catch(IOException e){
		  e.printStackTrace();
	  }
  }
  public void writeAccess(String ip) {
 
    Access record = new Access();
    record.setIp(ip);
    record.setAccessTime(new Date());

    Access exist = accessMapper.selectByPrimaryKey(ip);
    try {
	    if (exist == null) {
	      accessMapper.insert(record);
	    } else {
	      accessMapper.updateByPrimaryKey(record);
	    }
    } catch(Exception e){
    	session.rollback();
    	e.printStackTrace();
    } finally {
        session.commit();     	
    }
  }
 
  public Access readAccess(String ip) {
    return accessMapper.selectByPrimaryKey(ip);
  }
 
}