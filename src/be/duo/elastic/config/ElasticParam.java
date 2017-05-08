package be.duo.elastic.config;

public enum ElasticParam {

	CLUSTERNAME("cluster.name"),
	NODENAME("node.name"),
	NODEATTRRACK("node.attr.rack"),
	PATHDATA("path.data"),
	PATHLOGS("pth.logs"),
	NETWORKHOST("network.host"),
	NETWORKPORt("network.port"),
	MEMORYLOCK("bootstrap.memory.lock"),
	DISCOVERYHOSTS("discovery.zen.ping.unicast.hosts"),
	DISCOVERYNODES("discovery.zen.minimum_master_nodes"),
	GATEWAYNODES("gateway.recover_after_nodes"),
	ISIGNORECLUSTERNAME("client.transport.ignore_cluster_name"),
	PINGTIMEOUT("client.transport.ping_timeout"),
	SAMPLERINTERVAL("client.transport.nodes_sampler_interval"),
	ISSNIF("client.transport.sniff");
	
	public final String name;
	private ElasticParam(String name){
		this.name = name;
	}
}
