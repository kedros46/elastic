package be.duo.elastic.model;



import java.net.InetAddress;
import java.net.UnknownHostException;

import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.common.transport.InetSocketTransportAddress;
import org.elasticsearch.transport.client.PreBuiltTransportClient;
import org.elasticsearch.xpack.XPackClient;
import org.elasticsearch.xpack.client.PreBuiltXPackTransportClient;
import org.elasticsearch.xpack.watcher.client.WatcherClient;

import be.duo.elastic.config.ElasticParam;

public class ElasticNode {

//	public enum ClientType{
//		ES,
//		MONITORING,
//		LICENSE,
//		MACHINE_LEARNING,
//		WATCHER,
//		SECURITY;
//	}
	
	Settings settings;
	
	public ElasticNode() {
		//TODO parameterize settings
		settings = Settings.builder()
				.put(ElasticParam.CLUSTERNAME.name, "duodev")
				.build();
	}
	
	public TransportClient getEsClient() throws UnknownHostException{
		return new PreBuiltTransportClient(settings).addTransportAddress(new InetSocketTransportAddress(InetAddress.getLocalHost(), 9300));
	}
	
	public WatcherClient getWatcherClient() throws UnknownHostException{
		TransportClient client = new PreBuiltXPackTransportClient(settings).addTransportAddress(new InetSocketTransportAddress(InetAddress.getLocalHost() , 9300));
		return new XPackClient(client).watcher();
	}
	
//	public SecurityClient getSecurityClient() throws UnknownHostException{
//		TransportClient client = new PreBuiltXPackTransportClient(settings).addTransportAddress(new InetSocketTransportAddress(InetAddress.getLocalHost() , 9300));
//		return new XPackClient(client).security();
//	}
}
