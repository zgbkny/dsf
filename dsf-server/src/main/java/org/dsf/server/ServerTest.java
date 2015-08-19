package org.dsf.server;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.channel.socket.SocketChannel;

public class ServerTest {

	private int port;
	
	public ServerTest(int port) {
		this.port = port;
	}
	
	public void run() throws Exception {
		EventLoopGroup bossGroup = new NioEventLoopGroup();
		EventLoopGroup workerGroup = new NioEventLoopGroup();
		
		ServerBootstrap b = new ServerBootstrap();
		b.group(bossGroup, workerGroup)
		 .channel(NioServerSocketChannel.class)
		 .childHandler(new ChannelInitializer<SocketChannel>(){

			@Override
			protected void initChannel(SocketChannel sc) throws Exception {
				// TODO Auto-generated method stub
				sc.pipeline().addLast(new DiscardServerHandler());
			}
			 
		 })
		 .option(ChannelOption.SO_BACKLOG, 128)
		 .option(ChannelOption.SO_KEEPALIVE, true);
		
		// Bind and start to accept incoming connections
		ChannelFuture f = b.bind(port).sync();
		
		// wait until the server socket is closed
		f.channel().closeFuture().sync();
	}
	
	public static void main(String[] args) {
		
	}

}
