package com.tamu222i.wsm;

import org.apache.wicket.markup.html.WebPage;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.request.http.WebRequest;
import org.apache.wicket.request.cycle.RequestCycle;
import javax.servlet.http.HttpServletRequest;

import com.tamu222i.wsm.bean.Access;
import java.text.SimpleDateFormat;

public class TopPage extends WebPage {
  private static final long serialVersionUID = 1L;
   
  private AccessService svc;
 
  public TopPage() {
 
	svc = new AccessService();
    WebRequest req = (WebRequest) RequestCycle.get().getRequest();
    HttpServletRequest httpReq = (HttpServletRequest) req.getContainerRequest();
    String ip = httpReq.getRemoteHost();
 
    // 現在のIP表示
    add(new Label("ip", ip));
 
    // 前にアクセスした日付取得
    Access access = svc.readAccess(ip);
    if (access != null && access.getAccessTime() != null) {
      SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
      add(new Label("access", sdf.format(access.getAccessTime())));
    } else {
      add(new Label("access", "Nothing"));
    }
 
    // アクセスした日時を記録
    svc.writeAccess(ip);
  }
  /*
  public TopPage() {
    Label ip = new Label("ip", "IP");
    add(ip);
    Label access = new Label("access", "ACCESS");
    add(access);
  }
  */
}