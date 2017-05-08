package be.duo.elastic.config;

public enum ElasticParam {

	CLUSTERNAME("cluster.name"),
	ISIGNORECLUSTERNAME("client.transport.ignore_cluster_name"),
	PINGTIMEOUT("client.transport.ping_timeout"),
	SAMPLERINTERVAL("client.transport.nodes_sampler_interval"),
	ISSNIF("client.transport.sniff");
	
	public final String name;
	private ElasticParam(String name){
		this.name = name;
	}
}
