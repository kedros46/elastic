package be.duo.elastic.model;

import java.net.InetAddress;
import java.net.UnknownHostException;

import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.common.transport.InetSocketTransportAddress;
import org.elasticsearch.xpack.XPackClient;
import org.elasticsearch.xpack.client.PreBuiltXPackTransportClient;
import org.elasticsearch.xpack.watcher.client.WatcherClient;

public class WatcherManager {

	public WatcherManager() throws UnknownHostException {
		// TODO Auto-generated constructor stub
		TransportClient client = new PreBuiltXPackTransportClient(Settings.builder()
			    .put("cluster.name", "myClusterName")
			    .build())
			    .addTransportAddress(new InetSocketTransportAddress(InetAddress.getByName("localhost"), 9300));

			XPackClient xpackClient = new XPackClient(client);
			WatcherClient watcherClient = xpackClient.watcher();
			
			client.close();
	}
}
