package be.duo.elastic.manager;

import java.net.UnknownHostException;
import java.util.List;

import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.xpack.notification.email.EmailTemplate;
import org.elasticsearch.xpack.watcher.actions.slack.SlackAction;
import org.elasticsearch.xpack.watcher.client.WatchSourceBuilder;
import org.elasticsearch.xpack.watcher.client.WatchSourceBuilders;
import org.elasticsearch.xpack.watcher.client.WatcherClient;
import org.elasticsearch.xpack.watcher.trigger.TriggerBuilders;
import org.elasticsearch.xpack.watcher.trigger.schedule.Schedules;

import be.duo.elastic.action.Action;
import be.duo.elastic.config.ActionType;
import be.duo.elastic.model.ElasticNode;

public class WatcherManager {
	
	public void listWatchers(){
		//get watches from .wacthes index (not writable for except x-pack)
		//also get watches from _watcher index (writable for devs)
		
		try {
			TransportClient esNode = new ElasticNode().getEsClient();
			SearchResponse srs = esNode.prepareSearch(".watches", "_watchers").get();
			
			//TODO do something with search
			esNode.close();
		} catch (UnknownHostException e) {
			e.printStackTrace();
		}
		
	}
	
	public void addWatcher(boolean isWatcher, String schedule, ActionType action){
		throw new UnsupportedOperationException("Not implemented yet");
		
		WatchSourceBuilder builder = WatchSourceBuilders.watchBuilder();
		
		builder.trigger(TriggerBuilders.schedule(Schedules.cron(schedule)));
		
		if(isWatcher){ //if false => reporting (doenst need condition) 
			
		}
		
		addAction(builder, new Action().type(Action.ActionType.EMAIL));
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
}
