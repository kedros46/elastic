package be.duo.elastic.model;

import java.util.List;

import be.duo.elastic.action.Action;
import be.duo.elastic.model.EsWatcher.Type;

public class EsWatcher {

	public enum Type{
		REPORTING, ALERTING;
	}
	
	String index, id;
	Type type;
	String trigger;
	String input;
	String condition;
	List<Action> actions;
	String throttlePeriod;
	
	public String getIndex() {
		return index;
	}
	public EsWatcher index(String index) {
		this.index = index;
		return this;
	}
	public Type getType() {
		return type;
	}
	public EsWatcher type(Type type) {
		this.type = type;
		return this;
	}
	public String getId() {
		return id;
	}
	public EsWatcher id(String id) {
		this.id = id;
		return this;
	}
	public String getTrigger() {
		return trigger;
	}
	public EsWatcher trigger(String trigger) {
		this.trigger = trigger;
		return this;
	}
	public String getInput() {
		return input;
	}
	public EsWatcher input(String input) {
		this.input = input;
		return this;
	}
	public String getCondition() {
		return condition;
	}
	public EsWatcher condition(String condition) {
		this.condition = condition;
		return this;
	}
	public List<Action> getActions() {
		return actions;
	}
	public EsWatcher actions(List<Action> actions) {
		this.actions = actions;
		return this;
	}
	public String getThrottlePeriod() {
		return throttlePeriod;
	}
	public EsWatcher throttlePeriod(String throttlePeriod) {
		this.throttlePeriod = throttlePeriod;
		return this;
	}
	
	public static Type toType(String type) {
		return "alert".equals(type) ? Type.ALERTING : Type.REPORTING;
	}
	public static List<Action> toActions(Object object) {
		return null;
	}
	
	
}
