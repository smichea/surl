package org.surl;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.channel.ChannelHandler.Sharable;
import io.netty.handler.codec.http.DefaultFullHttpResponse;
import io.netty.handler.codec.http.FullHttpRequest;
import io.netty.handler.codec.http.FullHttpResponse;
import io.netty.handler.codec.http.HttpHeaderNames;
import io.netty.handler.codec.http.HttpResponseStatus;
import io.netty.handler.codec.http.HttpVersion;
import io.netty.util.CharsetUtil;

@Sharable
public class SurlChannelHandler extends SimpleChannelInboundHandler<FullHttpRequest> {
    
    @Override
    protected void channelRead0(ChannelHandlerContext ctx, FullHttpRequest msg) throws Exception {
        //log the request
        System.out.println("uri:"+msg.uri());
        final StringBuffer responseBody=new StringBuffer("{\"uri\":\""+msg.uri()+"\",\n\"headers\":{\n");
        msg.headers().entries().forEach(entry->{
            String header="\""+entry.getKey()+"\":\""+entry.getValue()+"\",";
            System.out.println(header);
            responseBody.append(header);
        });
        
        //wonderfull way to remove the last comma ...
        responseBody.setLength(responseBody.length() - 1);
        
        responseBody.append("\n}\n}");
        //return the response
        ByteBuf content = Unpooled.copiedBuffer(responseBody.toString(), CharsetUtil.UTF_8);  
        FullHttpResponse response = new DefaultFullHttpResponse(HttpVersion.HTTP_1_1, HttpResponseStatus.OK, content);
        response.headers().set(HttpHeaderNames.CONTENT_TYPE, "application/json");
        response.headers().set(HttpHeaderNames.CONTENT_LENGTH, content.readableBytes());  
        ctx.write(response);
        ctx.flush(); 
    }
}
