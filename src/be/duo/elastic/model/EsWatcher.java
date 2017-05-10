package be.duo.elastic.model;

import java.util.List;

import be.duo.elastic.action.Action;

interface EsWatcher {
	String getIndex();
	String getType();
	String getId();
	
	String getTrigger();
	String getInput();
	String getCondition();
	String getThrottlePeriod();
	List<Action> getActions();
}
