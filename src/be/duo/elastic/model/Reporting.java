package be.duo.elastic.model;

import java.util.List;

import be.duo.elastic.action.Action;

public class Reporting implements EsWatcher{

	String index, type, id;
	String trigger;
	List<Action> actions;
	String throttlePeriod;
	
	
	@Override
	public String getInput() {
		return null;
	}

	@Override
	public List<Action> getActions() {
		return actions;
	}

	@Override
	public String getIndex() {
		return index;
	}

	@Override
	public String getType() {
		return type;
	}

	@Override
	public String getId() {
		return id;
	}

	@Override
	public String getCondition() {
		//reporter has no condition
		return null;
	}

	@Override
	public String getTrigger() {
		return trigger;
	}

	@Override
	public String getThrottlePeriod() {
		return throttlePeriod;
	}

	public Reporting  index(String index) {
		this.index = index;
		return this;
	}

	public Reporting  type(String type) {
		this.type = type;
		return this;
	}

	public Reporting id(String id) {
		this.id = id;
		return this;
	}

	public Reporting trigger(String trigger) {
		this.trigger = trigger;
		return this;
	}

	public Reporting actions(List<Action> actions) {
		this.actions = actions;
		return this;
	}

	public Reporting throttlePeriod(String throttlePeriod) {
		this.throttlePeriod = throttlePeriod;
		return this;
	}
	
	
	public Reporting build(){
		return this;
	}
}
