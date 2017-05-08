package be.duo.elastic.model;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Map;

import org.apache.lucene.queryparser.ext.Extensions.Pair;
import org.elasticsearch.action.ActionListener;
import org.elasticsearch.action.DocWriteResponse.Result;
import org.elasticsearch.action.bulk.byscroll.BulkByScrollResponse;
import org.elasticsearch.action.delete.DeleteResponse;
import org.elasticsearch.action.get.GetResponse;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.common.transport.InetSocketTransportAddress;
import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.index.reindex.DeleteByQueryAction;
import org.elasticsearch.index.reindex.DeleteByQueryRequestBuilder;
import org.elasticsearch.rest.RestStatus;
import org.elasticsearch.transport.client.PreBuiltTransportClient;

import be.duo.elastic.config.ElasticParam;

public class ElasticsearchManager {
	Settings settings;
	
	public ElasticsearchManager() {
		//TODO parameterize settings
		
		Settings settings = Settings.builder()
				.put(ElasticParam.CLUSTERNAME.name, "duodev")
				.build();	
	}
	
	@SuppressWarnings("resource")
	private TransportClient makeLocalClient(Settings settings) throws UnknownHostException{
		return new PreBuiltTransportClient(settings)
		.addTransportAddress(new InetSocketTransportAddress(InetAddress.getLocalHost(), 9300));
	}
	
	public boolean index(TransportClient client, String index, String type, Map<String, Object> json){
		IndexResponse rs = client.prepareIndex(index, type).setSource(json).get();
		return rs.status() == RestStatus.CREATED;
	}
	
	public GetResponse getDocument(TransportClient client, String index, String type, String id){
		return client.prepareGet(index, type, id)
		        //.setOperationThreaded(false)
		        .get();
	}
	
	public boolean deleteIndex(TransportClient client, String index, String type, String id){
		DeleteResponse rs = client.prepareDelete(index, type, id)
		        //.setOperationThreaded(false)
		        .get();
		return rs.status() != RestStatus.NOT_FOUND;
	}
	
	public boolean deleteIndexByQuery(TransportClient client, String index, Map<String, Object> filter){
		DeleteByQueryRequestBuilder query = DeleteByQueryAction.INSTANCE.newRequestBuilder(client).source(index);
		filter.keySet().stream().forEach(k -> query.filter(QueryBuilders.matchQuery(k, filter.get(k))));
		
		BulkByScrollResponse response = query.get();
		
		// OR async
/**		query.execute(new ActionListener<BulkByScrollResponse>() {
			@Override
			public void onResponse(BulkByScrollResponse response) {
								
			}
			
			@Override
			public void onFailure(Exception e) {
				
			}
		}); 
*/
		
		
		return response.getDeleted() > 0;
	}
	
}
