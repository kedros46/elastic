package be.duo.elastic.manager;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutionException;

import org.apache.lucene.queryparser.ext.Extensions.Pair;
import org.elasticsearch.action.bulk.byscroll.BulkByScrollResponse;
import org.elasticsearch.action.delete.DeleteResponse;
import org.elasticsearch.action.get.GetResponse;
import org.elasticsearch.action.get.MultiGetItemResponse;
import org.elasticsearch.action.get.MultiGetRequestBuilder;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.action.update.UpdateRequest;
import org.elasticsearch.action.update.UpdateResponse;
import org.elasticsearch.client.Client;
import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.index.reindex.DeleteByQueryAction;
import org.elasticsearch.index.reindex.DeleteByQueryRequestBuilder;

public class ElasticsearchManager {
	Client client;
	
	public ElasticsearchManager(TransportClient esNode) {
		client = esNode;
	}

	public IndexResponse index(String index, String type, Map<String, Object> json){
		return client.prepareIndex(index, type).setSource(json).get();
	}
	
	public GetResponse getDocument( String index, String type, String id){
		return client.prepareGet(index, type, id)
		        //.setOperationThreaded(false)
		        .get();
	}
	
	public DeleteResponse deleteIndex( String index, String type, String id){
		return client.prepareDelete(index, type, id)
		        //.setOperationThreaded(false)
		        .get();
	}
	
	public BulkByScrollResponse deleteIndexByQuery( String index, Map<String, Object> filter){
		DeleteByQueryRequestBuilder query = DeleteByQueryAction.INSTANCE.newRequestBuilder(client).source(index);
		filter.keySet().stream().forEach(k -> query.filter(QueryBuilders.matchQuery(k, filter.get(k))));
		
		return query.get();
		
		// OR async
/**		query.execute(new ActionListener<BulkByScrollResponse>() {
			... override
		}); 
*/
		
	}
	
	public UpdateResponse UpdateIndex( String index, String type, String id, Map<String, Object> source)
	  throws InterruptedException, ExecutionException{
		return client.update(new UpdateRequest(index, type, id).doc(source)).get();
	}
	
	/**
	 * 
	 * @param client
	 * @param source expects key = index, value = Pair<type, [id's...]>
	 * @return list of the GetResponse
	 */
	public List<GetResponse> getBulk( Map<String, Pair<String, String[]>> source){
		List<GetResponse> response = new ArrayList<>();
		MultiGetRequestBuilder requestBuilder = client.prepareMultiGet();
		
		source.keySet().stream().forEach(index -> {
			Pair<String, String[]> pair = source.get(index);
			if(pair != null && pair.cud.length > 0){
				requestBuilder.add(index, pair.cur, pair.cud);
			}
		});
		
		for (MultiGetItemResponse item : requestBuilder.get()) { 
		    if (item.getResponse().isExists()) {                      
		        response.add(item.getResponse());
		    }
		}
		
		return response;
	}
}
