package be.duo.elastic.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import be.duo.elastic.manager.WatcherManager;
import be.duo.elastic.model.ElasticNode;

@Controller
public class WatcherController {
	//	@Autowired
	ElasticNode node;
	
	@RequestMapping("list")
	public ModelAndView listWatchers(){
		System.out.println("listing watchers");
		WatcherManager wm = new WatcherManager();
		return new ModelAndView("watchers/list")
				.addObject("watchers", wm.listWatchers());
		//		.addObject("cluster", esClient.getCluster());
		
	}
}
