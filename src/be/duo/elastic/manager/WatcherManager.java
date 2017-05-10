package be.duo.elastic.manager;

import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.action.search.SearchType;
import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.common.bytes.BytesArray;
import org.elasticsearch.script.Script;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.xpack.notification.email.EmailTemplate;
import org.elasticsearch.xpack.watcher.Watcher;
import org.elasticsearch.xpack.watcher.client.WatchSourceBuilder;
import org.elasticsearch.xpack.watcher.client.WatchSourceBuilders;
import org.elasticsearch.xpack.watcher.client.WatcherClient;
import org.elasticsearch.xpack.watcher.condition.ScriptCondition;
import org.elasticsearch.xpack.watcher.input.search.SearchInput;
import org.elasticsearch.xpack.watcher.support.search.WatcherSearchTemplateRequest;
import org.elasticsearch.xpack.watcher.transport.actions.activate.ActivateWatchRequestBuilder;
import org.elasticsearch.xpack.watcher.transport.actions.get.GetWatchResponse;
import org.elasticsearch.xpack.watcher.transport.actions.put.PutWatchResponse;
import org.elasticsearch.xpack.watcher.trigger.TriggerBuilders;
import org.elasticsearch.xpack.watcher.trigger.schedule.Schedules;

import be.duo.elastic.action.Action;
import be.duo.elastic.model.ElasticNode;

public class WatcherManager {
	//@Autowired 
	TransportClient esClient;
	//@Autowired
	WatcherClient watchClient;
	
	public WatcherManager() {
		try {
			ElasticNode node = new ElasticNode(); 
			esClient = node.getEsClient();
			watchClient = node.getWatcherClient();
			
		} catch (UnknownHostException e) {
			e.printStackTrace();
		}
	}
	public void listWatchers(){
		List<EsWatcher> watchers = new ArrayList<>();
		
		//get watches from .wacthes index (not writable for except x-pack)
		//also get watches from .watchers index (writable for devs)

		SearchResponse rs = esClient.prepareSearch(".watches", "_watchers").get();
		//TODO resolve response
		for(SearchHit hit : rs.getHits()){
			Map<String, Object> source = hit.getSource();
			

			watchers.add(new Alerting()
					.index(hit.getIndex())
					.type(hit.getType())
					.id(hit.getId())
					.trigger(source.get("trigger"))
					.input(source.get("input"))
					.condition(source.get("condition"))
					.throttlePeriod(source.get("throttle_period"))
					.actions(source.get("actions")));
		}
	}
	
	public GetWatchResponse getWatchStatus(String id) throws UnknownHostException{
		return watchClient.prepareGetWatch(id).get();
	}
	public ActivateWatchRequestBuilder setWatchState(String id, boolean state){
		return watchClient.prepareActivateWatch(id, state);
	}
	
	public PutWatchResponse addWatcher(String id, String schedule, Action action, SearchRequest request, String condition) throws UnknownHostException{
		
		WatchSourceBuilder builder = WatchSourceBuilders.watchBuilder();
		
		builder.trigger(TriggerBuilders.schedule(Schedules.cron(schedule)));
		
		builder.input(makeSearchInput(request));
		builder.condition(new ScriptCondition(new Script(condition)));
		addAction(builder, action);
		
		return watchClient.preparePutWatch(id)
							.setSource(builder).get();
	}
	
	public void addReporter(String id, String schedule, Action action){

	}

	private void addAction(WatchSourceBuilder builder, Action action) {
		// TODO Auto-generated method stub
		switch(action.getType()){
			case EMAIL:
				EmailTemplate.Builder ebuilder = EmailTemplate.builder();
				ebuilder.to(action.getTo());
				ebuilder.from(action.getFrom());
				ebuilder.bcc(action.getBcc());
				ebuilder.cc(action.getCc());
				ebuilder.textBody(action.getBody());
				
				break;
			case SLACK: 
				break;
			case WEBHOOK: break;
			case ELK: break;
		default:
			break;
				
		}
	}
	
	private SearchInput makeSearchInput(SearchRequest request){
		return new SearchInput(new WatcherSearchTemplateRequest(request.indices(), null, SearchType.DEFAULT, 
				WatcherSearchTemplateRequest.DEFAULT_INDICES_OPTIONS, new BytesArray(request.source().toString())), null, null, null);
	}
}
