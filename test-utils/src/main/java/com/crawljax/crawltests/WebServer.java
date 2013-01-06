package com.crawljax.crawltests;

import java.net.URL;

import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.server.handler.ResourceHandler;
import org.eclipse.jetty.util.resource.Resource;

import com.google.common.base.Preconditions;

public class WebServer {
	private final Resource resource;

	private int port;
	private URL demoSite;
	private Server server;
	private boolean started;

	/**
	 * @param classPathResource
	 *            The name of the resource. This resource must be on the test or regular classpath.
	 */
	public WebServer(Resource classPathResource) {
		resource = classPathResource;
	}

	public void start() throws Exception {
		server = new Server(0);
		ResourceHandler handler = new ResourceHandler();
		handler.setBaseResource(resource);
		server.setHandler(handler);
		server.start();
		this.port = server.getConnectors()[0].getLocalPort();
		this.demoSite = new URL("http", "localhost", port, "/");
		this.started = true;
	}

	public URL getSiteUrl() {
		checkServerStarted();
		return demoSite;
	}

	public int getPort() {
		checkServerStarted();
		return port;
	}

	public void stop() throws Exception {
		checkServerStarted();
		server.stop();
	}

	private void checkServerStarted() {
		Preconditions.checkState(started, "Server not started");
	}

	public void join() throws InterruptedException {
		server.join();
	}
}