package com.dustdawn;

import com.dustdawn.netty.WSServer;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;

@Component
public class NettyBooter implements ApplicationListener<ContextRefreshedEvent> {//上下文
    @Override
    public void onApplicationEvent(ContextRefreshedEvent contextRefreshedEvent) {
        //获得事件上下文对象父类是否为空，如空则启动
        if(contextRefreshedEvent.getApplicationContext().getParent() == null) {
            try {
                WSServer.getInstance().start();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }


}
